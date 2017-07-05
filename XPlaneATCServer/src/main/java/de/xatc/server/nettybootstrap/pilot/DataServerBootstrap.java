/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.nettybootstrap.pilot;

import de.xatc.server.config.ServerConfig;
import de.xatc.server.sessionmanagment.SessionManagement;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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
import java.util.Map.Entry;
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataServerBootstrap {

    private static final Logger LOG = Logger.getLogger(DataServerBootstrap.class.getName());
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    public DataServerBootstrap() {

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
                            ch.pipeline().addLast(new DataServerHandler());
                            
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, ServerConfig.getMaxConnectionsAllowed()) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, ServerConfig.isKeepConnectionsAlive()); // (6)

            LOG.info("Listening for Data on " + ServerConfig.getDataIP() + ":" + ServerConfig.getDataPort());
            channelFuture = b.bind(ServerConfig.getDataIP(), ServerConfig.getDataPort()).sync(); // (7)

        } catch (InterruptedException ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
        }

    }

    public void shutdownServer() {

       LOG.info("ATC Server Bootstrap shutdown initiated!");
        Thread t = new Thread(new Runnable() {
            public void run() {

                LOG.info("ShutdownThread started");
                

                try {
                         for (Entry<String,Channel> entry : SessionManagement.getPilotChannels().entrySet()) {
                        
                        entry.getValue().close().sync();
                        
                    }
                    
                    SessionManagement.getPilotDataStructures().clear();
                    channelFuture.channel().closeFuture().sync();
                    
                } catch (InterruptedException ex) {
                    LOG.error(ex.getLocalizedMessage());
                    ex.printStackTrace(System.err);
                }
                
                LOG.info("Shutdown workergroup gracefully");
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();

                

                ServerConfig.setDataServerBootStrap(null);

                LOG.info("Disconnected");
            }
        });
        t.start();

    }

}
