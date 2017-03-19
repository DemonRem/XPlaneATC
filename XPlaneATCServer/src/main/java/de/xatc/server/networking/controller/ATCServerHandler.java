/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.controller;

import de.xatc.commons.networkpackets.atc.datasync.RequestSyncPacket;
import de.xatc.commons.networkpackets.atc.servercontrol.RequestServerMetrics;
import de.xatc.commons.networkpackets.atc.servercontrol.ShutdownServer;
import de.xatc.commons.networkpackets.atc.servercontrol.StartClientConnector;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMQBroker;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMessagingConsumers;
import de.xatc.commons.networkpackets.atc.servercontrol.StartMessagingProducers;
import de.xatc.commons.networkpackets.atc.servercontrol.StopClientConnector;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMQBroker;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMessagingConsumers;
import de.xatc.commons.networkpackets.atc.servercontrol.StopMessagingProducers;
import de.xatc.commons.networkpackets.atc.stations.SupportedAirportStation;
import de.xatc.commons.networkpackets.atc.stations.SupportedFirStation;
import de.xatc.commons.networkpackets.atc.usermgt.DeleteUser;
import de.xatc.commons.networkpackets.atc.usermgt.NewUser;
import de.xatc.commons.networkpackets.atc.usermgt.RequestUserList;
import de.xatc.commons.networkpackets.atc.usermgt.UpdateUser;
import de.xatc.commons.networkpackets.client.LoginPacket;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.server.networking.protocol.controller.ATCLoginHandler;
import de.xatc.server.networking.protocol.controller.MetricsHandler;
import de.xatc.server.networking.protocol.controller.RequestUserListHandler;
import de.xatc.server.networking.protocol.controller.ServerControlHandler;
import de.xatc.server.networking.protocol.controller.ServerSyncHandler;
import de.xatc.server.networking.protocol.controller.SetupATCHandler;
import de.xatc.server.networking.protocol.controller.UserManagementHander;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCServerHandler extends ChannelInboundHandlerAdapter {

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

            System.out.println("Recieved Packet is a NetworkPacket");

            if (StringUtils.isEmpty(networkPacket.getSessionID())) {

                System.out.println("Session ID is empty. Looking for a LoginPacket...");
                if (msg instanceof LoginPacket) {

                    System.out.println("Login Packet received!");
                    ATCLoginHandler.handleLogin(channel, msg);

                }
                System.out.println("SessionID empty and no LoginPacket.... returning.");
                return;
            }

            if (msg instanceof RequestServerMetrics) {

                System.out.println("Server received ServerMetrics Request");
                MetricsHandler.handleMetrics(channel, msg);
                return;

            }
            if (msg instanceof StartClientConnector) {

                ServerControlHandler.handleStartClientConnections(msg);
                return;
            }
            
            if (msg instanceof StopClientConnector) {
                ServerControlHandler.handleStopClientConnections(msg);
                return;
            }
            if (msg instanceof ShutdownServer) {
                ServerControlHandler.handleShutdownServer(msg);
                return;
            }
            if (msg instanceof StartMessagingProducers) {
                ServerControlHandler.handleStartMessagingProducers(msg);
                return;
            }
            if (msg instanceof StopMessagingProducers) {
                ServerControlHandler.handleStopMessagingProducers(msg);
                return;
            }
            if (msg instanceof StartMessagingConsumers) {
                ServerControlHandler.handleStartMessagingConsumers(msg);
                return;
            }
            if (msg instanceof StopMessagingConsumers) {
                ServerControlHandler.handleStopMessagingConsumers(msg);
                return;
            }
            if (msg instanceof StartMQBroker) {
                ServerControlHandler.handleStartMQBroker(msg);
                return;
            }
            if (msg instanceof StopMQBroker) {
                ServerControlHandler.handleStopMQBroker(msg);
                return;
            }
            if (msg instanceof RequestUserList) {
                RequestUserListHandler.handleGetUserListRequest(channel);
                return;
            }
            if (msg instanceof DeleteUser) {
                DeleteUser u = (DeleteUser) msg;
                UserManagementHander.deleteUser(u.getUserName());
                return;
            }
            if (msg instanceof NewUser) {
                NewUser n = (NewUser) msg;
                UserManagementHander.newUser(n);
                return;
            }
            if (msg instanceof UpdateUser) {
                UpdateUser u = (UpdateUser) msg;
                UserManagementHander.updateUser(u);
                return;
            }
            if (msg instanceof RequestSyncPacket) {
                
                RequestSyncPacket p = (RequestSyncPacket) msg;
                
                ServerSyncHandler.handleServerSyncRequest(channel, p.getDataSetToSync());
                return;
                
            }
            
            if (msg instanceof SupportedAirportStation) {
                System.out.println("Incoming SupportedAirportStation");
                SupportedAirportStation airport = (SupportedAirportStation) msg;
                SetupATCHandler.handleAirportSetup(airport,ctx.channel());
            }
            
            if (msg instanceof SupportedFirStation) {
                System.out.println("Incoming SupportedAirportStation");
                SupportedFirStation fir = (SupportedFirStation) msg;
                SetupATCHandler.handleFirSetup(fir,ctx.channel());
            }
            
            
            
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        SessionManagement.removeATCChannel(ctx.channel());
        SessionManagement.removeATCSession(ctx.channel().id().asLongText());
        System.out.println("client disconnected");

        channel.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected");
        this.channel = ctx.channel();
        SessionManagement.addATCChannel(ctx.channel());
        //Die Session wird allerdinsg vom Login handler zugefuegt

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("SERVER EXCEPTION CAUGHT!!!!!!!!!!!!!!!!!");
        cause.printStackTrace(System.err);
        SessionManagement.getAtcChannelGroup().remove(ctx.channel());
        SessionManagement.removeATCChannel(ctx.channel());
        channel.close();
    }

}
