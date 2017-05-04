/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.nettyclient;


import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.atc.datasync.DataSyncPacket;
import de.xatc.commons.networkpackets.atc.servercontrol.ServerMetrics;
import de.xatc.commons.networkpackets.atc.usermgt.UserListResponse;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.commons.networkpackets.pilot.ServerMessageToClient;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansPacket;
import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.gui.tools.ControllerClientGuiTools;
import de.xatc.controllerclient.network.handlers.DataSyncHandler;
import de.xatc.controllerclient.network.handlers.LoginAnswerHandler;
import de.xatc.controllerclient.network.handlers.MetricsAnswerHandler;
import de.xatc.controllerclient.network.handlers.SubmittedFlightPlanActionHandler;
import de.xatc.controllerclient.network.handlers.SubmittedFlightPlansHandler;
import de.xatc.controllerclient.network.handlers.UserListResponseHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.List;
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
            
        }
       else if (msg instanceof ServerMetrics) {
           System.out.println("Handling ServerMetrics Packet");
           MetricsAnswerHandler.handleMetricsAnswer(msg);
       }
       else if (msg instanceof DataSyncPacket) {
           
           DataSyncPacket p = (DataSyncPacket) msg;
           
           DataSyncHandler.handleIncomingDataSyncPacket(p);
           
       }
       
       
       
       if (msg instanceof PlanePosition) {
           
           
           PlanePosition p = (PlanePosition) msg;
           System.out.println("PlanePosition received");
           XHSConfig.getMainFrame().getMainPanel().getMapPanel().getAircraftPainter().setP(p);
           XHSConfig.getMainFrame().getMainPanel().getMapPanel().repaint();
           return;
       }
       if (msg instanceof UserListResponse) {
           
           UserListResponse u = (UserListResponse) msg;
           UserListResponseHandler.handleUserListResonse(u);
           return;
           
       }
       
       if (msg instanceof ServerMessageToClient) {
           
           ServerMessageToClient message = (ServerMessageToClient) msg;
           SwingTools.alertWindow(message.getMessage(), XHSConfig.getMainFrame());
           return;
           
       }
       if (msg instanceof TextMessagePacket) {
           
           ControllerClientGuiTools.showChatFrame();
           TextMessagePacket messagePacket = (TextMessagePacket) msg;
           
           XHSConfig.getChatFrame().toFront();
           XHSConfig.getChatFrame().addMessage(messagePacket);
           return;
       }
       
       if (msg instanceof SubmittedFlightPlansPacket) {   
           System.out.println("SAVING FLIGHTPLANS");
           SubmittedFlightPlansPacket p = (SubmittedFlightPlansPacket) msg; 
           SubmittedFlightPlansHandler.saveFlightSubmittedFlightPlans(p.getList());
           return;
       }
       if (msg instanceof SubmittedFlightPlansActionPacket) {
           
           SubmittedFlightPlansActionPacket p = (SubmittedFlightPlansActionPacket) msg;
           SubmittedFlightPlanActionHandler.handleActionPacket(p);
           return;
           
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