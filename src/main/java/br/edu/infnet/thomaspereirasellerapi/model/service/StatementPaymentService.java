package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CardOnfile;
import br.edu.infnet.thomaspereirasellerapi.model.domain.CreditCardData;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementPayment;
import br.edu.infnet.thomaspereirasellerapi.model.domain.client.CieloZeroAuthFeignClient;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.CreditCardRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementPaymentResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementPaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatementPaymentService {

    private final CieloZeroAuthFeignClient cieloZeroAuthFeignClient;
    private final StatementService statementService;
    private final StatementPaymentRepository statementPaymentRepository;
    private final SellerRepository sellerRepository;

    public StatementPaymentService(CieloZeroAuthFeignClient cieloZeroAuthFeignClient, StatementService statementService, StatementPaymentRepository statementPaymentRepository, SellerRepository sellerRepository) {
        this.cieloZeroAuthFeignClient = cieloZeroAuthFeignClient;
        this.statementService = statementService;
        this.statementPaymentRepository = statementPaymentRepository;
        this.sellerRepository = sellerRepository;
    }

    public StatementPaymentResponseDTO copyFromStatementPayment(StatementPayment statementPayment) {

        StatementPaymentResponseDTO statementPaymentResponseDTO = new StatementPaymentResponseDTO();
        StatementResponseDTO statementResponseDTO = statementService.copyFromStatement(statementPayment.getStatement());
        statementPaymentResponseDTO.setId(statementPayment.getId());
        statementPaymentResponseDTO.setPaymentDate(statementPayment.getPaymentDate());
        statementPaymentResponseDTO.setCreditCardData(statementPayment.getCreditCard());
        statementPaymentResponseDTO.setStatementAmount(statementPayment.getStatementAmount());
        statementPaymentResponseDTO.setStatement(statementResponseDTO);

        return statementPaymentResponseDTO;
    }

    public Boolean isValidCreditCard(CreditCardData cardNumber, String merchantId, String merchantKey) {

        CardOnfile cardOnfile = new CardOnfile();
        cardOnfile.setReason("Recurring");
        cardOnfile.setUsage("First");
        CreditCardRequestDTO creditCardRequestDTO = new CreditCardRequestDTO();

        creditCardRequestDTO.setCardNumber(cardNumber.getCreditCardNumber());
        creditCardRequestDTO.setBrand(cardNumber.getBrand());
        creditCardRequestDTO.setExpirationDate(cardNumber.getExpirationDate());
        creditCardRequestDTO.setHolder(cardNumber.getHolderName());
        creditCardRequestDTO.setSecurityCode(cardNumber.getSecurityCode());
        creditCardRequestDTO.setCardOnfile(cardOnfile);

        CieloZeroAuthFeignClient.CieloZeroAuthResponse response = cieloZeroAuthFeignClient.checkCreditCard(creditCardRequestDTO, merchantId, merchantKey);

        return response.isValid();
    }

    public StatementPaymentResponseDTO createStatementPayment(StatementRequestDTO statement, CreditCardData creditCard) {
        Statement statement1 = statementService.copyToStatement(statement);

        StatementPayment statementPayment = new StatementPayment();
        statementPayment.setStatement(statement1);
        statementPayment.setCreditCard(creditCard);
        statementPayment.setPaymentDate(LocalDateTime.now());
        statementPayment.setSeller(sellerRepository.findSellerByCnpj(statement.getSellerCnpj()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found")));
        statementPayment.setStatementAmount(statementService.monthlyStatementCalculation(statement1));

        statementPaymentRepository.save(statementPayment);
        StatementPaymentResponseDTO statementPaymentResponseDTO = copyFromStatementPayment(statementPayment);

        return statementPaymentResponseDTO;
    }

    public List<StatementPaymentResponseDTO> getStatementPayments() {

        List<StatementPaymentResponseDTO> statementPaymentResponseDTOs = new ArrayList<>();

        for(StatementPayment statementPayment : statementPaymentRepository.findAll()) {
                StatementPaymentResponseDTO statementPaymentResponseDTO = copyFromStatementPayment(statementPayment);
                statementPaymentResponseDTOs.add(statementPaymentResponseDTO);
        }

        return statementPaymentResponseDTOs;
    }

    public StatementPaymentResponseDTO getStatementPaymentById(Long id) {

        StatementPayment statementPayment = statementPaymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Statement with id %d not found", id)));
        StatementPaymentResponseDTO statementPaymentResponseDTO = copyFromStatementPayment(statementPayment);

        return statementPaymentResponseDTO;
    }


}
