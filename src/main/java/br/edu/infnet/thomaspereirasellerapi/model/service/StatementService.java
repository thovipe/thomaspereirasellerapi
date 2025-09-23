package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.StamentStatus;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidQuantityItemValueException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicReference;

public class StatementService {

    public BigDecimal monthlyStatementCalculation(Statement statement)
    {
       statement.getStatementItems().forEach(statementItem -> {

               if(statementItem.getValue().equals(BigDecimal.ZERO) || statementItem.getValue() == null)
               {
                   throw new InvalidItemValueException ("Value cannot be null or zero.");
               }
               if(statementItem.getValue().compareTo(BigDecimal.ZERO) < 0)
               {
                   throw new InvalidItemValueException ("Value cannot be negative.");
               }

       });

       statement.getStatementItems().forEach(statementItem -> {
           if(statementItem.getQuantity() == null || statementItem.getQuantity() == 0)
           {
               throw new InvalidQuantityItemValueException("Quantity cannot be null or zero.");
           }
       });

       if (statement.getReference() == null) {
           throw new InvalidMonthReferenceException("Month reference can not be null.");
       }

        AtomicReference<BigDecimal> statementFullAmount = new AtomicReference<>(BigDecimal.ZERO);
        statement.getStatementItems().forEach(statementItem -> {
            if(statementItem.isBillable()) {
                statementFullAmount.set(statement.getStatementItems().stream().map(item -> item.getValue().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        });

       return statementFullAmount.get();
    }

    public BigDecimal monthlyStatementWithDiscountCalculation(Statement statement, Integer discount) {

        BigDecimal finalStatementValue = BigDecimal.ZERO;

        if(statement.getStatus() == StamentStatus.OVERDUE) {
            return finalStatementValue;
        }
        if( discount == null || discount <= 0 ) {
            throw new InvalidParameterException("Discount cannot be null or zero.");
        }

       if(discount <= 30)  {
            BigDecimal statementWithoutDiscount = monthlyStatementCalculation(statement);
            BigDecimal discountValue = statementWithoutDiscount.multiply(BigDecimal.valueOf(discount));
            finalStatementValue = discountValue.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            finalStatementValue = statementWithoutDiscount.subtract(finalStatementValue);
            return finalStatementValue;
        } else {
            return monthlyStatementWithDiscountCalculation(statement, 30);
        }

    }

}
