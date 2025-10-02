package br.edu.infnet.thomaspereirasellerapi.model.domain.dto;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CreditCardData;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatementPaymentResponseDTO {

    private Long id;
    private StatementResponseDTO statement;
    private CreditCardData creditCardData;
    private LocalDateTime paymentDate;
    private BigDecimal statementAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatementResponseDTO getStatement() {
        return statement;
    }

    public void setStatement(StatementResponseDTO statement) {
        this.statement = statement;
    }

    public CreditCardData getCreditCardData() {
        return creditCardData;
    }

    public void setCreditCardData(CreditCardData creditCardData) {
        this.creditCardData = creditCardData;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getStatementAmount() {
        return statementAmount;
    }

    public void setStatementAmount(BigDecimal statementAmount) {
        this.statementAmount = statementAmount;
    }
}
