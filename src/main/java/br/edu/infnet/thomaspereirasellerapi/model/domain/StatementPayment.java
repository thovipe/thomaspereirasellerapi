package br.edu.infnet.thomaspereirasellerapi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class StatementPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Valid
    @ManyToOne
    private Seller seller;
    private BigDecimal statementAmount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Statement statement;
    private LocalDateTime paymentDate;
    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER, optional = false)
    private CreditCardData creditCard;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BigDecimal getStatementAmount() {
        return this.statementAmount;
    }

    public void setStatementAmount(BigDecimal statementAmount) {
        this.statementAmount = statementAmount;
    }

    public Statement getStatement() {
        return this.statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public LocalDateTime getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CreditCardData getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(CreditCardData creditCard) {
        this.creditCard = creditCard;
    }
}
