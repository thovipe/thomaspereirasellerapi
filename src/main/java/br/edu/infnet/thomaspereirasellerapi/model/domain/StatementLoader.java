package br.edu.infnet.thomaspereirasellerapi.model.domain;

import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.SellerRepository;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.SellerNotFoundException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 2)
public class StatementLoader implements ApplicationRunner {

    private final SellerRepository sellerRepository;
    private final StatementRepository statementRepository;

    public StatementLoader(SellerRepository sellerRepository,  StatementRepository statementRepository) {
        this.sellerRepository = sellerRepository;
        this.statementRepository = statementRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Statement statement = new Statement();

        List<StatementItem> items = new ArrayList<>();

        StatementItem statementItem = new StatementItem();

        statement.setReference(MonthReference.APRIL);
        statement.setSeller(sellerRepository.findSellerById(1L).orElseThrow(() -> new SellerNotFoundException("Seller not found.")));
        statement.setStatus(StatementStatus.OPEN);
        statement.setDescription("Loader test statement.");

        statementItem.setItemCode("COMPUTER");
        statementItem.setIsBillable(true);
        statementItem.setItemValue(new BigDecimal("1500.00"));
        statementItem.setItemQuantity(5);
        statementItem.setItemName("Dell Computer");
        statementItem.setDescription("Loader test statement item.");

        items.add(statementItem);

        statement.setStatementItems(items);

        statementRepository.save(statement);

    }
}
