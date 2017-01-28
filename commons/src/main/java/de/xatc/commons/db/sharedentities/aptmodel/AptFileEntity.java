
package de.xatc.commons.db.sharedentities.aptmodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * This database entity contains an apt.dat file description on the harddrive
 *
 * @author Mirko Bubel (mirko_bubel@hotmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "AptFileEntry")
public class AptFileEntity {

    /**
     * filename
     */
    @Id
    @Index(name = "FilenameIndex")
    private String fileName;

    /**
     * getter
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * setter
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
