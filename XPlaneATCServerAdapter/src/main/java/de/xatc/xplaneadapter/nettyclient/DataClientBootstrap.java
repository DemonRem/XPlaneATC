/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.nettyclient;

import de.mytools.tools.swing.SwingTools;
import de.xatc.xplaneadapter.config.AdapterConfig;
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
import java.awt.Color;
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

    public DataClientBootstrap() {

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
                    DataClient client = new DataClient();

                    AdapterConfig.setDataClient(client);
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(client);

                }
            });
            System.out.println("Connecting......");

            channelFuture = bootstrap.connect(AdapterConfig.getConfigBean().getAtcServerName(), Integer.parseInt(AdapterConfig.getConfigBean().getAtcServerPort())).sync();

          
            System.out.println("Connected!");
            if (channelFuture.isSuccess()) {

                AdapterConfig.getMainFrame().getConnectMenuItem().setEnabled(false);
                AdapterConfig.getMainFrame().getDisconnectMenuItem().setEnabled(true);

                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getConnectedToXPlaneATCServerDataIcon().setColor(Color.GREEN);
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();
            } else {
                System.out.println("NO CONNECTION SUCCESSFULL");
            }

        } catch (InterruptedException ex) {
            System.out.println("EXCEPTOIN");
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            if (ex instanceof ConnectException) {
                SwingTools.alertWindow("Could not connect to ATC-Server.", AdapterConfig.getConnectFrame());
            }
        }

    }

    public void shutdownClient() {

        Thread t = new Thread(new Runnable() {
            public void run() {
                AdapterConfig.getMainFrame().getDisconnectMenuItem().setEnabled(false);
                AdapterConfig.getMainFrame().getConnectMenuItem().setEnabled(true);

                AdapterConfig.getDataClient().getCtx().channel().close();
                //AdapterConfig.getDataClient().getCtx().channel().parent().close();

                try {
                    channelFuture.channel().closeFuture().sync();

                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.err);
                }

                workerGroup.shutdownGracefully();

                AdapterConfig.setDataClient(null);
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getConnectedToXPlaneATCServerDataIcon().setColor(Color.RED);
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();
                System.out.println("Disconnected");
            }
        });
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
