package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.PayBuyCreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayBuyCreditTest {
    private DashboardPage dashboardPage;
    private PayBuyCreditPage payBuyCreditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        dashboardPage = open("http://localhost:8080/", DashboardPage.class);
    }

    @AfterEach
    void cleanDB() {
        SqlHelper.clearDataBase();
    }

    @Test
    public void purchaseCompletedApprovedCard() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForSuccessedNotification();
        val expected = DataHelper.getValidCardStatus();
        val actual = SqlHelper.getPaymentCreditStatus();
        assertEquals(expected, actual);
    }

    @Test
    void purchaseNotCompletedDeclinedCard() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getInValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForFailedNotification();
        val expected = DataHelper.getInValidCardNumber();
        val actual = SqlHelper.getPaymentCreditStatus();
        assertEquals(expected, actual);
    }

    @Test
    void purchaseNotCompletedClearLines() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getClearCardNumber();
        val month = DataHelper.getClearMonth();
        val year = DataHelper.getClearYear();
        val owner = DataHelper.getClearOwner();
        val cvc = DataHelper.getClearCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompletedClearCardNumber() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getClearCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompletedWithRandomCard() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForFailedNotification();
    }

    @Test
    void purchaseNotComplete15DigitsCardNumber() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.get15DigitsCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteZeroCardNumber() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getZeroCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotComplete1DigitCardNumber() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.get1DigitCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersCardNumber() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getSpecialCharactersCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteClearMonth() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getClearMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatMonth() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteMore12Month() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getMore12Month();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectCardExpirationMessage();
    }

    @Test
    void purchaseNotCompleteZeroMonth() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectCardExpirationMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersMonth() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getSpecialCharactersMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteClearYear() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getClearYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatYear() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersYear() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getSpecialCharactersYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteClearOwner() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getClearOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteCyrillicOwner() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCyrillicOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteDigitsOwner() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getDigitsOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersOwner() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getSpecialCharactersOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteClearCvc() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getClearCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatCvc() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getInvalidFormatCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompletegetZeroCvc() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getZeroCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersCvc() {
        payBuyCreditPage = dashboardPage.clickButtonBuyCredit().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getSpecialCharactersCvc();
        payBuyCreditPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCreditPage.waitForUnacceptableSymbolsMessage();
    }

}



