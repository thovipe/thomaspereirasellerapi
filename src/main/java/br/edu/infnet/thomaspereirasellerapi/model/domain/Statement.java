package br.edu.infnet.thomaspereirasellerapi.model.domain;

import java.math.BigDecimal;
import java.util.List;

public class Statement {

    private String reference;
    private BigDecimal amount;
    private Seller seller;
    private String description;
    private List<StatementItem> statementItems;
    private StamentStatus status;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public StamentStatus getStatus() {
        return status;
    }

    public void setStatus(StamentStatus status) {
        this.status = status;
    }
}
