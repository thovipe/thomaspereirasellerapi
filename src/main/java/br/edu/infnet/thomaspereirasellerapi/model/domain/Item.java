package br.edu.infnet.thomaspereirasellerapi.model.domain;

import java.math.BigDecimal;

public class Item {

    private String description;
    private String name;
    private BigDecimal value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
