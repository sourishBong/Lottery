package com.lottery.system.dto;

import java.io.Serializable;

public class TicketRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numberOfLines;

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }
}
