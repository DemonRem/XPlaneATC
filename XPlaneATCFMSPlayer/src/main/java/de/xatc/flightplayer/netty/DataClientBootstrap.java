/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.netty;

import de.mytools.tools.swing.SwingTools;
import de.xatc.flightplayer.config.PlayerConfig;
import de.xatc.flightplayer.playerthread.PlayerControlThread;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.net.ConnectException;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataClientBootstrap {

    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    private Bootstrap bootstrap;
    private DataClient client;
    
    
    
    private String serverAddress;
    private int serverPort;
    private String userName;
    private String passWord;
    private PlayerControlThread controlThread;
    
    public DataClientBootstrap(String server, int port, String username, String password, PlayerControlThread thread) {

        this.serverAddress = server;
        this.serverPort = port;
        this.userName = username;
        this.passWord = password;
        this.controlThread = thread;
                
        initClient();
    }

    public void initClient() {

        workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap(); // (2)
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class); // (3)

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    client = new DataClient(controlThread);

                    
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(client);

                }
            });
            System.out.println("Connecting......");

            channelFuture = bootstrap.connect(this.serverAddress, this.serverPort).sync();

          
            System.out.println("Connected!");
            if (channelFuture.isSuccess()) {

                
                
            } else {
                System.out.println("NO CONNECTION SUCCESSFULL");
            }

        } catch (InterruptedException ex) {
            System.out.println("EXCEPTOIN");
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            if (ex instanceof ConnectException) {
                SwingTools.alertWindow("Could not connect to ATC-Server.", PlayerConfig.getMainFrame());
            }
        }

    }

    public void shutdownClient() {

        ClientShutDownThread t = new ClientShutDownThread(client, channelFuture, workerGroup);
        t.start();

    }

    public DataClient getClient() {
        return client;
    }

    public void setClient(DataClient client) {
        this.client = client;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

}
