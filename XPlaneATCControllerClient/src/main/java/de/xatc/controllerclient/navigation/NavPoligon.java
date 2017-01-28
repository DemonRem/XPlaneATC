
package de.xatc.controllerclient.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * this bean represents a multiline poligon
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NavPoligon {

    /**
     * the navpoint list
     */
    private List<NavPoint> navPointList = new ArrayList<>();

    /**
     * getter
     *
     * @return
     */
    public List<NavPoint> getNavPointList() {
        return navPointList;
    }

    /**
     * setter
     *
     * @param navPointList
     */
    public void setNavPointList(List<NavPoint> navPointList) {
        this.navPointList = navPointList;
    }

}
