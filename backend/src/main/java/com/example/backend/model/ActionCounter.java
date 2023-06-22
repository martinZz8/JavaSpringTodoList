package com.example.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "action_counter")
@Table(name= "action_counter")
public class ActionCounter {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "action_name", nullable = false)
    private String actionName;
    @Column(name = "counter", nullable = false, columnDefinition="integer default '0'")
    private Integer counter;

    @Column(name = "update_on")
    @UpdateTimestamp
    private Date updateOn;

    public ActionCounter(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }
}
