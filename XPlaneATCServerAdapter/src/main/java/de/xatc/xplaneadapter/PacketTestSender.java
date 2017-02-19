/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.xplaneadapter;

import de.xatc.xplaneadapter.config.AdapterConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class PacketTestSender extends Thread {

    private boolean running = true;
    private File pathFile;

    public static void main(String[] arg) {

        String fileName = AdapterConfig.getRecordingDirectory() + File.separator + arg[0] + ".txt";
        
        File pathFile = new File(fileName);
        if (!pathFile.exists()) {
            
            System.out.println("PathFile not found. Exiting");
            
        }
        
        PacketTestSender sender = new PacketTestSender();
        sender.setPathFile(pathFile);
        sender.start();
    }

    @Override
    public void run() {

        byte[] message = "47.4636354527 -122.307750265 121.297803167 0.0 1881.87329102 2.13730290852e-05 4700 1 180.329605103 1.38026595116 0.665557742119[1]['EDDK'][16791166][0][50.867828369140625][7.147380352020264][512]['DK130'][33588579][0][50.816436767578125][7.2198028564453125][4]['COL'][7853][0][50.78351974487305][7.594193935394287][512]['KERAX'][33619640][0][50.474998474121094][9.581945419311523][2]['PSA'][5101][0][49.86224365234375][9.3483247756958][2048] ['+49.885 +009.228'][-1][0][49.885406494140625][9.227511405944824][4] ['CHA'][7772][0][49.921104431152344][9.039811134338379][2048] ['+50.097 +008.940'][-1][0][50.097496032714844][8.939977645874023][512] ['REDGO'][33653160][4000][50.10916519165039][8.856389045715332][2048] ['+50.085 +008.755'][-1][2610][50.08509063720703][8.754647254943848][1] ['EDDF'][16786835][0][50.03886032104492][8.560810089111328]".getBytes();
        try {

            while (running) {
                InetAddress address = InetAddress.getByName(AdapterConfig.getConfigBean().getXplaneListnerIP());
               
                

                BufferedReader br = new BufferedReader(new FileReader(this.pathFile));

                String line = null;
                while ((line = br.readLine()) != null) {
                    byte[] msg = line.getBytes();
                     DatagramPacket packet = new DatagramPacket(msg, msg.length,
                        address, 8181);
                    DatagramSocket dsocket = new DatagramSocket();
                    System.out.println("Sending to ..." + address.getHostAddress());
                    System.out.println(line);
                    dsocket.send(packet);
                    dsocket.close();
                    Thread.sleep(1000);

                }

                br.close();

            }

        } catch (UnknownHostException ex) {
            ex.printStackTrace(System.err);
        } catch (SocketException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public File getPathFile() {
        return pathFile;
    }

    public void setPathFile(File pathFile) {
        this.pathFile = pathFile;
    }
    

}
