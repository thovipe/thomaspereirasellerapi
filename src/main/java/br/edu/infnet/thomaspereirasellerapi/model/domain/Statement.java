package br.edu.infnet.thomaspereirasellerapi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MonthReference reference;
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Seller seller;
    private String description;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StatementItem> statementItems;
    @Enumerated(EnumType.STRING)
    private StatementStatus status;

    public Statement(Seller seller, BigDecimal amount) {
        this.reference = MonthReference.JANUARY;
        this.seller = seller;
        this.status = StatementStatus.PENDING;
    }

    public Statement() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
