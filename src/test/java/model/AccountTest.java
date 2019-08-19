package model;

import revolut.exceptions.IllegalAccountOperationException;
import revolut.exceptions.InsufficientFundsException;
import revolut.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @Before
    public void setUp(){
    }

    @Test
    public void successfulAccountDebit() {
        Account testAccount = new Account("1","100.10");

        BigDecimal balanceAfterDebit = testAccount.withdraw(new BigDecimal("20.20"));

        assertEquals(new BigDecimal("79.90"), balanceAfterDebit);
        assertEquals(new BigDecimal("79.90"), testAccount.getAccountBalance());
    }

    @Test
    public void successfulDebitToZero(){
        Account testAccount = new Account("2","100");

        BigDecimal balanceAfterDebit = testAccount.withdraw(new BigDecimal("100"));

        assertEquals(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_DOWN), balanceAfterDebit);
        assertEquals(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_DOWN), testAccount.getAccountBalance());
    }


    @Test(expected = InsufficientFundsException.class)
    public void accountDebitBelowLimitShouldThrowException() {
        Account testAccount = new Account("3","100");

        testAccount.withdraw(new BigDecimal("101"));
    }

    @Test(expected = IllegalAccountOperationException.class)
    public void negativeDebitShouldThrowException() {
        Account testAccount = new Account("4","500");

        testAccount.withdraw(new BigDecimal("-30.28"));
    }

    @Test(expected = IllegalAccountOperationException.class)
    public void nullDebitShouldThrowException() {
        Account testAccount = new Account("5","700");

        testAccount.withdraw(null);
    }

    @Test
    public void successfulAccountCredit() {
        Account testAccount = new Account("6","50.12");

        BigDecimal balanceAfterCredit = testAccount.deposit(new BigDecimal("30.28"));

        assertEquals(new BigDecimal("80.40"), balanceAfterCredit);
        assertEquals(new BigDecimal("80.40"), testAccount.getAccountBalance());
    }

    @Test(expected = IllegalAccountOperationException.class)
    public void negativeCreditShouldThrowException() {
        Account testAccount = new Account("7","666");

        testAccount.deposit(new BigDecimal("-30.28"));
    }

    @Test(expected = IllegalAccountOperationException.class)
    public void nullCreditShouldThrowException() {
        Account testAccount = new Account();

        testAccount.deposit(null);
    }
}
