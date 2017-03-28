/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 31.05.2016
 */
package de.xatc.controllerclient.config;

import de.mytools.encoding.ObjectsMarshaller;
import de.mytools.tools.swing.SwingTools;
import de.xatc.commons.beans.sharedgui.ChatFrame;
import de.xatc.controllerclient.gui.FlightPlanStrips.SubmittedFlightPlansFrame;
import de.xatc.controllerclient.gui.config.ConnectionConfigFrame;
import de.xatc.controllerclient.gui.config.FolderPropertiesFrame;
import de.xatc.controllerclient.gui.connect.ConnectFrame;
import de.xatc.controllerclient.gui.datasync.ServerSyncFrame;
import de.xatc.controllerclient.gui.main.MainFrame;
import de.xatc.controllerclient.gui.metrics.MetricsFrame;
import de.xatc.controllerclient.gui.servercontrol.FileIndexerFrame;
import de.xatc.controllerclient.gui.servercontrol.ServerControlFrame;
import de.xatc.controllerclient.gui.setupatc.ATCSetupFrame;
import de.xatc.controllerclient.gui.usercontrol.UserControlFrame;
import de.xatc.controllerclient.log.DebugMessageLevel;
import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import de.xatc.controllerclient.network.DataClient;
import de.xatc.controllerclient.network.DataClientBootstrap;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class XHSConfig {

    
    private static DataClient dataClient;
    private static DataClientBootstrap dataClientBootstrap;
    private static String currentSessionID;
    private static String currentChannelID;
    
    private static MainFrame mainFrame;
    private static final String propertiesFileName = "atcClient.properties";
    
    
    //WINDOWS
    private static ConnectionConfigFrame configFrame;
    private static ConnectFrame connectFrame;
    private static MetricsFrame metricsFrame;
    private static ServerControlFrame serverControlFrame;
    private static UserControlFrame userControlFrame;
    private static ServerSyncFrame serverSyncFrame;
    private static FileIndexerFrame fileIndexerFrame;
    private static FolderPropertiesFrame folderPropertiesFrame;
    private static ATCSetupFrame atcSetupFrame;
    private static ChatFrame chatFrame;
    private static SubmittedFlightPlansFrame myFlightPlansFrame;
    private static SubmittedFlightPlansFrame submittedFlightPlansPoolFrame;
    
    private static boolean aptIndexingRunning = false;
    
    /**
     * initial mapZoomFactor
     */
    private static final String initialMapZoomFactor = "0";
    /**
     * initial latitude
     */
    private static final String initialLatitude = "040.052.00.00";

    /**
     * intital longitude
     */
    private static final String intitalLongitude = "034.034.00.00";

    /**
     * initial Position
     */
    private static NavPoint initialPos = null;

    /**
     * my current applictation version
     */
    private final static String VERSION = "0.1-alpha";

    /**
     * what is my name
     */
    private final static String APPNAME = "XPlane Home Server";

    /**
     * last updated
     */
    private static final String lastUpdated = "5/30/2016 14:7";

    /**
     * debug and write a lot of debug messages to stdout
     */
    private static final boolean doDebug = true;

    /**
     * debug message level
     */
    private static final DebugMessageLevel debugLevel = DebugMessageLevel.INFO;

    /**
     * hibernate loglevel. normally set to off, because of large log output
     */
    private final static Level hibernateLogLevel = Level.WARNING;
    /**
     * loglevel of ehcache
     */
    private final static Level ehCacheLogLevel = Level.WARNING;

    /**
     * This is me
     */
    private static final String authorString = "Mirko Bubel";

    
    /**
     * the lock file to ensure that only one instance is running
     */
    private final static File lockFile = new File("./.homeserverLock");

    /**
     * the circumference of the earth (meters)
     */
    private final static double earthCircumference = 40075016.686;
    
    /**
     * this minumum visibility range of an airport station
     */
    private final static int minAirportVisRange = 60;
    
    /**
     * this maximum visibility range of an airport station
     */
    private final static int maxAirportVisRange = 1200;
    
    /**
     * meters per nautical mile (needed for range calculation meters per pixel)
     */
    private final static int metersPerNauticalMile = 1852;
    
    /**
     * config Bean
     *
     */
    private static ConfigBean configBean = null;

    public static boolean isDoDebug() {
        return doDebug;
    }

    public static DebugMessageLevel getDebugLevel() {
        return debugLevel;
    }

    public static Level getHibernateLogLevel() {
        return hibernateLogLevel;
    }

    public static Level getEhCacheLogLevel() {
        return ehCacheLogLevel;
    }

    public static String getAuthorString() {
        return authorString;
    }

    public static String getVERSION() {
        return VERSION;
    }

    public static String getAPPNAME() {
        return APPNAME;
    }

    public static String getLastUpdated() {
        return lastUpdated;
    }

    public static NavPoint getInitialPos() {
        if (initialPos == null) {
            initialPos = new NavPoint();
            
            String lat = XHSConfig.initialLatitude;
            String lon = XHSConfig.intitalLongitude;
            String hdg = "0";
            
            System.out.println("SETTING NEW INITIAL NAVPOINT");
            initialPos.setLatitudeSTring(lat);
            initialPos.setLongitudeSTring(lon);
            initialPos.setHeadingString(hdg);
            initialPos.setHeadingDouble(Double.parseDouble(hdg));

            XHSConfig.debugMessage("Starting with position: " + lat + " " + lon + " " + hdg, DebugMessageLevel.INFO);
            initialPos = NavPointHelpers.convertToXY(initialPos);
        }
        return initialPos;
    }

    public static void setInitialPos(NavPoint initalPos) {
        XHSConfig.initialPos = initalPos;
    }

    public static File getLockFile() {
        return lockFile;
    }

    /**
     * write an debug Message
     *
     * @param s
     */
    public static void debugMessage(String s, DebugMessageLevel level) {

        if (!doDebug) {
            return;
        }

        if (level.getLevel() <= XHSConfig.getDebugLevel().getLevel()) {

            System.out.println(s);

        }

    }

    public static ConfigBean getConfigBean() {
        if (configBean == null) {
            loadPropsFile();
        }
        return configBean;
    }

    public static void setConfigBean(ConfigBean configBean) {
        XHSConfig.configBean = configBean;
    }

    public static String getInitialLatitude() {
        return initialLatitude;
    }

    public static String getInitialMapZoomFactor() {
        return initialMapZoomFactor;
    }

    public static String getIntitalLongitude() {
        return intitalLongitude;
    }

    public static DataClient getDataClient() {
        return dataClient;
    }

    public static void setDataClient(DataClient dataClient) {
        XHSConfig.dataClient = dataClient;
    }

    public static DataClientBootstrap getDataClientBootstrap() {
        return dataClientBootstrap;
    }

    public static void setDataClientBootstrap(DataClientBootstrap dataClientBootstrap) {
        XHSConfig.dataClientBootstrap = dataClientBootstrap;
    }

    public static String getCurrentSessionID() {
        return currentSessionID;
    }

    public static void setCurrentSessionID(String currentSessionID) {
        XHSConfig.currentSessionID = currentSessionID;
    }

    public static String getCurrentChannelID() {
        return currentChannelID;
    }

    public static void setCurrentChannelID(String currentChannelID) {
        XHSConfig.currentChannelID = currentChannelID;
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        XHSConfig.mainFrame = mainFrame;
    }

    public static ConnectionConfigFrame getConfigFrame() {
        return configFrame;
    }

    public static void setConfigFrame(ConnectionConfigFrame configFrame) {
        XHSConfig.configFrame = configFrame;
    }
    
    
    /**
     * save a marshelled config bean
     */
    public static void savePropsFile() {
        XHSConfig.debugMessage("Saving Props File", DebugMessageLevel.DEBUG);

        XHSConfig.debugMessage("SAVING PROPERTIES", DebugMessageLevel.DEBUG);

        String configBeanContent = ObjectsMarshaller.objectToXml(configBean);
        XHSConfig.debugMessage(configBeanContent, DebugMessageLevel.DEBUG);
        try {
            FileUtils.writeStringToFile(new File(XHSConfig.getPropertiesFileName()), configBeanContent);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            SwingTools.alertWindow("Could not save Properties File!", mainFrame);
        }

    }
    
    /**
     * load the properties File
     */
    public static void loadPropsFile() {
        
        XHSConfig.debugMessage("loading ConfigBean", DebugMessageLevel.DEBUG);
        
        if (!doesConfigFileExist()) {
            setConfigBean(new ConfigBean());
            return;
        }
        
        try {
            String configString = FileUtils.readFileToString(new File(XHSConfig.getPropertiesFileName()));
            ConfigBean bean = (ConfigBean) ObjectsMarshaller.xmlToObject(configString);
            setConfigBean(bean);
        } catch (IOException ex) {
            setConfigBean(null);
        }
        
    }

    public static String getPropertiesFileName() {
        return propertiesFileName;
    }
    
     /**
     * check if ConfigFileExists
     */
    public static boolean doesConfigFileExist() {
        
        File configFile = new File(XHSConfig.getPropertiesFileName());
        return configFile.exists();
        
    }

    public static ConnectFrame getConnectFrame() {
        return connectFrame;
    }

    public static void setConnectFrame(ConnectFrame connectFrame) {
        XHSConfig.connectFrame = connectFrame;
    }

    public static MetricsFrame getMetricsFrame() {
        return metricsFrame;
    }

    public static void setMetricsFrame(MetricsFrame metricsFrame) {
        XHSConfig.metricsFrame = metricsFrame;
    }

    public static ServerControlFrame getServerControlFrame() {
        return serverControlFrame;
    }

    public static void setServerControlFrame(ServerControlFrame serverControlFrame) {
        XHSConfig.serverControlFrame = serverControlFrame;
    }

    public static UserControlFrame getUserControlFrame() {
        return userControlFrame;
    }

    public static void setUserControlFrame(UserControlFrame userControlFrame) {
        XHSConfig.userControlFrame = userControlFrame;
    }

    public static ServerSyncFrame getServerSyncFrame() {
        return serverSyncFrame;
    }

    public static void setServerSyncFrame(ServerSyncFrame serverSyncFrame) {
        XHSConfig.serverSyncFrame = serverSyncFrame;
    }

    public static boolean isAptIndexingRunning() {
        return aptIndexingRunning;
    }

    public static void setAptIndexingRunning(boolean aptIndexingRunning) {
        XHSConfig.aptIndexingRunning = aptIndexingRunning;
    }

    public static FileIndexerFrame getFileIndexerFrame() {
        return fileIndexerFrame;
    }

    public static void setFileIndexerFrame(FileIndexerFrame fileIndexerFrame) {
        XHSConfig.fileIndexerFrame = fileIndexerFrame;
    }

    public static FolderPropertiesFrame getFolderPropertiesFrame() {
        return folderPropertiesFrame;
    }

    public static void setFolderPropertiesFrame(FolderPropertiesFrame folderPropertiesFrame) {
        XHSConfig.folderPropertiesFrame = folderPropertiesFrame;
    }

    public static ATCSetupFrame getAtcSetupFrame() {
        return atcSetupFrame;
    }

    public static void setAtcSetupFrame(ATCSetupFrame atcSetupFrame) {
        XHSConfig.atcSetupFrame = atcSetupFrame;
    }

    public static double getEarthCircumference() {
        return earthCircumference;
    }

    public static int getMinAirportVisRange() {
        return minAirportVisRange;
    }

    public static int getMaxAirportVisRange() {
        return maxAirportVisRange;
    }

    public static int getMetersPerNauticalMile() {
        return metersPerNauticalMile;
    }

    public static ChatFrame getChatFrame() {
        return chatFrame;
    }

    public static void setChatFrame(ChatFrame chatFrame) {
        XHSConfig.chatFrame = chatFrame;
    }

    public static SubmittedFlightPlansFrame getMyFlightPlans() {
        return myFlightPlansFrame;
    }

    public static void setMyFlightPlans(SubmittedFlightPlansFrame myFlightPlans) {
        XHSConfig.myFlightPlansFrame = myFlightPlans;
    }

    public static SubmittedFlightPlansFrame getSubmittedFlightPlansPoolFrame() {
        return submittedFlightPlansPoolFrame;
    }

    public static void setSubmittedFlightPlansPoolFrame(SubmittedFlightPlansFrame submittedFlightPlansPoolFrame) {
        XHSConfig.submittedFlightPlansPoolFrame = submittedFlightPlansPoolFrame;
    }
    
    
    
    
    
    
    
    
    
    
    

}
