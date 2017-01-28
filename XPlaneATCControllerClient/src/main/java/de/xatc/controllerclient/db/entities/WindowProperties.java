/**
 * This file is part of the XPlane Home Server License.
 * You may edit and use this file as you like. But there is no warranty at all and no license condition.
 * XPlane Home Server tries to build up a simple network for flying in small local networks or via internet.
 * Have fun!
 *
 * @Author Mirko Bubel (mirko_bubel@hotmail.com)
 * @Created 02.06.2016
 */
package de.xatc.controllerclient.db.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "windowProperties")
public class WindowProperties implements Serializable {

    @Id
    @Index(name = "windowName")
    private String windowName;

    private int windowWidth;
    private int windowHeight;
    private int windowX;
    private int windowY;

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowX() {
        return windowX;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

}
