package com.store.banani.dto;

import java.time.LocalDate;

public class WeekDTO {
    private int index;
    private LocalDate start;
    private LocalDate end;

    public WeekDTO(int index, LocalDate start, LocalDate end) {
        this.index = index;
        this.start = start;
        this.end = end;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
