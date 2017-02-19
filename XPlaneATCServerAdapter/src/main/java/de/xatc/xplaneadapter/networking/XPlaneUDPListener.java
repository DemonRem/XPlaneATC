/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter.networking;

import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.networkpackets.client.FMSPlan;
import de.xatc.commons.networkpackets.client.FMSWayPoint;
import de.xatc.commons.networkpackets.client.PlanePosition;
import de.xatc.xplaneadapter.config.AdapterConfig;
import de.xatc.xplaneadapter.config.ConfigBean;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class XPlaneUDPListener extends Thread {

    private boolean running = true;
    private int packetCounter = 0;
    ConfigBean configBean;
    private DatagramSocket serverSocket;
    private FMSPlan oldFMSPlan;

    public XPlaneUDPListener() {

        configBean = AdapterConfig.getConfigBean();

    }

    @Override
    public void run() {

        if (configBean == null) {

            SwingTools.alertWindow("Could not find configuration. Exit", AdapterConfig.getMainFrame());
            return;

        }

        try {
            serverSocket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(configBean.getXplaneListnerIP(), Integer.parseInt(configBean.getXplaneListenerPort()));
            serverSocket.bind(address);

            byte[] receiveData = new byte[65536];

            System.out.printf("Listening on udp:%s:%d%n",
                    InetAddress.getLocalHost().getHostAddress(), 8080);
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            AdapterConfig.setListeningToXPlane(true);

            AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getListenToXPlaneIcon().setColor(Color.YELLOW);
            AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
            AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();

            while (running) {
                serverSocket.receive(receivePacket);
                this.packetCounter++;
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getListenToXPlaneIcon().setColor(Color.GREEN);
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
                AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();

                String sentence = new String(receivePacket.getData(), 0,
                        receivePacket.getLength());
                System.out.println("RECEIVED: " + sentence);

                if (sentence.equals("SHUTDOWN")) {
                    this.closeSocket();
                    break;
                }

                System.out.println("Writing to Server");

                System.out.println("new PlanePosition");

                this.appendPathToFile(sentence);
                PlanePosition p = new PlanePosition();

                String[] split = sentence.split("\\|\\|\\|");

                String planePos = split[0];

                String[] planePosSplit = planePos.split(" ");
                if (planePosSplit.length < 12) {
                    System.out.println("arraysize of UPD Xplane packet does not fit");
                    continue;
                }

                p.setUserName(AdapterConfig.getCurrentConnectionName());
                p.setLatitude(planePosSplit[0]);
                p.setLongitude(planePosSplit[1]);
                p.setAltitude(planePosSplit[2]);
                p.setGpsSpeed(planePosSplit[3]);
                p.setWeigth(planePosSplit[4]);
                p.setGroundSpeed(planePosSplit[5]);
                p.setTransponder(planePosSplit[6]);
                p.setTransponderMode(planePosSplit[7]);
                p.setHeading(planePosSplit[8]);
                p.setTruePhi(planePosSplit[9]);
                p.setTrueTheta(planePosSplit[10]);
                p.setCom1Freq(planePosSplit[11]);

                System.out.println("DATACLIENT: " + AdapterConfig.getDataClient());

                if (AdapterConfig.getDataClient() != null) {
                    AdapterConfig.getDataClient().writeMessage(p);
                }
                if (split.length != 2) {
                    System.out.println("continuing due to wrong split length! Length= " + split.length);
                    continue;
                }
                String fmsPlan = split[1];
                if (!StringUtils.isEmpty(fmsPlan)) {

                    FMSPlan plan = new FMSPlan();

                    String[] fmsSplit = fmsPlan.split("\\*\\*\\*");
                    for (String s : fmsSplit) {

                        String[] fmsEntrySplit = s.split("\\*\\*");

                        if (fmsEntrySplit.length != 6) {
                            break;
                        }

                        FMSWayPoint w = new FMSWayPoint();

                        String fmsOutType = fmsEntrySplit[0];
                        String fmsOutID = fmsEntrySplit[1];
                        String outRef = fmsEntrySplit[2];
                        String outAltitude = fmsEntrySplit[3];
                        String outLat = fmsEntrySplit[4];
                        String outLon = fmsEntrySplit[5];
                        w.setAltitude(outAltitude);
                        w.setId(fmsOutID);
                        w.setLatitude(outLat);
                        w.setLongitude(outLon);
                        w.setName(outRef);
                        w.setRemark(fmsOutType);
                        plan.getWayPointList().put(outRef,w);

                    }
                    System.out.println(plan.hashCode());
                    System.out.println("flightPlan created!");
                    if (AdapterConfig.getDataClient() != null) {
                        
                        
                        
                        if (!this.isEqualFMSPlans(plan, this.oldFMSPlan)) {
                            System.out.println("Sending FlightPlan");
                            AdapterConfig.getDataClient().writeMessage(plan);
                        } else {
                            System.out.println("No Changes in FMSPlan. Not sending....");
                        }
                        this.oldFMSPlan = plan;
                    }

                }
            }

            AdapterConfig.setListeningToXPlane(false);

            serverSocket.close();
            System.out.println("XPlane Socket closed");

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void closeSocket() {
        System.out.println("Closing Socket...");
        this.running = false;
        this.serverSocket.disconnect();
        this.serverSocket.close();
        AdapterConfig.setXplaneUDPListener(null);
        System.out.println("Socket closed..");
        AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().getListenToXPlaneIcon().setColor(Color.RED);
        AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().revalidate();
        AdapterConfig.getMainFrame().getMainPanel().getConnectionStatusPanel().repaint();

    }

    public boolean isListening() {
        return running;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void appendPathToFile(String s) {

        if (!AdapterConfig.getMainFrame().getRecordFligtItem().isSelected()) {
            return;
            
        }
        String fileName = AdapterConfig.getMainFrame().getRecordFileName().getText();
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        
        
        try {
            File pathFile = new File(AdapterConfig.getRecordingDirectory() + File.separator + fileName + ".txt");
           

            System.out.println("writing line!");
            FileWriter fw = new FileWriter(pathFile, true); //the true will append the new data
            fw.write(s + "\n");//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }
    
    private boolean isEqualFMSPlans(FMSPlan plan, FMSPlan old) {
        
        if (old == null) {
            return false;
        }
        
        if ((plan.getWayPointList().size() != old.getWayPointList().size()) || (plan == null && old!= null) || (plan != null && old== null)) {
            return false;
        }
        for (Entry<String,FMSWayPoint> entry : old.getWayPointList().entrySet()) {
            
            if (!plan.getWayPointList().containsKey(entry.getKey())) {
                return false;
            }
            
        }
        for (Entry<String,FMSWayPoint> entry : plan.getWayPointList().entrySet()) {
            
            if (!old.getWayPointList().containsKey(entry.getKey())) {
                return false;
            }
            
        }
        
      
        return true;
    }
    
    
}
