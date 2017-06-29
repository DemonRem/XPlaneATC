/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.nettyclient;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.datastructure.structureaction.PlanePositoinSyncResponse;
import de.xatc.commons.datastructure.structureaction.RemovePilotStructure;
import de.xatc.commons.networkpackets.atc.datasync.DataStructuresResponsePacket;
import de.xatc.commons.networkpackets.atc.datasync.DataSyncPacket;
import de.xatc.commons.networkpackets.atc.servercontrol.ServerMetrics;
import de.xatc.commons.networkpackets.atc.usermgt.UserListResponse;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.FMSPlan;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.commons.networkpackets.pilot.ServerMessageToClient;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.datastructures.DataStructureSilo;
import de.xatc.controllerclient.gui.tools.ControllerClientGuiTools;
import de.xatc.controllerclient.network.handlers.DataStructureResponseHandler;
import de.xatc.controllerclient.network.handlers.DataSyncHandler;
import de.xatc.controllerclient.network.handlers.LoginAnswerHandler;
import de.xatc.controllerclient.network.handlers.MetricsAnswerHandler;
import de.xatc.controllerclient.network.handlers.SubmittedFlightPlanActionHandler;
import de.xatc.controllerclient.network.handlers.UserListResponseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataClient extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    public void writeMessage(Object msg) {

        if (msg instanceof LoginPacket) {
            sendPacket(msg);
        } else if (msg instanceof RegisterPacket) {
            sendPacket(msg);
        } else {

            if (StringUtils.isEmpty(XHSConfig.getCurrentSessionID())) {
                System.out.println("No SessionID, could not send packet");
                return;
            }
            if (StringUtils.isEmpty(XHSConfig.getCurrentChannelID())) {
                System.out.println("No ChannelID, could not send packet");
                return;
            }

            NetworkPacket p = (NetworkPacket) msg;
            p.setChannelID(XHSConfig.getCurrentChannelID());
            p.setSessionID(XHSConfig.getCurrentSessionID());
            sendPacket(p);

        }

    }

    private void sendPacket(Object msg) {

        if (this.ctx.channel().isActive()) {
            this.ctx.channel().writeAndFlush(msg);
        } else {
            System.out.println("Channel is not active!");
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("Channel Read of DataClient, incoming Packet");
        System.out.println("Packet is: " + msg);
        if (msg instanceof LoginPacket) {
            System.out.println("INCOMING LoginPacket");
            LoginAnswerHandler.handleLoginAnswer(msg);

        } else if (msg instanceof ServerMetrics) {
            System.out.println("Handling ServerMetrics Packet");
            MetricsAnswerHandler.handleMetricsAnswer(msg);
            return;
        } else if (msg instanceof DataSyncPacket) {

            System.out.println("incoming data sync packet");
            DataSyncPacket p = (DataSyncPacket) msg;

            DataSyncHandler.handleIncomingDataSyncPacket(p);
            return;
        }

        if (msg instanceof PlanePosition) {

            PlanePosition p = (PlanePosition) msg;
            System.out.println("PlanePosition received");
            DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getAircraftPainter().setP(p);
            //XHSConfig.getMainFrame().getMainPanel().getMapPanel().getAircraftPainter().setP(p);
            DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getPilotServerStructure().setLastKnownPlanePosition(p);
            DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getPilotServerStructure().getPlanePositionList().add(p);
            System.out.println("SIZE OF positoinLIST: " + DataStructureSilo.getLocalPilotStructure().get(p.getSessionID()).getPilotServerStructure().getPlanePositionList().size());
            XHSConfig.getMainFrame().getMainPanel().getMapPanel().repaint();
            return;
        }
        if (msg instanceof UserListResponse) {

            System.out.println("User List Response received");
            UserListResponse u = (UserListResponse) msg;
            UserListResponseHandler.handleUserListResonse(u);
            return;

        }

        if (msg instanceof ServerMessageToClient) {

            System.out.println("Server Message To Client received");
            ServerMessageToClient message = (ServerMessageToClient) msg;
            SwingTools.alertWindow(message.getMessage(), XHSConfig.getMainFrame());
            return;

        }
        if (msg instanceof TextMessagePacket) {
            System.out.print("Textmessage Packet received");

            ControllerClientGuiTools.showChatFrame();
            TextMessagePacket messagePacket = (TextMessagePacket) msg;

            XHSConfig.getChatFrame().toFront();
            XHSConfig.getChatFrame().addMessage(messagePacket);
            return;
        }

        if (msg instanceof DataStructuresResponsePacket) {

            
            System.out.println("DataStructuresREspoinsePacket received");
            DataStructuresResponsePacket r = (DataStructuresResponsePacket) msg;

            if (r.getAtcStructure() != null) {
                
                DataStructureResponseHandler.handleNewATCStructure(r.getAtcStructure());
                
            }
            else if (r.getPilotStructure() != null) {
                DataStructureResponseHandler.handleNewPilotStructure(r.getPilotStructure());
            }
            System.out.println(r.getStructureSsessionID() + "**************************************");
            
            return;

        }
        if (msg instanceof RemovePilotStructure) {
            
            RemovePilotStructure r = (RemovePilotStructure) msg;
            DataStructureResponseHandler.handleRemovePilotStructure(r.getStructureSessionID());
            return;
        }
        if (msg instanceof FMSPlan) {
            
            FMSPlan plan = (FMSPlan) msg;
            DataStructureSilo.getLocalPilotStructure().get(plan.getSessionID()).getPilotServerStructure().setFmsPlan(plan);
            
        }
        if (msg instanceof SubmittedFlightPlan) {
            
            
            
        }
        
 
        Hier muss noch was mit den Actions gemacht werden.
        if (msg instanceof SubmittedFlightPlansActionPacket) {

            SubmittedFlightPlansActionPacket p = (SubmittedFlightPlansActionPacket) msg;
            SubmittedFlightPlanActionHandler.handleActionPacket(p);
            return;

        }
        
        if (msg instanceof PlanePositoinSyncResponse) {
            
            PlanePositoinSyncResponse r = (PlanePositoinSyncResponse) msg;
            DataStructureSilo.getLocalPilotStructure().get(r.getStructureSessionID()).getPilotServerStructure().getPlanePositionList().add(r.getP());
            
            
        }

        

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("client Channel is now inactive");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Client Channel is now Active!");
        this.ctx = ctx;

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("EXCEPTION CAUGHT ON DATA-CLIENT.....");
        cause.printStackTrace(System.err);
        ctx.close();

    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

}
