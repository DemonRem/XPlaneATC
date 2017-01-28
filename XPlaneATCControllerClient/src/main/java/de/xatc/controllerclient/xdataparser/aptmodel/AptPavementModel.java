/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.xdataparser.aptmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the pavements. All surroundings of the airports
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
public class AptPavementModel {

    /**
     * chunkmodel
     */
    private List<AptChunkModel> chunkList = new ArrayList<>();
    /**
     * pavement name
     */
    private String name;

    /**
     * getter
     *
     * @return
     */
    public List<AptChunkModel> getChunkList() {
        return chunkList;
    }

    /**
     * setter
     *
     * @param chunkList
     */
    public void setChunkList(List<AptChunkModel> chunkList) {
        this.chunkList = chunkList;
    }

    /**
     * getter
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setter
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}
