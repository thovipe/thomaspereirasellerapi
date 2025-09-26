package br.edu.infnet.thomaspereirasellerapi;

import br.edu.infnet.thomaspereirasellerapi.model.domain.*;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.StatementRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidMonthReferenceException;
import br.edu.infnet.thomaspereirasellerapi.model.exception.InvalidQuantityItemValueException;
import br.edu.infnet.thomaspereirasellerapi.model.service.StatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StatementServiceTest {

    private StatementService statementService;
    private Seller seller;
    private Statement statement;
    private StatementItem statementItem;
    private StatementRepository statementRepository;

    @BeforeEach
    void setUp() {
        seller = new Seller();
        statement = new Statement(seller, BigDecimal.ZERO);
        statementItem = new StatementItem("CDP01", true);
        statementService = new StatementService(statementRepository);
    }

    @Test
    @DisplayName("RF001-01 Calculo de fatura mensal não pode ter item com valor zerado")
    void shouldCalcMonthStatement_whenItemNullOrZero() {

        statementItem.setItemValue(BigDecimal.ZERO);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidItemValueException.class, () -> statementService.monthlyStatementCalculation(statement));
    }

    @Test
    @DisplayName("RF001-01 Calculo de fatura mensal não pode ter itens com quantidade zero")
    void shouldCalcMonthStatement_whenItemsQuantityZero() {

        statementItem.setItemValue(new BigDecimal("100.00"));
        statementItem.setItemQuantity(0);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidQuantityItemValueException.class, () -> statementService.monthlyStatementCalculation(statement), "Quantity can not be zero.");
    }
    @Test
    @DisplayName("RF001-02 Toda fatura precisa ter um Seller associado.")
    void shouldCalcMonthStatement_whenSellerNull() {

        statementItem.setItemValue(new BigDecimal("100.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        statementItem.setItemQuantity(1);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertInstanceOf(Seller.class, statement.getSeller(), "Seller should be instance of Seller");
    }

    @Test
    @DisplayName("RF001-03 Calculo de fatura nao pode ser negativo")
    void shouldCalcMonthStatmentNotNegative() {
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("-100.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        statementItem.setItemQuantity(1);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidItemValueException.class, () -> statementService.monthlyStatementCalculation(statement), "Calculation can not be negative.");
    }

    @Test
    @DisplayName("RF001-04 Itens não cobrados devem ser reduzidos da fatura mensal.")
    void shouldCalcMonthStatementNonBillable() {
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("100.00"));
        statementItem.setIsBillable(false);
        statementItem.setItemQuantity(1);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertEquals(BigDecimal.ZERO, statementService.monthlyStatementCalculation(statement), "Seller should not be charged.");
    }

    @Test
    @DisplayName("RF001-05 Uma fatura precisa ter uma referencia valida.")
    void shouldCalcMonthStatementValidReference_whenCreated() {
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("100.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        statementItem.setIsBillable(false);
        statementItem.setItemQuantity(1);
        statement.setReference(null);
        statement.setSeller(seller);
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidMonthReferenceException.class, () -> statementService.monthlyStatementCalculation(statement));
    }

    @Test
    @DisplayName("RF001-06 O valor de um item não pode ser negativo.")
    void shouldCalcMonthStatementNonNegativeItemValue(){
        Seller seller = new Seller();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("-10.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        statement.setStatementItems(Arrays.asList(statementItem));

        assertThrows(InvalidItemValueException.class, () -> statementService.monthlyStatementCalculation(statement));
    }

    @Test
    @DisplayName("RF001-07 Validar Calculo de fatura com mais de um item.")
    void shouldCalcMonthStatement_whenMoreThanOneItem() {
        ArrayList<StatementItem> statementItems = new ArrayList<>();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("10.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        StatementItem newStatementItem = new StatementItem("CDP02",true);
        newStatementItem.setItemQuantity(2);
        newStatementItem.setItemValue(new BigDecimal("10.00"));
        newStatementItem.setItemName("Test item 2");
        newStatementItem.setDescription("Test description 2");
        statementItems.add(newStatementItem);
        statementItems.add(statementItem);
        statement.setStatementItems(statementItems);
        BigDecimal expectedValue = new BigDecimal("30.00");

        assertEquals(expectedValue, statementService.monthlyStatementCalculation(statement));
    }

    @Test
    @DisplayName("RF001-08 Se o statement estiver atrasado o monthlyStatementWithDiscountCalculation deve retornar zero.")
    void shouldCalcMonthStatement_whenStatusOverdue() {
        BigDecimal calculatedValue = null;
        statement.setStatus(StatementStatus.OVERDUE);

        if (statement.getStatus() == StatementStatus.OVERDUE) {
            calculatedValue = statementService.monthlyStatementWithDiscountCalculation(statement, 10);
        }

        assertEquals(BigDecimal.ZERO, calculatedValue, "O metodo deve retornar ZERO para statements com status OVERDUE.");
    }

    @Test
    @DisplayName("RF001-09 O calculo monthlyStatementWithDiscountCalculation deve dar no máximo 30 por cento de desconto.")
    void shouldCalcMonthStatement_whenMax30PercentDiscount() {
        BigDecimal calculatedValue = null;
        ArrayList<StatementItem> statementItems = new ArrayList<>();
        seller.setName("Test seller");
        seller.setCnpj("123456789");
        statementItem.setItemValue(new BigDecimal("10.00"));
        statementItem.setItemName("Test item");
        statementItem.setDescription("Test description");
        StatementItem newStatementItem = new StatementItem("CDP02",true);
        newStatementItem.setItemQuantity(2);
        newStatementItem.setItemValue(new BigDecimal("10.00"));
        newStatementItem.setItemName("Test item 2");
        newStatementItem.setDescription("Test description 2");
        statementItems.add(newStatementItem);
        statementItems.add(statementItem);
        statement.setStatementItems(statementItems);
        BigDecimal expectedValue = new BigDecimal("21.00");

        if (statement.getStatus() != StatementStatus.OVERDUE) {
            calculatedValue = statementService.monthlyStatementWithDiscountCalculation(statement, 50);
        }

        assertEquals(expectedValue, calculatedValue, "O calculo monthlyStatementWithDiscountCalculation deve dar no máximo 30 por cento de desconto.");
    }

}
