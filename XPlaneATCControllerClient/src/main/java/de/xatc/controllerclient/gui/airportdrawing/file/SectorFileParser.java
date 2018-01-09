/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or modify it as you like. There is absolutely no warranty at all.
 * The Author of this file is not responsible for any damage, that may occur by using this file.
 * If you want to distribute this file, feel free. It would be very kind, if you write me a short mail.
 * Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2015
 * Have fun!
 */
package de.xatc.controllerclient.gui.airportdrawing.file;

import de.mytools.tools.swing.SwingTools;
import de.twyhelper.tools.FMCConfig;
import de.twyhelper.tools.debug.DebugMessageLevel;
import de.xatc.controllerclient.navigation.NavLine;
import de.xatc.controllerclient.navigation.NavPoint;
import de.xatc.controllerclient.navigation.NavPointHelpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * this class is responsible for parsing the VRC or Euroscope sectofiles
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class SectorFileParser {

    /**
     * container for drawn lines
     */
    private List<NavLine> lineList = new ArrayList();

    /**
     * container for labels
     */
    private List<NavPoint> labelsList = new ArrayList();

    /**
     * container for airports
     */
    private Map<String, NavPoint> aiports = new HashMap<>();

    /**
     * are we currently parsing the GEO part of the file
     */
    private boolean isGEOMark = false;

    /**
     * or labels
     */
    private boolean isLabelsMark = false;

    /**
     * or airports
     */
    private boolean isAirPortMark = false;

    /**
     * or freetext (not implemented yet)
     */
    private boolean isFreeTextMark = false;

    /**
     * count the lines
     */
    private int lineNumber = 0;

    /**
     * how can I see, that I am now entering the labels part
     */
    private final String LABELMARK = "[LABELS]";

    /**
     * and GEO
     */
    private final String GEOMARK = "[GEO]";

    /**
     * and Freetext (Euroscope)
     */
    private final String FREETEXTMARK = "[FREETEXT]";

    /**
     * or Airport
     */
    private final String AIRPORTMARK = "[AIRPORT]";

    /**
     * open, read and parse all Sectorfiles in Sectorfiles dir defined in
     * ConfigBean.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readAndParse() throws FileNotFoundException, IOException {

        FMCConfig.debugMessage("READING AND PARSING FILES", DebugMessageLevel.DEBUG);
        FMCConfig.debugMessage("SECTORS: " + FMCConfig.getConfigBean().getFolder_sectorFilesFolder(), DebugMessageLevel.DEBUG);

        String exportFilesPath = FMCConfig.getConfigBean().getFolder_sectorFilesFolder();

        if (StringUtils.isEmpty(exportFilesPath)) {
            SwingTools.alertWindow("No Sector Files Path found. Please set this folder in Properties.", FMCConfig.getMainFrame());
        }
        ArrayList<File> sectorFiles = SwingTools.getFilesAsList(exportFilesPath);

        int exceptionCounter = 0;
        int linecounter = 0;
        int totallines = 0;
        for (File file : sectorFiles) {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                linecounter = 0;
                while ((line = br.readLine()) != null) {
                    linecounter++;
                    totallines++;
                    if (line.startsWith(this.AIRPORTMARK)) {
                        this.resetAllLineMarks();
                        this.isAirPortMark = true;
                        continue;

                    }
                    if (line.startsWith(this.FREETEXTMARK)) {
                        this.resetAllLineMarks();
                        this.isFreeTextMark = true;
                        continue;
                    }
                    if (line.startsWith(this.GEOMARK)) {
                        this.resetAllLineMarks();
                        this.isGEOMark = true;
                        continue;
                    }
                    if (line.startsWith(this.LABELMARK)) {
                        this.resetAllLineMarks();
                        this.isLabelsMark = true;
                        continue;
                    }
                    if (line.startsWith("[")) {
                        this.resetAllLineMarks();
                        continue;
                    }

                    if (this.isAirPortMark) {
                        try {
                            parseAirportLine(line);
                        } catch (RuntimeException e) {
                            FMCConfig.debugMessage("Exception: File: " + file.getName() + " LINE: " + linecounter, DebugMessageLevel.DEBUG);
                            exceptionCounter++;
                            continue;
                        }
                    }
                    if (this.isFreeTextMark) {
                        try {
                            parseFreeTextLine(line);
                        } catch (RuntimeException e) {
                            exceptionCounter++;
                            FMCConfig.debugMessage("Exception: File: " + file.getName() + " LINE: " + linecounter, DebugMessageLevel.DEBUG);
                            continue;
                        }
                    }
                    if (this.isGEOMark) {
                        try {
                            parseGEOLine(line);
                        } catch (RuntimeException e) {
                            exceptionCounter++;
                            FMCConfig.debugMessage("Exception: File: " + file.getName() + " LINE: " + linecounter + " " + e.getLocalizedMessage(), DebugMessageLevel.DEBUG);

                            continue;
                        }
                    }
                    if (this.isLabelsMark) {
                        try {
                            this.parseLabelLine(line);

                        } catch (RuntimeException e) {
                            exceptionCounter++;
                            FMCConfig.debugMessage("Exception: File: " + file.getName() + " LINE: " + linecounter, DebugMessageLevel.DEBUG);
                            continue;
                        }
                    }

                }
            }

        }
        FMCConfig.savePropsFile();
        FMCConfig.debugMessage("TOTAL EXCEPTIONS: " + exceptionCounter, DebugMessageLevel.DEBUG);
        FMCConfig.debugMessage("total lines " + totallines, DebugMessageLevel.DEBUG);

    }

    /**
     * parse a Label line
     *
     * @param line
     * @throws ArrayIndexOutOfBoundsException
     */
    private void parseLabelLine(String line) throws ArrayIndexOutOfBoundsException {

        line = line.replaceAll("\\s+", " ");
        line = line.replace("^ ", "");
        if (line.startsWith("\"")) {

            FMCConfig.debugMessage(line, DebugMessageLevel.DEBUG);
            String[] splitQuotes = line.split("\"");
            FMCConfig.debugMessage("splitQUOTES SIZE: " + splitQuotes.length, DebugMessageLevel.DEBUG);
            FMCConfig.debugMessage("SplitQuotes: " + Arrays.toString(splitQuotes), DebugMessageLevel.DEBUG);
            String labelText = splitQuotes[1];

            FMCConfig.debugMessage(labelText, DebugMessageLevel.DEBUG);
            String restLine = splitQuotes[2];
            FMCConfig.debugMessage("RESTLINE: '" + restLine + "'", DebugMessageLevel.DEBUG);
            String[] splitLine = restLine.split(" ");

            String latString = splitLine[2];
            String lonString = splitLine[1];

            labelText = labelText.replaceAll("\"", "");
            NavPoint p = new NavPoint();
            p.setName(labelText);

            latString = this.deletePrefix(latString);
            lonString = this.deletePrefix(lonString);

            FMCConfig.debugMessage("LAT: " + latString, DebugMessageLevel.DEBUG);
            FMCConfig.debugMessage("LON: " + lonString, DebugMessageLevel.DEBUG);
            p.setLatitudeSTring(latString);

            p.setLongitudeSTring(lonString);

            p = NavPointHelpers.convertToXY(p);
            this.labelsList.add(p);

            FMCConfig.debugMessage("LABEL: " + p.getName(), DebugMessageLevel.DEBUG);
        }

    }

    /**
     * parse a GEO line
     *
     * @param line
     * @throws ArrayIndexOutOfBoundsException
     */
    private void parseGEOLine(String line) throws ArrayIndexOutOfBoundsException {

        FMCConfig.debugMessage(line, DebugMessageLevel.DEBUG);
        line = line.replaceAll("\\t", " ");
        line = line.replaceAll("\\s+", " ");
        line = line.replace("^ ", "");

        FMCConfig.debugMessage(line, DebugMessageLevel.DEBUG);

        if (!this.checkIfLineDrawn(line)) {
            return;
        }
        String[] splitLine = line.split("\\s");
        String long1 = splitLine[0];
        String lat1 = splitLine[1];
        String long2 = splitLine[2];
        String lat2 = splitLine[3];
        String name = splitLine[4];

        long1 = deletePrefix(long1);
        long2 = deletePrefix(long2);
        lat1 = deletePrefix(lat1);
        lat2 = deletePrefix(lat2);

        NavPoint p1 = new NavPoint();
        p1.setX(800);
        p1.setY(800);
        p1.setLatitudeSTring(lat1);
        p1.setLongitudeSTring(long1);
        p1.setName(name);
        NavPoint p2 = new NavPoint();
        p2.setX(800);
        p2.setY(800);

        p2.setLatitudeSTring(lat2);
        p2.setLongitudeSTring(long2);
        p2.setName(name);

        p1 = NavPointHelpers.convertToXY(p1);
        p2 = NavPointHelpers.convertToXY(p2);

        NavLine d = new NavLine();
        d.setNavPointFrom(p1);
        d.setNavPointTo(p2);
        this.lineList.add(d);

    }

    /**
     * parse a text line
     *
     * @param line
     * @throws ArrayIndexOutOfBoundsException
     */
    private void parseFreeTextLine(String line) throws ArrayIndexOutOfBoundsException {
        // to be done
    }

    /**
     * parse a airport line
     *
     * @param line
     * @throws ArrayIndexOutOfBoundsException
     */
    private void parseAirportLine(String line) throws ArrayIndexOutOfBoundsException {
        String[] splitLine = line.split(" ");
        String icao = splitLine[0];
        String heading = splitLine[1];
        String lat = splitLine[2];
        String lon = splitLine[3];
        NavPoint p = new NavPoint();
        lat = this.deletePrefix(lat);
        lon = this.deletePrefix(lon);
        p.setLatitudeSTring(lat);
        p.setLongitudeSTring(lon);
        p = NavPointHelpers.convertToXY(p);
        this.aiports.put(icao, p);

    }

    /**
     * reset all marks
     */
    private void resetAllLineMarks() {
        this.isAirPortMark = false;
        this.isFreeTextMark = false;
        this.isGEOMark = false;
        this.isLabelsMark = false;

    }

    /**
     * delete a prefix of a lat lon coordinates. I do not want an E or an N in
     * my Double
     *
     * @param in
     * @return
     */
    private String deletePrefix(String in) {

        in = in.replace("E", "");
        in = in.replace("N", "");
        in = in.replace("W", "");
        in = in.replace("S", "");
        return in;

    }

    /**
     * remove dots from lat lon notation
     *
     * @param in
     * @return
     */
    private String removeDots(String in) {

        in = in.replaceFirst("\\.", "FIRSTPOINT");

        in = in.replaceAll("\\.", "");
        in = in.replaceFirst("FIRSTPOINT", ".");

        return in;

    }

    /**
     * getter
     *
     * @return
     */
    public List<NavLine> getLineList() {
        return lineList;
    }

    /**
     * setter
     *
     * @param lineList
     */
    public void setLineList(List<NavLine> lineList) {
        this.lineList = lineList;
    }

    /**
     * getter
     *
     * @return
     */
    public List<NavPoint> getLabelsList() {
        return labelsList;
    }

    /**
     * setter
     *
     * @param labelsList
     */
    public void setLabelsList(List<NavPoint> labelsList) {
        this.labelsList = labelsList;
    }

    /**
     * check, if wa shall draw this line. Normally, there is a remark at the end
     * of the line, looks like a remark. if this remark appears in the config
     * Bean (can be modified in gui by user), we parse and draw it.
     *
     * @param line
     * @return
     */
    private boolean checkIfLineDrawn(String line) {

        FMCConfig.debugMessage("Check line Drawn", DebugMessageLevel.DEBUG);
        int last = line.lastIndexOf(" ");
        FMCConfig.debugMessage("LAST INDEX OF BLANK: " + last, DebugMessageLevel.DEBUG);
        String substring = line.substring(last);
        substring = substring.replaceAll("\\s", "");

        if (!FMCConfig.getConfigBean().getGeoLineTypes().contains(substring)) {
            FMCConfig.getConfigBean().getGeoLineTypes().add(substring);
        }
        FMCConfig.debugMessage("SUBSTRING: " + substring, DebugMessageLevel.DEBUG);
        for (String s : FMCConfig.getConfigBean().getDrawGeoLineTypesList()) {

            if (line.contains(s)) {
                return true;
            }
        }
        return false;
    }

}
