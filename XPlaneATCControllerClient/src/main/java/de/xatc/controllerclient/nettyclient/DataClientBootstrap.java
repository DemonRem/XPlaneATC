/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.controllerclient.nettyclient;

import de.mytools.tools.swing.SwingTools;
import de.xatc.controllerclient.config.XHSConfig;
import de.xatc.controllerclient.gui.config.ConnectionConfigFrame;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class DataClientBootstrap {

    private static final Logger LOG = Logger.getLogger(DataClientBootstrap.class.getName());
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;

    private Bootstrap bootstrap;
    private DataClient client;

    public DataClientBootstrap() {

        initClient();
    }

    public void initClient() {

        
        if (XHSConfig.getConfigBean().getAtcServerIP() == null) {
            SwingTools.alertWindow("No Connection Properties Found!", XHSConfig.getMainFrame());
            XHSConfig.setConfigFrame(new ConnectionConfigFrame());
            XHSConfig.getConfigFrame().setVisible(true);
            return;
        }
        workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap(); // (2)
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class); // (3)

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    DataClient client = new DataClient();

                    XHSConfig.setDataClient(client);
                    
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(client);

                }
            });
            LOG.info("Connecting......");

            channelFuture = bootstrap.connect(XHSConfig.getConfigBean().getAtcServerIP(), Integer.parseInt(XHSConfig.getConfigBean().getAtcServerPort())).sync();

            LOG.info("Connected!");
            if (channelFuture.isSuccess()) {

//                AdapterConfig.getMainFrame().getConnectMenuItem().setEnabled(false);
//                AdapterConfig.getMainFrame().getDisconnectMenuItem().setEnabled(true);
//
//                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getConnectedToXPlaneATCServerDataIcon().setColor(Color.GREEN);
//                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
//                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();
                //TODO
            } else {
                LOG.warn("NO CONNECTION SUCCESSFULL");
            }

        } catch (InterruptedException ex) {
            LOG.error("EXCEPTOIN " + ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
        } catch (Exception ex) {
            LOG.error(ex.getLocalizedMessage());
            ex.printStackTrace(System.err);
            if (ex instanceof ConnectException) {
                SwingTools.alertWindow("Could not connect to ATC-Server.", XHSConfig.getMainFrame());
            }
        }

    }

    public void shutdownClient() {

        LOG.info("DataClientBootstrap . Shutting down client!");
        Thread t = new Thread(new Runnable() {
            public void run() {
                
                LOG.info("ShutdownThread started");
                
                XHSConfig.getDataClient().getCtx().close();
                
                XHSConfig.getMainFrame().getDisconnectItem().setEnabled(false);
                XHSConfig.getMainFrame().getConnectItem().setEnabled(true);

                try {
                    LOG.info("Channel Fututre closing");
                    channelFuture.channel().closeFuture().sync();

                } catch (InterruptedException ex) {
                    LOG.error("EXCEPTION FIRED " + ex.getLocalizedMessage());
                    ex.printStackTrace(System.err);
                }

                LOG.info("Shutdown workergroup gracefully");
                workerGroup.shutdownGracefully();

                XHSConfig.getMainFrame().getMainPanel().getStatusPanel().getConnectedToATCDataServer().setColor(Color.RED);
                XHSConfig.getMainFrame().getMainPanel().getStatusPanel().revalidate();
                XHSConfig.getMainFrame().getMainPanel().getStatusPanel().repaint();

                XHSConfig.setDataClientBootstrap(null);
                XHSConfig.setDataClient(null);
                LOG.info("Shutdown complete");
                LOG.info("Disconnected");
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
