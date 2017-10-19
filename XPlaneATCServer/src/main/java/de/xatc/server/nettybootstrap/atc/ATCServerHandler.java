/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.nettybootstrap.atc;

import de.xatc.commons.datastructure.atc.ATCStructure;
import de.xatc.commons.datastructure.structureaction.RemoveATCStructure;
import de.xatc.commons.datastructure.structureaction.RequestATCStructure;
import de.xatc.commons.datastructure.structureaction.RequestPilotStructure;
import de.xatc.commons.datastructure.structureaction.RequestPlanePositionSync;
import de.xatc.commons.networkpackets.atc.datasync.RequestDataStructuresPacket;
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
import de.xatc.commons.networkpackets.atc.supportedstations.SupportedAirportStation;
import de.xatc.commons.networkpackets.atc.supportedstations.SupportedFirStation;
import de.xatc.commons.networkpackets.atc.usermgt.DeleteUser;
import de.xatc.commons.networkpackets.atc.usermgt.NewUser;
import de.xatc.commons.networkpackets.atc.usermgt.RequestUserList;
import de.xatc.commons.networkpackets.atc.usermgt.UpdateUser;
import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.SubmittedFlightPlansActionPacket;
import de.xatc.server.config.ServerConfig;
import de.xatc.server.networking.protocol.controller.ATCLoginHandler;
import de.xatc.server.networking.protocol.controller.MetricsHandler;
import de.xatc.server.networking.protocol.controller.RequestUserListHandler;
import de.xatc.server.networking.protocol.controller.ServerControlHandler;
import de.xatc.server.networking.protocol.controller.ServerSyncHandler;
import de.xatc.server.networking.protocol.controller.SetupATCHandler;
import de.xatc.server.networking.protocol.controller.UserManagementHander;
import de.xatc.server.sessionmanagment.NetworkBroadcaster;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(ATCServerHandler.class.getName());
    
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

            LOG.debug("Recieved Packet is a NetworkPacket");

            if (StringUtils.isEmpty(networkPacket.getSessionID())) {

                LOG.warn("Session ID is empty. Looking for a LoginPacket...");
                if (msg instanceof LoginPacket) {

                    LOG.debug("Login Packet received!");
                    ATCLoginHandler.handleLogin(channel, msg);

                }
                LOG.warn("SessionID empty and no LoginPacket.... returning.");
                return;
            }

            if (msg instanceof RequestServerMetrics) {

                LOG.debug("Server received ServerMetrics Request");
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

                ServerSyncHandler.handleServerSyncRequest(ctx.channel(), p.getDataSetToSync());
                return;

            }

            if (msg instanceof SupportedAirportStation) {
                LOG.debug("Incoming SupportedAirportStation");
                SupportedAirportStation airport = (SupportedAirportStation) msg;
                SetupATCHandler.handleAirportSetup(airport, ctx.channel());
                return;
            }

            if (msg instanceof SupportedFirStation) {
                LOG.debug("Incoming SupportedAirportStation");
                SupportedFirStation fir = (SupportedFirStation) msg;
                SetupATCHandler.handleFirSetup(fir, ctx.channel());
                return;

            }
        

                //TRACKDATAINNB_START
                //itemName="atcRevokesPilotsFlightPlan"
                //comment="server receives FlightPlanActionPacket and puts it into mq"
                //step=2
                //itemType="inside" 
                //className=ATCServerHandler
                //methodName="na"
                //TRACKDATAINNB_STOP
            if (msg instanceof SubmittedFlightPlansActionPacket) {
                SubmittedFlightPlansActionPacket action = (SubmittedFlightPlansActionPacket) msg;
                ServerConfig.getMessageSenders().get("submittedFlightPlanActionsATC").sendObjectMessage(action);
                return;
            }
            if (msg instanceof RequestDataStructuresPacket) {

                LOG.debug("data structures sync request incoming......");
                LOG.debug("Assembling data structure response packet...");
                ServerSyncHandler.handleStructuresSyncRequest(ctx.channel());
                return;
            }
            if (msg instanceof RequestATCStructure) {
                RequestATCStructure r = (RequestATCStructure) msg;
                ServerSyncHandler.handleSingleATCStructureRequest(ctx.channel(), r.getStructureSessionID());
                return;
            }
            if (msg instanceof RequestPilotStructure) {
                RequestPilotStructure r = (RequestPilotStructure) msg;
                ServerSyncHandler.handleSinglePilotStructureRequest(ctx.channel(), r.getStructureSessionID());
                return;
            }
            if (msg instanceof RequestPlanePositionSync) {
                RequestPlanePositionSync s = (RequestPlanePositionSync) msg;
                ServerSyncHandler.syncPlanePositionsOfPilot(ctx.channel(), s.getStructureSessionID());
                return;
            }

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        SessionManagement.removeATCSessionByChannel(ctx.channel());

        ATCStructure s = SessionManagement.findATCStructureByChannelID(ctx.channel().id().asLongText());

        if (s != null) {
            LOG.debug("boradcasting lost atcstructure to all stations");
            RemoveATCStructure removePacket = new RemoveATCStructure();
            removePacket.setStrucutureSessionID(s.getStructureSessionID());
            SessionManagement.getAtcDataStructures().remove(s.getStructureSessionID());
            NetworkBroadcaster.broadcastAll(removePacket);
            
        }
        LOG.info("client disconnected");

        channel.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("Client connected");
        this.channel = ctx.channel();

        //Die Session wird allerdinsg vom Login handler zugefuegt
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        LOG.error("SERVER EXCEPTION CAUGHT!!!!!!!!!!!!!!!!!");
        cause.printStackTrace(System.err);
        LOG.error(cause.getLocalizedMessage());

        SessionManagement.removeATCSessionByChannel(ctx.channel());

        channel.close();
    }

}
