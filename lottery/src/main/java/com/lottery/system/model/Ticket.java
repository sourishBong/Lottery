package com.lottery.system.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Line> lines;
    private boolean statusChecked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public boolean isStatusChecked() {
        return statusChecked;
    }

    public void setStatusChecked(boolean statusChecked) {
        this.statusChecked = statusChecked;
    }
}
