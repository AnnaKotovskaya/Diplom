package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayBuyCardPage {
    private SelenideElement cardNumberLine = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthLine = $("[placeholder='08']");
    private SelenideElement yearLine = $("[placeholder='22']");
    private SelenideElement ownerLine = $$("[class='input__control']").get(3);
    private SelenideElement cvcLine = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successedNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement necessaryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement incorrectFormatMessage = $(byText("Неверный формат"));
    private SelenideElement unacceptableSymbolsMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement incorrectCardExpirationMessage = $(byText("Неверно указан срок действия карты"));

    public void completeForm(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberLine.sendKeys(cardNumber);
        monthLine.sendKeys(month);
        yearLine.sendKeys(year);
        ownerLine.sendKeys(owner);
        cvcLine.sendKeys(cvc);
        continueButton.click();
    }

    public PayBuyCardPage clear() {
        clearLines();
        return new PayBuyCardPage();

    }

    public void clearLines() {
        cardNumberLine.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthLine.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearLine.doubleClick().sendKeys(Keys.BACK_SPACE);
        ownerLine.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcLine.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForFailedNotification() {
        failedNotification.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }

    public void waitForSuccessedNotification() {
        successedNotification.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }

    public void waitForNecessaryFieldMessage() {
        necessaryFieldMessage.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }

    public void waitForIncorrectFormatMessage() {
        incorrectFormatMessage.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }

    public void waitForUnacceptableSymbolsMessage() {
        unacceptableSymbolsMessage.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }

    public void waitForIncorrectCardExpirationMessage() {
        incorrectCardExpirationMessage.shouldBe(Condition.visible, Duration.ofSeconds(12));
    }
}
