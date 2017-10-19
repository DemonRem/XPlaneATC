/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.nettybootstrap.pilot;

import de.xatc.commons.datastructure.pilot.PilotStructure;
import de.xatc.commons.datastructure.structureaction.RemovePilotStructure;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.FMSPlan;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.networking.protocol.controller.TextMessageHandler;
import de.xatc.server.networking.protocol.pilot.LoginHandler;
import de.xatc.server.networking.protocol.pilot.RegisterHandler;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(DataServerHandler.class.getName());
    private Channel channel;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        LOG.debug("CHANNEL READ. Incoming Packet!");
        LOG.info("Object is: " + msg);

        if (msg instanceof NetworkPacket == false) {
            LOG.warn("No NetworkPacket received! Returning");
            return;
        } else {

            NetworkPacket networkPacket = (NetworkPacket) msg;
            //TODO hier noch Session Validierung.....

            if (StringUtils.isEmpty(networkPacket.getSessionID())) {

                if (msg instanceof RegisterPacket) {

                    LOG.debug("CHANNEL ID: " + this.channel.id());
                    LOG.debug("RegisterPacketAnswer Received!");
                    RegisterHandler.doRegistering(channel, msg);

                } else if (msg instanceof LoginPacket) {

                    LOG.debug("Login Packet received!");
                    LoginHandler.handleLogin(channel, msg);

                }

            }
            if (msg instanceof TextMessagePacket) {

                TextMessageHandler.handleUserTextMessage(channel, msg);

            } else if (msg instanceof PlanePosition) {
                LOG.debug("received PlanePosition");
                if (SessionManagement.getAtcDataStructures().size() > 0) {
                    ServerConfig.getMessageSenders().get("planePosition").sendObjectMessage((PlanePosition) msg);
                }
                
                //TRACKDATAINNB_START
                //itemName="pilotSendNewFlightPlan"
                //className="DataServerHandler"
                //comment="Server takes FlightplanActoin and sends it via mq"
                //step=2
                //itemType="inside"
                //methodName="na"
                //TRACKDATAINNB_STOP    
            } else if (msg instanceof SubmittedFlightPlansActionPacket) {
                SubmittedFlightPlansActionPacket f = (SubmittedFlightPlansActionPacket) msg;
                ServerConfig.getMessageSenders().get("submittedFlightPlanActionsPilot").sendObjectMessage(f);
                return;
            } 
        
            
            else if (msg instanceof FMSPlan) {
                FMSPlan plan = (FMSPlan) msg;
                SessionManagement.getPilotDataStructures().get(plan.getSessionID()).setFmsPlan(plan);
                NetworkBroadcaster.broadcastATC(plan);
            }

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        SessionManagement.removePilotSessionByChannel(ctx.channel());

        PilotStructure s = SessionManagement.findPilotStructureByChannelID(ctx.channel().id().asLongText());
        if (s != null) {
            RemovePilotStructure removePacket = new RemovePilotStructure();
            removePacket.setStructureSessionID(s.getStructureSessionID());
            SessionManagement.getPilotDataStructures().remove(s.getStructureSessionID());
            NetworkBroadcaster.broadcastATC(removePacket);
                    
        }
        
        LOG.info("client disconnected");

        channel.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("Client connected");
        this.channel = ctx.channel();


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        LOG.error("SERVER EXCEPTION CAUGHT!!!!!!!!!!!!!!!!!");
        LOG.error(cause.getLocalizedMessage());
        cause.printStackTrace(System.err);
        SessionManagement.removePilotSessionByChannel(ctx.channel());
        
        channel.close();
    }

}
