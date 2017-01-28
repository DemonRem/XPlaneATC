
package de.xatc.controllerclient.navigation;

/**
 * this bean represents a line drawn on map
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class NavLine {

    /**
     * from navpoint
     */
    private NavPoint navPointFrom;

    /**
     * to navpoint
     */
    private NavPoint navPointTo;

    /**
     * getter
     *
     * @return
     */
    public NavPoint getNavPointFrom() {
        return navPointFrom;
    }

    /**
     * setter
     *
     * @param navPointFrom
     */
    public void setNavPointFrom(NavPoint navPointFrom) {
        this.navPointFrom = navPointFrom;
    }

    /**
     * getter
     *
     * @return
     */
    public NavPoint getNavPointTo() {
        return navPointTo;
    }

    /**
     * setter
     *
     * @param navPointTo
     */
    public void setNavPointTo(NavPoint navPointTo) {
        this.navPointTo = navPointTo;
    }

}
