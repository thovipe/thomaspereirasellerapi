package br.edu.infnet.thomaspereirasellerapi;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Item;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementItem;
import br.edu.infnet.thomaspereirasellerapi.model.service.StatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StatementServiceTest {

    private StatementService statementService;

    @BeforeEach
    void setUp() {
        statementService = new StatementService();
    }

    @Test
    @DisplayName("RF001-01 Calculo de fatura mensal não pode ter item com valor zerado")
    void shouldCalcMonthStatement_whenItemNullOrZero() {

        Statement statement = new Statement();
        Item item = new Item();
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        item.setValue(BigDecimal.ZERO);

        assertNotNull(item.getValue(), "Item value can not be null");
        assertNotEquals(BigDecimal.ZERO, item.getValue(), "Item value can not be Zero");
    }

    @Test
    @DisplayName("RF001-01 Calculo de fatura mensal não pode ter itens com quantidade zero")
    void shouldCalcMonthStatement_whenItemsQuantityZero() {

        Statement statement = new Statement();
        Item item = new Item();
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setQuantity(0);

        assertNotEquals(0, statementItem.getQuantity(), "Item quantity can not be zero");
    }
    @Test
    @DisplayName("RF001-02 Toda fatura precisa ter um Seller associado.")
    void shouldCalcMonthStatement_whenSellerNull() {
        Statement statement = new Statement();
        Seller seller = null;
        Item item = new Item();
        item.setValue(new BigDecimal("100.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setQuantity(1);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertInstanceOf(Seller.class, statement.getSeller(), "Seller should be instance of Seller");
    }

    @Test
    @DisplayName("RF001-03 Calculo de fatura nao pode ser negativo")
    void shouldCalcMonthStatmentNotNegative() {
        Statement statement = new Statement();
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        Item item = new Item();
        item.setValue(new BigDecimal("-100.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setQuantity(1);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidParameterException.class, () -> statementService.monthlyStamentCalculation(statement), "Calculation can not be negative.");

    }

}
