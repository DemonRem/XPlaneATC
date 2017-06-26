/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

/**
 *
 * @author c047
 */
public class ClientShutDownThread extends Thread {

    private DataClient dataClient;
    private ChannelFuture channelFuture;
    private EventLoopGroup workerGroup;

    public ClientShutDownThread(DataClient client, ChannelFuture f, EventLoopGroup w) {

        this.dataClient = client;
        this.channelFuture = f;
        this.workerGroup = w;
    }

    @Override
    public void run() {

        this.dataClient.getCtx().channel().close();
        //AdapterConfig.getDataClient().getCtx().channel().parent().close();

        try {
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        workerGroup.shutdownGracefully();

        

        System.out.println("Disconnected");

    }

}
