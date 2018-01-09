/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.gui.airportdrawing.file;

import de.mytools.tools.swing.SwingTools;
import de.twyhelper.tools.FMCConfig;
import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.commons.db.sharedentities.aptmodel.AptFileEntity;
import de.xatc.commons.db.sharedentities.aptmodel.IndexUpdatedEntity;
import de.xatc.commons.db.sharedentities.aptmodel.NavDataEntity;
import de.xatc.controllerclient.db.DBSessionManager;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * This file scans the xplane folder on hard disk and passes found apt.dat files
 * to the parser
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptFileIndexer extends Thread {

    /**
     * total file counter
     */
    private int filesToIndex = 0;

    /**
     * current file
     */
    private int currentFileCounter = 0;

    /**
     * overwritten thread method
     */
    @Override
    public void run() {

        if (StringUtils.isEmpty(FMCConfig.getConfigBean().getFolder_xplaneFolder())) {
            SwingTools.alertWindow("Could not find X-Plane Directory. Indexing aborted!", FMCConfig.getMainFrame());
            return;
        }
        //first we delete all stuff from database

        if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptStatusLabel().setText("Deleting old data...");
        }

        Session session = DBSessionManager.getSession();

        Transaction t = session.beginTransaction();
        Query deleteAirportsQ = session.createQuery("delete from AptAirport");
        deleteAirportsQ.executeUpdate();
        t.commit();
        session.flush();

        Transaction t1 = session.beginTransaction();
        Query deleteFilesQ = session.createQuery("delete from AptFileEntity");
        deleteFilesQ.executeUpdate();
        t1.commit();
        session.flush();

        Transaction t2 = session.beginTransaction();
        Query deleteFreqQ = session.createQuery("delete from NavDataEntity");
        deleteFreqQ.executeUpdate();
        t2.commit();
        session.flush();

        Transaction t3 = session.beginTransaction();
        Query q = session.createQuery("delete from IndexUpdatedEntity");
        q.executeUpdate();
        t3.commit();
        session.flush();
        DBSessionManager.closeSession();

        FMCConfig.setAptIndexingRunning(true);

        if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptStatusLabel().setText("Searching apt-files...");
        }

        Collection<File> aptFiles = FileUtils.listFiles(new File(FMCConfig.getConfigBean().getFolder_xplaneFolder()), new String[]{"dat"}, true);

        session = DBSessionManager.getSession();
        for (File f : aptFiles) {

            if (f.getAbsolutePath().contains(".svn")) {
                continue;
            }
            if (f.getAbsolutePath().contains(".git")) {
                continue;
            }

            if (f.getName().equals("apt.dat")) {

                try {

                    AptFileEntity e = new AptFileEntity();
                    e.setFileName(f.getAbsolutePath());
                    session.saveOrUpdate(e);
                    session.flush();

                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }

            }

        }
        DBSessionManager.closeSession();

        this.splitAptFiles();

        session = DBSessionManager.getSession();
        long airportCounter = session.createCriteria(AptAirport.class).list().size();
        long filesCounter = session.createCriteria(AptFileEntity.class).list().size();
        DBSessionManager.closeSession();

       // System.out.println("*******************************************");
       // System.out.println("Airports: " + airportCounter);
       // System.out.println("files: " + filesCounter);

        FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setValue(0);
        FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptStatusLabel().setText("Parsing earth_nav.dat");
        FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptFilesLineCounterLabel().setText(" ");

        try {
            this.parseFreqs();
        } catch (IOException ex) {
            System.out.println("Could not parse earth_nav.dat data." + ex.getLocalizedMessage());
        }

        IndexUpdatedEntity updated = new IndexUpdatedEntity();

        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();

        updated.setUpdateDate(new java.sql.Timestamp(now.getTime()));

        session = DBSessionManager.getSession();
        session.saveOrUpdate(updated);
        session.flush();
        DBSessionManager.closeSession();

        session = DBSessionManager.getSession();
        List<IndexUpdatedEntity> list = session.createCriteria(IndexUpdatedEntity.class).list();
        for (IndexUpdatedEntity i : list) {
            //System.out.println("TIME UPDATED: " + i.getUpdateDate().toString());

        }
        DBSessionManager.closeSession();

        SwingTools.alertWindow("Indexing successful!", FMCConfig.getMainFrame());
        if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setValue(0);
            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptStatusLabel().setText(" ");
            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptFilesLineCounterLabel().setText(" ");

        }
        FMCConfig.setAptIndexingRunning(false);
        FMCConfig.getMainFrame().getGotoAirportTextField().setEnabled(true);

    }

    /**
     * split the found apt.dat files and call the parser
     */
    private void splitAptFiles() {

        Session session = DBSessionManager.getSession();
        List<AptFileEntity> fileList = session.createCriteria(AptFileEntity.class).list();

        this.filesToIndex = fileList.size();

        if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setMinimum(0);
            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setMaximum(this.filesToIndex);
            FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setValue(0);
        }

        for (AptFileEntity aptFileEntity : fileList) {

            this.currentFileCounter++;

            if (FMCConfig.getMainFrame().getMainPanel().getToolPanel() != null) {

                FMCConfig.getMainFrame().getMainPanel().getToolPanel().getProgressBar().setValue(this.currentFileCounter);
                FMCConfig.getMainFrame().getMainPanel().getToolPanel().getAptStatusLabel().setText("Indexing " + this.currentFileCounter + "/" + this.filesToIndex);
            }

            if (StringUtils.isEmpty(aptFileEntity.getFileName())) {
                continue;
            }

            File aFile = new File(aptFileEntity.getFileName());
            if (!aFile.exists()) {
                continue;
            }

            //FMCConfig.debugMessage("reading File: " + aFile.getAbsolutePath());
            AptParser parser = new AptParser(aFile);
            parser.parse();
        }

    }

    /**
     * parse the earth_nav.dat files to extract ils freqs
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void parseFreqs() throws FileNotFoundException, IOException {

        Session session = DBSessionManager.getSession();
        String earthNavDataFileName = FMCConfig.getConfigBean().getFolder_xplaneFolder() + File.separator + "Resources" + File.separator + "default data" + File.separator + "earth_nav.dat";

        File datFile = new File(earthNavDataFileName);
        if (!datFile.exists()) {
            System.out.println("Earth Nav Dat File does not exist. Not parsing");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(datFile));
        String line;

        while ((line = br.readLine()) != null) {

            if (line.startsWith("4 ")
                    || line.startsWith("5 ")
                    || line.startsWith("6 ")) {

                String[] splittedLine = StringUtils.split(line);
                if (splittedLine.length != 11) {
                    continue;
                }
                try {
                    String lineType = splittedLine[0];
                    String lat = splittedLine[1];
                    String lon = splittedLine[2];
                    String freq = splittedLine[4];
                    String runway = splittedLine[9];
                    String icao = splittedLine[8];
                    String type = splittedLine[10];
                    String heading = splittedLine[6];

                    NavDataEntity navData = new NavDataEntity();
                    navData.setHeading(heading);
                    navData.setIcao(icao);
                    navData.setFreq(NavPointHelpers.formatFrequency(freq));
                    navData.setLat(lat);
                    navData.setLon(lon);
                    navData.setLineTypeNumber(Integer.parseInt(lineType));
                    navData.setType(type);
                    navData.setRunway(runway);

                   // System.out.println(navData.getFreq());

                    session.saveOrUpdate(navData);
                    session.flush();
                } catch (StringIndexOutOfBoundsException ex) {
                    //System.out.println("Skipping line");
                }

            }

        }
        DBSessionManager.closeSession();
    }

    /**
     * getter
     *
     * @return
     */
    public int getFilesToIndex() {
        return filesToIndex;
    }

    /**
     * setter
     *
     * @param filesToIndex
     */
    public void setFilesToIndex(int filesToIndex) {
        this.filesToIndex = filesToIndex;
    }

    /**
     * getter
     *
     * @return
     */
    public int getCurrentFileCounter() {
        return currentFileCounter;
    }

    /**
     * setter
     *
     * @param currentFileCounter
     */
    public void setCurrentFileCounter(int currentFileCounter) {
        this.currentFileCounter = currentFileCounter;
    }

}
