package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

import br.edu.infnet.thomaspereirasellerapi.model.domain.MonthReference;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementItem;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementStatus;

import java.util.List;

public class StatementRequestDTO {

    private MonthReference reference;
    private String sellerCnpj;
    private String description;
    private List<StatementItem> statementItems;
    private StatementStatus status;

    public MonthReference getReference() {
        return reference;
    }

    public void setReference(MonthReference reference) {
        this.reference = reference;
    }

    public String getSellerCnpj() {
        return sellerCnpj;
    }

    public void setSellerCnpj(String sellerCnpj) {
        this.sellerCnpj = sellerCnpj;
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
