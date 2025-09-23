package br.edu.infnet.thomaspereirasellerapi.model.domain;

import java.math.BigDecimal;
import java.util.List;

public class Statement {

    private MonthReference reference;

    private Seller seller;
    private String description;
    private List<StatementItem> statementItems;
    private StatementStatus status;

    public Statement(Seller seller, BigDecimal amount) {
        this.reference = MonthReference.JANUARY;
        this.seller = seller;
        this.status = StatementStatus.PENDING;
    }


    public MonthReference getReference() {
        return reference;
    }

    public void setReference(MonthReference reference) {
        this.reference = reference;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StatementItem> getStatementItems() {
        return statementItems;
    }

    public void setStatementItems(List<StatementItem> statementItems) {
        this.statementItems = statementItems;
    }

    public StatementStatus getStatus() {
        return status;
    }

    public void setStatus(StatementStatus status) {
        this.status = status;
    }
}
