package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidQuantityItemValueException;

import java.math.BigDecimal;
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

        AtomicReference<BigDecimal> calc = new AtomicReference<>(BigDecimal.ZERO);
        statement.getStatementItems().forEach(statementItem -> {
            if(statementItem.isBillable()) {
                calc.set(statementItem.getValue().multiply(BigDecimal.valueOf(statementItem.getQuantity())));
            }
        });


       return calc.get();
    }

    // TODO Create a method for applying discounts it calls monthlyStatementCalculation and applies a discount received as parameter
}
