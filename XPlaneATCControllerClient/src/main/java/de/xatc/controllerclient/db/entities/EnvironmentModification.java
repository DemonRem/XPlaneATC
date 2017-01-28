/**
 * This file is part of the FollowMeCar for X-Plane Package. You may use or
 * modify it as you like. There is absolutely no warranty at all. The Author of
 * this file is not responsible for any damage, that may occur by using this
 * file. If you want to distribute this file, feel free. It would be very kind,
 * if you write me a short mail. Author: Mirko Bubel (mirko_bubel@hotmail.com)
 * Created: April/2016 Have fun!
 */
package de.xatc.controllerclient.db.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * This database entity does contain all environmental changes This file was
 * introduces by version 1.0 in order to delete the aptfile store index with 2.6
 * GB of xml junk. Now we use the embedded derby database
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "environment")
public class EnvironmentModification implements Serializable {

    /**
     * primary identifier
     */
    @Id
    @Index(name = "environmentIdIndex")
    private int ID;

    /**
     * given name of environment change
     */
    @Id
    @Index(name = "environmentNameIndex")
    private String name;

    /**
     * timestamp of data the change was executed
     */
    private Timestamp executedAt;

    /**
     * getter
     *
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * setter
     *
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
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

    /**
     * getter
     *
     * @return
     */
    public Timestamp getExecutedAt() {
        return executedAt;
    }

    /**
     * setter
     *
     * @param executedAt
     */
    public void setExecutedAt(Timestamp executedAt) {
        this.executedAt = executedAt;
    }

}
