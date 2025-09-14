package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;

import java.math.BigDecimal;

public class StatementService {



    public BigDecimal monthlyStamentCalculation(Statement statement)
    {
       statement.getStatementItems().forEach(statementItem -> {
           statementItem.getItems().forEach(item -> {
               if(item.getValue().equals(BigDecimal.ZERO) || item.getValue() == null)
               {
                   throw new InvalidItemValueException ("Value cannot be null or zero.");
               }
           });
       });


       return BigDecimal.ZERO;
    }

    // TODO Create a method for applying discounts it calls monthlyStatementCalculation and applies a discount received as parameter
}
