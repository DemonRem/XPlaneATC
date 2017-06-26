/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.netty;

import de.xatc.commons.networkpackets.parent.NetworkPacket;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.commons.networkpackets.pilot.TextMessagePacket;
import de.xatc.flightplayer.playerthread.PlayerControlThread;
import de.xatc.flightplayer.playerthread.protocolhandler.IncomingTextMessageHandler;
import de.xatc.flightplayer.playerthread.protocolhandler.LoginAnswerHandler;
import de.xatc.flightplayer.playerthread.protocolhandler.RegisterAnwerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataClient extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;
    private PlayerControlThread controlThread;

    public DataClient(PlayerControlThread t) {
        this.controlThread = t;
    }
    
    public void writeMessage(Object msg) {

        if (msg instanceof LoginPacket) {
            sendPacket(msg);
        } else if (msg instanceof RegisterPacket) {
            sendPacket(msg);
        } else {

            if (StringUtils.isEmpty(controlThread.getSessionID())) {
                System.out.println("No SessionID, could not send packet");
                return;
            }
            if (StringUtils.isEmpty(controlThread.getChannelID())) {
                System.out.println("No ChannelID, could not send packet");
                return;
            }

            NetworkPacket p = (NetworkPacket) msg;
            p.setChannelID(controlThread.getChannelID());
            p.setSessionID(controlThread.getSessionID());
            
         
            
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
        if (msg instanceof RegisterPacket) {

            RegisterAnwerHandler.handlePacket(msg,controlThread);

        } else if (msg instanceof LoginPacket) {
            System.out.println("INCOMING LoginPacket");
            LoginAnswerHandler.handleLoginAnswer(msg,controlThread);
            
        }
        else if (msg instanceof TextMessagePacket) {
            System.out.println("client received TextMessagePacket");
            IncomingTextMessageHandler.handleIncomingTextMessage(msg,controlThread);
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
