package br.edu.infnet.thomaspereirasellerapi.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatementPayment {

    private Seller seller;
    private BigDecimal statementAmount;
    private Statement statement;
    private LocalDateTime paymentDate;
    private CreditCardData creditCard;

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
