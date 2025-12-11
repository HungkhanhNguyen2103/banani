package com.store.banani.dto;

import com.store.banani.config.Helpers;

public class ThongKeDTO {
    private String label;
    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = Helpers.convertMoney(value);
    }
}
