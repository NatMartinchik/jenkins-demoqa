package com.martinchikn;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class DemoTest extends TestBase {

    @Test
    @DisplayName("Successful fill registration test")
    void fillFormTest() {
        String fname = "Alex";
        String lname = "Egorov";
        String email = "alexetest@test.ru";
        String phone = "7000999001";
        String address = "36 Main str, app 54";

        step("Open registration form", () -> {
            open("/automation-practice-form");
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });
        step("Fill registration form", () -> {
            $("#firstName").setValue(fname);
            $("#lastName").setValue(lname);
            $("#userEmail").setValue(email);
            $(byText("Male")).click();
            $("#userNumber").setValue(phone);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue("8");
            $(".react-datepicker__year-select").selectOptionByValue("1995");
            $x("//*[@id=\"dateOfBirth\"]/div[2]/div[2]/div/div/div[2]/div[2]/div[1]/div[7]").click();
            $("#subjectsInput").setValue("Computer Science").pressEnter();
            $(byText("Reading")).click();
            $("input#uploadPicture").uploadFile(new File("src/test/resources/cat.jpg"));
            $("#currentAddress").setValue(address);
            $(".css-1wa3eu0-placeholder").click();
            $(byText("NCR")).click();
            $(".css-1wa3eu0-placeholder").click();
            $(byText("Delhi")).click();
            $("#submit").click();
        });
        step("Verify form data", () -> {
        $(".table-responsive").shouldHave(
                text( fname + " " + lname),
                text(phone),
                text(email),
                text("Male"),
                text("02 September,1995"),
                text("Computer Science"),
                text("cat.jpg"),
                text("Reading"),
                text("36 Main str, app 54"),
                text("NCR Delhi"));
        });

    }
}