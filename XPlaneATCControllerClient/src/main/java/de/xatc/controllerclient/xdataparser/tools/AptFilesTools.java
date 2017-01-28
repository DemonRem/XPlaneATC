/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser.tools;

import de.xatc.commons.db.sharedentities.aptmodel.AptAirport;
import de.xatc.controllerclient.navigation.NavPoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * This class contains a bunch of utilities for processing apt files an models
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptFilesTools {

    /**
     * extract an airport passage from an apt.dat file
     *
     * @param fileName
     * @param lineNumber
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String extractAirportFromFile(String fileName, int lineNumber) throws FileNotFoundException, IOException {
        return extractAirportFromFile(new File(fileName), lineNumber);
    }

    /**
     * extract an airport passage from an apt.dat file
     *
     * @param file
     * @param lineNumber
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String extractAirportFromFile(File file, int lineNumber) throws FileNotFoundException, IOException {

        if (!file.exists()) {
            //System.out.println("FILE NOT FOUND! REturning null");
            return null;
        }
        //System.out.println("FILE: " + file.getAbsolutePath());
        //System.out.println("LINENUMBER: " + lineNumber);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int lineCounter = 0;
        boolean isAirport = false;
        String airportString = "";
        while ((line = br.readLine()) != null) {

            lineCounter++;
            if (lineCounter < lineNumber) {
                continue;
            } else if (lineCounter == lineNumber) {
                airportString += line;
//                System.out.println("LineCounter == lineNumber");
//                System.out.println("airportString is now: " + airportString);
                continue;
            } else {
                isAirport = true;
//                System.out.println("linecounter larger -> isAirport true");
            }
            if (isAirport && !(line.startsWith("1 ") || line.startsWith("17 ") || line.startsWith("16 "))) {
//                System.out.println("Airport true - appending line");
                airportString += "\n" + line;
//                System.out.println("AirportString is now: " + airportString);
            }
            if (line.startsWith("1 ") || line.startsWith("17 ") || line.startsWith("16 ")) {
//                System.out.println("Next airport marker found");
//                System.out.println(line);
//                System.out.println("returning airportstring");
                isAirport = false;
                return airportString;
            }

        }
        if (isAirport) {
            return airportString;
        } else {
            return null;
        }
    }

    /**
     * get an initial airport position. Used to navigate the map to a selected
     * airport
     *
     * @param airportString
     * @return
     */
    public static NavPoint getAirportInitialPosition(String airportString) {

        try {
            NavPoint nav = new NavPoint();
            BufferedReader bufReader = new BufferedReader(new StringReader(airportString));

            String line;
          
            while ((line = bufReader.readLine()) != null) {
               // System.out.println(line);
               

              
                if (line.startsWith("100"))  {

                    String[] splitString = StringUtils.split(line);
                    //System.out.println(line);
                   // System.out.println("INITPOS " + line);
                    String lati = splitString[9];
                    String longi = splitString[10];
                    nav.setLatitudeSTring(lati);
                    nav.setLongitudeSTring(longi);
                   // System.out.println("LONGITUDE " + longi);
                   // System.out.println("LATITUDE " + lati);

                    return nav;
                }
                
                if (line.startsWith("101")) {
                    
                    String[] splitString = StringUtils.split(line);
                    System.out.println(line);
                   // System.out.println("INITPOS " + line);
                    String lati = splitString[4];
                    String longi = splitString[5];
                    nav.setLatitudeSTring(lati);
                    nav.setLongitudeSTring(longi);
                    
                    return nav;
                }
                
                
                if (line.startsWith("102")) {
                    
                    String[] splitString = StringUtils.split(line);
                   // System.out.println("INITPOS " + line);
                    String lati = splitString[2];
                    String longi = splitString[3];
                    nav.setLatitudeSTring(lati);
                    nav.setLongitudeSTring(longi);
                   // System.out.println("LONGITUDE " + longi);
                   // System.out.println("LATITUDE " + lati);

                    return nav;
                    
                    
                    
                }

            }

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * join an array to text
     *
     * @param a
     * @param start
     * @param stop
     * @return
     */
    public static String joinArray(String[] a, int start, int stop) {

        String returnString = "";
        int counter = 0;
        for (String s : a) {

            if (counter >= start && counter <= stop) {
                returnString = returnString + s + " ";
            }
            counter++;
        }
        return returnString;

    }

    /**
     * extract a short name of an apt.dat file. This is used as tooltip when
     * selecting an apt airport
     *
     * @param s
     * @return
     */
    public static String extractShortAptFileName(String s) {

        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splitted = s.split(pattern);

        return splitted[splitted.length - 3];

    }
    
        /**
     * parse the airport line
     *
     * @param s
     * @return
     */
    public static AptAirport parseAirport(String s, String fullFileName) {

        AptAirport airport = new AptAirport();

        String[] splitted = StringUtils.split(s);

        airport.setAirportType(Integer.parseInt(splitted[0]));
        airport.setIcao(splitted[4]);
        airport.setAiportName(AptFilesTools.joinArray(splitted, 5, splitted.length - 1));
        airport.setFullFileName(fullFileName);
        airport.setShortFileName(AptFilesTools.extractShortAptFileName(airport.getFullFileName()));

        return airport;
    }

}
