
package de.xatc.commons.db.sharedentities.aptmodel;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This entity saves the date when the last apt-file indexer ran.
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
public class IndexUpdatedEntity {

    /**
     * date of indexer run
     */
    @Id
    private Timestamp updateDate;

    /**
     * getter
     *
     * @return
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    /**
     * setter
     *
     * @param updateDate
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

}
