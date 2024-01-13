package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.PayBuyCardPage;
import ru.netology.data.SqlHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayBuyCardTest {

    private DashboardPage dashboardPage;
    private PayBuyCardPage payBuyCardPage;

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
    void cleanDataBase() {
        SqlHelper.clearDataBase();
    }


    @Test
    public void purchaseCompletedApprovedCard() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForSuccessedNotification();
        val expected = DataHelper.getValidCardStatus();
        val actual = SqlHelper.getPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void purchaseNotCompletedDeclinedCard() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getInValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForFailedNotification();
        val expected = DataHelper.getInValidCardStatus();
        val actual = SqlHelper.getPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
        void purchaseNotCompletedWithRandomCard() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForFailedNotification();
    }

    @Test
    void purchaseNotCompletedClearLines() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getClearCardNumber();
        val month = DataHelper.getClearMonth();
        val year = DataHelper.getClearYear();
        val owner = DataHelper.getClearOwner();
        val cvc = DataHelper.getClearCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompletedClearCardNumber() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getClearCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }


    @Test
    void purchaseNotComplete15DigitsCardNumber() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.get15DigitsCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteZeroCardNumber() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getZeroCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersCardNumber() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getSpecialCharactersCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForUnacceptableSymbolsMessage();
    }


    @Test
    void purchaseNotComplete1DigitCardNumber() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.get1DigitCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }


    @Test
    void purchaseNotCompleteClearMonth() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getClearMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatMonth() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteZeroMonth() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectCardExpirationMessage();
    }

    @Test
    void purchaseNotCompleteMore12Month() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getMore12Month();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectCardExpirationMessage();
    }


    @Test
    void purchaseNotCompleteSpecialCharactersMonth() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getSpecialCharactersMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteClearYear() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getClearYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatYear() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersYear() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getSpecialCharactersYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteClearOwner() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getClearOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompleteCyrillicOwner() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCyrillicOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteDigitsOwner() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getDigitsOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersOwner() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getSpecialCharactersOwner();
        val cvc = DataHelper.getValidCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForUnacceptableSymbolsMessage();
    }

    @Test
    void purchaseNotCompleteClearCvc() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getClearCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForNecessaryFieldMessage();
    }

    @Test
    void purchaseNotCompletegetZeroCvc() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getZeroCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteInvalidFormatCvc() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getInvalidFormatCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForIncorrectFormatMessage();
    }

    @Test
    void purchaseNotCompleteSpecialCharactersCvc() {
        payBuyCardPage = dashboardPage.clickButtonBuyCard().clear();
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getSpecialCharactersCvc();
        payBuyCardPage.completeForm(cardNumber, month, year, owner, cvc);
        payBuyCardPage.waitForUnacceptableSymbolsMessage();
    }
}














