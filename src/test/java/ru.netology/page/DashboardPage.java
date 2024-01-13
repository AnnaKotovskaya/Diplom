package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private static final SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']")
            .shouldHave(Condition.text("Путешествие дня"));


    private static final SelenideElement buyCard = $("[class='heading heading_size_m heading_theme_alfa-on-white']");


    private static final SelenideElement formCard = $$("[class='button__content']")
            .findBy(Condition.text("Купить"));


    private static final SelenideElement formCredit = $$("[class='button__content']")
            .findBy(Condition.text("Купить в кредит"));

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    public PayBuyCardPage clickButtonBuyCard() {
        formCard.click();
        buyCard.shouldHave(exactText("Оплата по карте"));
        return new PayBuyCardPage();
    }

    public PayBuyCreditPage clickButtonBuyCredit() {
        formCredit.click();
        buyCard.shouldHave(exactText("Кредит по данным карты"));
        return new PayBuyCreditPage();
    }

}
