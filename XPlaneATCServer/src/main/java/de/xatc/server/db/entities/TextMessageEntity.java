/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.xatc.server.db.entities;

import com.sun.istack.Nullable;
import de.xatc.commons.db.sharedentities.user.RegisteredUser;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

/**
 *
 * @author c047
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
        region = "registeredUsers")
public class TextMessageEntity implements Serializable {

    @Id
    @Index(name = "id")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(nullable = false)
    private int id;
    
    @ManyToOne
    private RegisteredUser fromUser;
    
    @ManyToOne
    @Nullable
    private RegisteredUser toUser;
    
    private Timestamp sentTime;
    
    private String textMessage;
    
    private String frequency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RegisteredUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(RegisteredUser fromUser) {
        this.fromUser = fromUser;
    }

    public RegisteredUser getToUser() {
        return toUser;
    }

    public void setToUser(RegisteredUser toUser) {
        this.toUser = toUser;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    
    

}
