package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyTo1Card() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val dashboardPage = loginPage.validLogin(authInfo).validVerify(DataHelper.getVerificationCodeFor(authInfo));
        int balance1Before = dashboardPage.restOfMoney1();
        int balance2Before = dashboardPage.restOfMoney2();
        val transferPage = dashboardPage.transferToFirst(DataHelper.getAmount());
        val newDashboardPage = transferPage.transferResult();
        assertEquals(DataHelper.getAmount(), Math.abs(balance1Before-newDashboardPage.restOfMoney1()));
        assertEquals(DataHelper.getAmount(), Math.abs(balance2Before-newDashboardPage.restOfMoney2()));
    }

    @Test
    void shouldTransferMoneyTo2Card() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val dashboardPage = loginPage.validLogin(authInfo).validVerify(DataHelper.getVerificationCodeFor(authInfo));
        int balance1Before = dashboardPage.restOfMoney1();
        int balance2Before = dashboardPage.restOfMoney2();
        val transferPage = dashboardPage.transferToSecond(DataHelper.getAmount());
        val newDashboardPage = transferPage.transferResult();
        assertEquals(DataHelper.getAmount(), Math.abs(balance1Before-newDashboardPage.restOfMoney1()));
        assertEquals(DataHelper.getAmount(), Math.abs(balance2Before-newDashboardPage.restOfMoney2()));
    }

    @Test
    void shouldTransferMoneyMoreThanThereIs() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val dashboardPage = loginPage.validLogin(authInfo).validVerify(DataHelper.getVerificationCodeFor(authInfo));
        int balance1Before = dashboardPage.restOfMoney1();
        val transferPage = dashboardPage.transferToSecond(balance1Before + 1);
        val newDashboardPage = transferPage.transferResult();
        assertEquals(-1, newDashboardPage.restOfMoney1());
    }

    @Test
    void shouldTransferMoney0() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val dashboardPage = loginPage.validLogin(authInfo).validVerify(DataHelper.getVerificationCodeFor(authInfo));
        int balance1Before = dashboardPage.restOfMoney1();
        val transferPage = dashboardPage.transferToSecond(DataHelper.getZero());
        val newDashboardPage = transferPage.transferResult();
        assertEquals(balance1Before, newDashboardPage.restOfMoney1());
    }
}
