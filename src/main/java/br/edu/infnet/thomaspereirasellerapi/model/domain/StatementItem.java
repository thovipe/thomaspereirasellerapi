package br.edu.infnet.thomaspereirasellerapi.model.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatementItem {

    private String code;
    private Integer quantity;
    private boolean billable;
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

    public StatementItem(String code, boolean billable) {
        this.code = code;
        this.quantity = 1;
        this.billable = billable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }
}
