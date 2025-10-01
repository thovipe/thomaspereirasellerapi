package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementStatus;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.SellerResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidQuantityItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.SellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StatementService {

    private final StatementRepository statementRepository;
    private final SellerService sellerService;
    private final SellerRepository sellerRepository;

    public StatementService(StatementRepository statementRepository,  SellerService sellerService,  SellerRepository sellerRepository) {
        this.statementRepository = statementRepository;
        this.sellerService = sellerService;
        this.sellerRepository = sellerRepository;
    }

    public  StatementResponseDTO copyFromStatement(Statement statement) {
        StatementResponseDTO statementResponseDTO = new StatementResponseDTO();
        statementResponseDTO.setId(statement.getId());
        statementResponseDTO.setDescription(statement.getDescription());
        statementResponseDTO.setStatus(statement.getStatus());
        statementResponseDTO.setReference(statement.getReference());
        statementResponseDTO.setSeller(sellerService.copyFromSeller(statement.getSeller()));
        statementResponseDTO.setStatementItems(statement.getStatementItems());
        return statementResponseDTO;
    }

    public Statement copyToStatement(StatementRequestDTO statementRequestDTO) {
        Statement statement = new Statement();
        statement.setDescription(statementRequestDTO.getDescription());
        statement.setStatus(statementRequestDTO.getStatus());
        statement.setReference(statementRequestDTO.getReference());
        statement.setStatementItems(statementRequestDTO.getStatementItems());
        statement.setSeller(sellerRepository.findSellerByCnpj(statementRequestDTO.getSellerCnpj()).orElseThrow(() -> new SellerNotFoundException("Seller not found.")));
        return statement;
    }

    public List<StatementResponseDTO> getAllStatements() {

        List<StatementResponseDTO> statementResponseDTOs = new ArrayList<>();

        for (Statement statement : statementRepository.findAll()) {
            statementResponseDTOs.add(copyFromStatement(statement));
        }

        return statementResponseDTOs;
    }

    public StatementResponseDTO getStatement(Long id) {
       return copyFromStatement(statementRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Statement with id %d not found", id))));
    }

    public StatementResponseDTO addStatement(StatementRequestDTO statement) {

        Statement statementFromRepository = statementRepository.save(copyToStatement(statement));

        return copyFromStatement(statementFromRepository);
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
