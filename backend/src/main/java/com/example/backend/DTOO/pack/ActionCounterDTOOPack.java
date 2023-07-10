package com.example.backend.DTOO.pack;

import com.example.backend.DTOO.ActionCounterDTOO;

public class ActionCounterDTOOPack {
    private ActionCounterDTOO actionCounter;
    private String message;

    public ActionCounterDTOOPack() {}

    public ActionCounterDTOOPack(ActionCounterDTOO actionCounter, String message) {
        this.actionCounter = actionCounter;
        this.message = message;
    }

    public ActionCounterDTOO getActionCounter() {
        return actionCounter;
    }

    public void setActionCounter(ActionCounterDTOO actionCounter) {
        this.actionCounter = actionCounter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
