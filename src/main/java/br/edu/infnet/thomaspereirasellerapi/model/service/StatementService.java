package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementStatus;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidQuantityItemValueException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StatementService {

    private final StatementRepository statementRepository;

    public StatementService(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public List<Statement> getAllStatements() {
        return statementRepository.findAll();
    }

    public Statement getStatement(Long id) {
        return statementRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Statement with id %d not found", id)));
    }

    public Statement addStatement(Statement statement) {
        return statementRepository.save(statement);
    }

    public void deleteStatement(Long id) {
        if(id == null) {
            throw new InvalidParameterException("id is null");
        }
        if (statementRepository.existsById(id)) {
            statementRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Statement with id %d not found", id));
        }
    }

    public BigDecimal monthlyStatementCalculation(Statement statement)
    {
       statement.getStatementItems().forEach(statementItem -> {

               if(statementItem.getItemValue().equals(BigDecimal.ZERO) || statementItem.getItemValue() == null)
               {
                   throw new InvalidItemValueException ("Value cannot be null or zero.");
               }
               if(statementItem.getItemValue().compareTo(BigDecimal.ZERO) < 0)
               {
                   throw new InvalidItemValueException ("Value cannot be negative.");
               }

       });

       statement.getStatementItems().forEach(statementItem -> {
           if(statementItem.getItemQuantity() == null || statementItem.getItemQuantity() == 0)
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
                statementFullAmount.set(statement.getStatementItems().stream().map(item -> item.getItemValue().multiply(BigDecimal.valueOf(item.getItemQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        });

       return statementFullAmount.get();
    }

    public BigDecimal monthlyStatementWithDiscountCalculation(Statement statement, Integer discount) {

        BigDecimal finalStatementValue = BigDecimal.ZERO;

        if(statement.getStatus() == StatementStatus.OVERDUE) {
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
