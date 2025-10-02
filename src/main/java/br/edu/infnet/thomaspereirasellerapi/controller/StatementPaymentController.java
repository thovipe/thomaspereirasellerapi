package br.edu.infnet.thomaspereirasellerapi.controller;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CreditCardData;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementPayment;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementPaymentRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementPaymentResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.service.StatementPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/statementpayments")
public class StatementPaymentController {

    private final StatementPaymentService statementPaymentService;

    public StatementPaymentController(StatementPaymentService statementPaymentService) {
        this.statementPaymentService = statementPaymentService;
    }

    @GetMapping
    public ResponseEntity<List<StatementPaymentResponseDTO>> getAllStatementPaymentService() {
        return ResponseEntity.status(HttpStatus.OK).body(statementPaymentService.getStatementPayments());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<StatementPaymentResponseDTO> getStatementPaymentById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(statementPaymentService.getStatementPaymentById(id));
    }

    @PostMapping(value="/isvalid")
    public ResponseEntity<Boolean> isCreditCardValid(@RequestBody @Valid CreditCardData cardNumber, @RequestHeader String merchantId, @RequestHeader String merchantKey){
        return ResponseEntity.status((HttpStatus.CREATED)).body(statementPaymentService.isValidCreditCard(cardNumber, merchantId, merchantKey));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<StatementPaymentResponseDTO> createStatementPayment(@Valid @RequestBody StatementPaymentRequestDTO statementPaymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statementPaymentService.createStatementPayment(statementPaymentRequestDTO));
    }
}
