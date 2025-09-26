package br.edu.infnet.thomaspereirasellerapi.model.domain;

import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementPaymentRepository;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.SellerNotFoundException;
import br.edu.infnet.thomaspereirasellerapi.model.service.StatementPaymentService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(3)
public class StatementPaymentLoader implements ApplicationRunner {

    private final SellerRepository sellerRepository;
    private final StatementRepository statementRepository;
    private final StatementPaymentService statementPaymentService;
    private final StatementPaymentRepository statementPaymentRepository;

    public StatementPaymentLoader(SellerRepository sellerRepository, StatementRepository statementRepository, StatementPaymentService statementPaymentService, StatementPaymentRepository statementPaymentRepository) {
        this.sellerRepository = sellerRepository;
        this.statementRepository = statementRepository;
        this.statementPaymentService = statementPaymentService;
        this.statementPaymentRepository = statementPaymentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Statement statement = new Statement();
        List<StatementItem> items = new ArrayList<>();
        StatementItem statementItem = new StatementItem();
        CreditCardData creditCardData = new CreditCardData();

        creditCardData.setBrand("Visa");
        creditCardData.setCreditCardNumber("5502095822650000");
        creditCardData.setExpirationDate("12/2035");
        creditCardData.setHolderName("Aline de Souza");
        creditCardData.setSecurityCode("123");

        statement.setSeller(sellerRepository.findSellerById(1L).orElseThrow(() -> new SellerNotFoundException("Seller not found")));
        statement.setStatus(StatementStatus.PENDING);
        statement.setDescription("Statement Payment Loader test.");
        statement.setReference(MonthReference.SEPTEMBER);
        statementItem.setItemCode("COMPUTER");
        statementItem.setIsBillable(true);
        statementItem.setItemValue(new BigDecimal("5500.00"));
        statementItem.setItemQuantity(5);
        statementItem.setItemName("Dell Computer");
        statementItem.setDescription("Loader test statement item.");
        items.add(statementItem);
        statement.setStatementItems(items);

        String MerchantId = "c77aad9f-43c5-4896-9b3e-ebd23ed09e5b";
        String MerchantKey = "YPWRRSJVGGSFTGDCLBJCNZGSTENLNCFAPCIHRRJF";

        System.out.println(statementPaymentService.isValidCreditCard(creditCardData, MerchantId, MerchantKey));

        StatementPayment  statementPayment = statementPaymentService.createStatementPayment(statement, creditCardData);

        statementPaymentRepository.save(statementPayment);

    }
}
