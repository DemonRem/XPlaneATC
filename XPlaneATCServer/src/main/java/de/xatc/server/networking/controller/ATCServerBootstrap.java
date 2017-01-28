/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.networking.controller;

import de.xatc.server.config.ServerConfig;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class ATCServerBootstrap {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    public ATCServerBootstrap() {

        initServer();
    }

    public void initServer() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            ch.pipeline().addLast(new ATCServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, ServerConfig.getMaxConnectionsAllowed()) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, ServerConfig.isKeepConnectionsAlive()); // (6)

            System.out.println("Listening for Data on " + ServerConfig.getAtcIP() + ":" + ServerConfig.getAtcPort());
            channelFuture = b.bind(ServerConfig.getAtcIP(), ServerConfig.getAtcPort()).sync(); // (7)

        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public void shutdownServer() {

        System.out.println("ATC Server Bootstrap shutdown initiated!");
        Thread t = new Thread(new Runnable() {
            public void run() {

                System.out.println("ShutdownThread started");
                

                try {
                    SessionManagement.getAtcChannelGroup().close().sync();
                    channelFuture.channel().closeFuture().sync();
                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.err);
                }
                
                System.out.println("Shutdown workergroup gracefully");
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();

                

                ServerConfig.setAtcServerBootStrap(null);

                System.out.println("Disconnected");
            }
        });
        t.start();

    }

}
