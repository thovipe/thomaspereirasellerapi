package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

import br.edu.infnet.thomaspereirasellerapi.model.domain.MonthReference;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementItem;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementStatus;

import java.util.List;

public class StatementResponseDTO {

    private Long id;
    private MonthReference reference;
    private SellerResponseDTO seller;
    private String description;
    private List<StatementItem> statementItems;
    private StatementStatus status;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthReference getReference() {
        return this.reference;
    }

    public void setReference(MonthReference reference) {
        this.reference = reference;
    }

    public SellerResponseDTO getSeller() {
        return this.seller;
    }

    public void setSeller(SellerResponseDTO seller) {
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
