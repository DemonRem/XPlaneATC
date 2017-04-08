/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.clients;

import de.xatc.commons.networkpackets.atc.stripsmgt.ATCRequestStripsPacket;
import de.xatc.commons.networkpackets.client.FMSPlan;
import de.xatc.commons.networkpackets.client.LoginPacket;
import de.xatc.commons.networkpackets.client.PlanePosition;
import de.xatc.commons.networkpackets.client.RegisterPacket;
import de.xatc.commons.networkpackets.client.SubmittedFlightPlan;
import de.xatc.commons.networkpackets.client.TextMessagePacket;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.networking.protocol.client.LoginHandler;
import de.xatc.server.networking.protocol.client.RegisterHandler;
import de.xatc.server.networking.protocol.controller.SubmittedFlightPlanHandler;
import de.xatc.server.networking.protocol.controller.TextMessageHandler;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataServerHandler extends ChannelInboundHandlerAdapter {

    private Channel channel;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("CHANNEL READ. Incoming Packet!");
        System.out.println("Object is: " + msg);

        if (msg instanceof NetworkPacket == false) {
            System.out.println("No NetworkPacket received! Returning");
            return;
        } else {

            NetworkPacket networkPacket = (NetworkPacket) msg;
            //TODO hier noch Session Validierung.....

            if (StringUtils.isEmpty(networkPacket.getSessionID())) {

                if (msg instanceof RegisterPacket) {

                    System.out.println("CHANNEL ID: " + this.channel.id());
                    System.out.println("RegisterPacketAnswer Received!");
                    RegisterHandler.doRegistering(channel, msg);

                } else if (msg instanceof LoginPacket) {

                    System.out.println("Login Packet received!");
                    LoginHandler.handleLogin(channel, msg);

                }

            }
            if (msg instanceof TextMessagePacket) {

                TextMessageHandler.handleUserTextMessage(channel, msg);

            } else if (msg instanceof PlanePosition) {
                System.out.println("received PlanePosition");
                if (SessionManagement.getAtcChannelGroup().size() > 0) {
                    ServerConfig.getMessageSenders().get("planePosition").sendObjectMessage((PlanePosition) msg);
                }
                
            } else if (msg instanceof SubmittedFlightPlan) {
                SubmittedFlightPlan f = (SubmittedFlightPlan) msg;
                ServerConfig.getMessageSenders().get("submittedFlightPlans").sendObjectMessage(f);
                return;
            } 
            
            
            
            
            else if (msg instanceof FMSPlan) {
                System.out.println("FMSPlAN received");
                //TODO
            }

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        SessionManagement.removeDataChannel(ctx.channel());
        SessionManagement.removeUserSession(SessionManagement.findUserSessionByChannelID(ctx.channel().id().asLongText(), SessionManagement.getUserSessionList()));
        System.out.println("client disconnected");

        channel.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected");
        this.channel = ctx.channel();
        SessionManagement.addDataChannel(ctx.channel());
        //TODO, das darf hier erst passieren, wenn ein Login packet kommt!

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("SERVER EXCEPTION CAUGHT!!!!!!!!!!!!!!!!!");
        cause.printStackTrace(System.err);
        SessionManagement.getDataChannelGroup().remove(ctx.channel());
        channel.close();
    }

}
