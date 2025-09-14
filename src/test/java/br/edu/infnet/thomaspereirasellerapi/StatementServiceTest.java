package br.edu.infnet.thomaspereirasellerapi;

import br.edu.infnet.thomaspereirasellerapi.model.domain.*;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
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
        item.setValue(BigDecimal.ZERO);
        statementItem.setItems(Arrays.asList(item));
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidItemValueException.class, () -> statementService.monthlyStamentCalculation(statement));
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
        Item item = new Item();
        item.setValue(new BigDecimal("100.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setQuantity(1);
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

    @Test
    @DisplayName("RF001-04 Itens não cobrados devem ser reduzidos da fatura mensal.")
    void shouldCalcMonthStatementNonBillable() {
        Statement statement = new Statement();
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        Item item = new Item();
        item.setValue(new BigDecimal("100.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setBillable(false);
        statementItem.setQuantity(1);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertEquals(BigDecimal.ZERO, statementService.monthlyStamentCalculation(statement), "Seller should not be charged.");
    }

    @Test
    @DisplayName("RF001-05 Uma fatura precisa ter uma referencia valida.")
    void shouldCalcMonthStatementValidReference_whenCreated() {
        Statement statement = new Statement();
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        Item item = new Item();
        item.setValue(new BigDecimal("100.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));
        statementItem.setBillable(false);
        statementItem.setQuantity(1);
        statement.setReference(null);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertInstanceOf(MonthReference.class, statement.getReference(), "Reference should be instance of MonthReference");
        assertThrows(InvalidMonthReferenceException.class, () -> statementService.monthlyStamentCalculation(statement));
    }

    @Test
    @DisplayName("RF001-06 O valor de um item não pode ser negativo.")
    void shouldCalcMonthStatementNonNegativeItemValue(){
        Statement statement = new Statement();
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        Item item = new Item();
        item.setValue(new BigDecimal("-10.00"));
        item.setName("Test item");
        item.setDescription("Test description");
        StatementItem statementItem = new StatementItem();
        statementItem.setItems(Arrays.asList(item));

        statementService.monthlyStamentCalculation(statement);

        assertThrows(InvalidItemValueException.class, () -> statementService.monthlyStamentCalculation(statement));
    }

}
