package com.example.backend.DTO;

import java.util.Date;

public class ActionCounterDTOO {
    private Long id;
    private String actionName;
    private Integer counter;
    private Date updateOn;

    public ActionCounterDTOO(){}

    public ActionCounterDTOO(Long id, String actionName, Integer counter, Date updateOn) {
        this.id = id;
        this.actionName = actionName;
        this.counter = counter;
        this.updateOn = updateOn;
    }

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
