/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.flightplayer.playerthread;


import de.xatc.commons.networkpackets.pilot.FMSPlan;
import de.xatc.commons.networkpackets.pilot.FMSWayPoint;
import de.xatc.commons.networkpackets.pilot.LoginPacket;
import de.xatc.commons.networkpackets.pilot.PlanePosition;
import de.xatc.commons.networkpackets.pilot.RegisterPacket;
import de.xatc.flightplayer.config.PlayerConfig;
import de.xatc.flightplayer.credentialsgenerator.CredentialsGenerator;
import de.xatc.flightplayer.netty.DataClientBootstrap;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author c047
 */
public class PlayerControlThread extends Thread {
    
    private FMSPlan oldFMSPlan;
    private boolean createAccount;
    private File playerFile;
    private String currentUserName;
    private String currentPassword;
    private boolean running;
    private int delay;
    private int interval;
    private DataClientBootstrap bootstrap;
    private AbstractMap.Entry<String,String> credentials;
    private String sessionID;
    private String channelID;
    
    
    
    
    public PlayerControlThread(String filename, int interval) {
        
        this.interval = interval;
        System.out.println("Control Thread initializing.....");
        this.playerFile = new File(filename);
        
        
        
    }
    
    private void connect() {
        
        System.out.println("connecting");
        
        
        bootstrap = new DataClientBootstrap(PlayerConfig.getMainFrame().getPlayerPanel().getAtcServerAddressField().getText(), 
                                            Integer.parseInt(PlayerConfig.getMainFrame().getPlayerPanel().getAtcServerPort().getText()), 
                                            credentials.getKey(), credentials.getValue(),this);
        
    }
    
    
    @Override
    public void run() {
        
        System.out.println("getting credentials: .....");
        credentials = CredentialsGenerator.getCredentials();
        System.out.println(credentials.getKey() + " - " + credentials.getValue());
        if (!this.playerFileExist()) {
            return;
        }
        this.running = true;
        this.connect();
        RegisterPacket registerPacket = new RegisterPacket();
        registerPacket.setUserName(credentials.getKey());
        registerPacket.setPassword(credentials.getValue());
        
        System.out.println("sending registering packet");
        
        this.bootstrap.getClient().writeMessage(registerPacket);
        System.out.println("Registering packet send.");
        try {
            System.out.println("Sleeping");
            this.sleep(5000);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        System.out.println("shutting down client");
        this.bootstrap.shutdownClient();
        
        System.out.println("connecting again.....");
        this.connect();
        System.out.println("Sending login packet");
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setFlightNumber(credentials.getKey());
        loginPacket.setUserName(credentials.getKey());
        loginPacket.setPassword(credentials.getValue());
        
        
        this.bootstrap.getClient().writeMessage(loginPacket);
        System.out.println("login packet sent");
        
        try {
            System.out.println("Sleeping again");
            this.sleep(5000);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        
        try {
            LineIterator it = FileUtils.lineIterator(this.playerFile, "UTF-8");
            
            while (it.hasNext()) {
                
                String line = it.nextLine();
                System.out.println(line);
                this.parseLine(line);
                sleep(interval * 1000);
                
            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } catch (InterruptedException ex) {
            Logger.getLogger(PlayerControlThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("Thread end!");
        bootstrap.shutdownClient();
    }
    
    
    private void parseLine (String line) {
        
                        PlanePosition p = new PlanePosition();

                String[] split = line.split("\\|\\|\\|");

                String planePos = split[0];

                String[] planePosSplit = planePos.split(" ");
                if (planePosSplit.length < 12) {
                    System.out.println("arraysize of UPD Xplane packet does not fit");
                    return;
                }

                p.setUserName(credentials.getKey());
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

               

                if (this.bootstrap.getClient() != null) {
                    bootstrap.getClient().writeMessage(p);
                }
                if (split.length != 2) {
                    System.out.println("continuing due to wrong split length! Length= " + split.length);
                    return;
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
                    if (bootstrap.getClient() != null) {
                        
                        
                        
                        if (!this.isEqualFMSPlans(plan, this.oldFMSPlan)) {
                            System.out.println("Sending FlightPlan");
                            bootstrap.getClient().writeMessage(plan);
                        } else {
                            System.out.println("No Changes in FMSPlan. Not sending....");
                        }
                        this.oldFMSPlan = plan;

                    }
        
                }
                    
        
        
        
    }
    
    
    private boolean playerFileExist() {
        
        return this.playerFile.exists();
        
    }

    public boolean isCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(boolean createAccount) {
        this.createAccount = createAccount;
    }

    public File getPlayerFile() {
        return playerFile;
    }

    public void setPlayerFile(File playerFile) {
        this.playerFile = playerFile;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public DataClientBootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(DataClientBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Map.Entry<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map.Entry<String, String> credentials) {
        this.credentials = credentials;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }
    

   private boolean isEqualFMSPlans(FMSPlan plan, FMSPlan old) {
        
        if (old == null) {
            return false;
        }
        
        if ((plan.getWayPointList().size() != old.getWayPointList().size()) || (plan == null && old!= null) || (plan != null && old== null)) {
            return false;
        }
        for (Map.Entry<String,FMSWayPoint> entry : old.getWayPointList().entrySet()) {
            
            if (!plan.getWayPointList().containsKey(entry.getKey())) {
                return false;
            }
            
        }
        for (Map.Entry<String,FMSWayPoint> entry : plan.getWayPointList().entrySet()) {
            
            if (!old.getWayPointList().containsKey(entry.getKey())) {
                return false;
            }
            
        }
        
      
        return true;
    }    
    
}
