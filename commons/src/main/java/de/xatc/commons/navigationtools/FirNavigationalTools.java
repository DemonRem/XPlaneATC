/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.commons.navigationtools;

import de.xatc.commons.db.sharedentities.atcdata.PlainAirport;
import de.xatc.commons.db.sharedentities.atcdata.PlainNavPoint;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mirko
 */
public class FirNavigationalTools {
    
    
    
    
     public static ArrayList<PlainAirport> findAirportsInFir(List<PlainAirport> airportList, ArrayList<PlainNavPoint> firPoligon) {

//        System.out.println("FIR HAS LINES: " + firPoligon.size());
        ArrayList<PlainAirport> returnList = new ArrayList<>();

       

        for (PlainAirport airport : airportList) {

            //System.out.println("Searching for airport " + airport.getAirportIcao());
            
            boolean isInside = isAirportInsidePoligon(airport, firPoligon);

            if (isInside) {
               
                returnList.add(airport);
                
                
            }
        }

        return returnList;
    }
    
     public static boolean isAirportInsidePoligon(PlainAirport airport, List<PlainNavPoint> poligonNavPoints) {

        List<Point> poligonPoints = new ArrayList<>();
        Point airportPoint = new Point();
        airportPoint.setLocation(airport.getPosition().getLat(), airport.getPosition().getLon());

        for (PlainNavPoint p : poligonNavPoints) {

            Point poligonPoint = new Point();
            poligonPoint.setLocation(p.getLat(), p.getLon());
            poligonPoints.add(poligonPoint);
        }

        
        Point[] points = new Point[poligonPoints.size()];
        return poligonContainsAirport(airportPoint, poligonPoints.toArray(points));

    }

    public static boolean poligonContainsAirport(Point airportPoint, Point[] poligonPoints) {

        Polygon2D p = new Polygon2D();

       for (Point point : poligonPoints) {
           p.addPoint(point.getX(), point.getY());
       }
       return p.contains(airportPoint);
//        
        
//        int i;
//        int j;
//        boolean result = false;
//        for (i = 0, j = poligonPoints.length - 1; i < poligonPoints.length; j = i++) {
//            if ((poligonPoints[i].y > airportPoint.y) != (poligonPoints[j].y > airportPoint.y)
//                    && (airportPoint.x < (poligonPoints[j].x - poligonPoints[i].x) * (airportPoint.y - poligonPoints[i].y) / (poligonPoints[j].y - poligonPoints[i].y) + poligonPoints[i].x)) {
//                result = !result;
//            }
//        }
//        
//        return result;
    }
    
}
