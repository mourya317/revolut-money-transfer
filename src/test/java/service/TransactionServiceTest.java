package service;

import revolut.dto.TransactionDto;
import revolut.dto.TransactionRepresentation;
import revolut.exceptions.IllegalAccountOperationException;
import revolut.exceptions.InsufficientFundsException;
import revolut.model.Account;
import revolut.repository.BankRepository;
import revolut.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
public class TransactionServiceTest {
    private final static Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);

    private static final String ACCOUNT_ID_1 = "1";
    private static final String ACCOUNT_ID_2 = "2";

    private final BankRepository repository = BankRepository.getInstance();
    private final TransactionService transactionService = TransactionService.getInstance();

    public Account accountA;
    public Account accountB;

    @Before
    public void setUp() {

        repository.clearAll();

        accountA = new Account(ACCOUNT_ID_1, "100.12");
        accountB = new Account(ACCOUNT_ID_2, "99.23");

        repository.addAccount(accountA);
        repository.addAccount(accountB);
    }

    @Test
    public void successfulTransaction() {
        TransactionDto trx = new TransactionDto(accountA, accountB, new BigDecimal("10.00"));

        transactionService.transfer(trx);

        assertEquals(new BigDecimal("90.12"), repository.get(accountA.getAcountNumber()).getAccountBalance());
        assertEquals(new BigDecimal("109.23"), repository.get(accountB.getAcountNumber()).getAccountBalance());
    }

    @Test
    public void concurrentSuccessfulTransactions() throws InterruptedException {
        Account src = new Account("3","3000");
        Account dest = new Account("4","2000");

        repository.addAccount(src);
        repository.addAccount(dest);

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        final Runnable transferAtoB = () -> {
            transactionService.transfer(new TransactionDto(src, dest, new BigDecimal("10.00")));
        };

        final Runnable transferBtoA = () -> {
            transactionService.transfer(new TransactionDto(dest, src, new BigDecimal("10.00")));
        };

        for(int i=0;i<200;i++){

            executorService.submit(transferAtoB);

            executorService.submit(transferAtoB);

            executorService.submit(transferBtoA);

        }

        executorService.shutdown(); //Completes the work
        executorService.awaitTermination(10,TimeUnit.SECONDS);

        assertEquals(new BigDecimal("1000.00"), repository.get("3").getAccountBalance());
        assertEquals(new BigDecimal("4000.00"), repository.get("4").getAccountBalance());

    }

    @Test
    public void insufficientBalanceTest() {
        final String lowBalanceAccountId = "5";
        Account lowBalanceAccount = new Account(lowBalanceAccountId, "0.12");
        repository.addAccount(lowBalanceAccount);

        TransactionDto trx = new TransactionDto(lowBalanceAccount, accountB, new BigDecimal("10.00"));

        assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(trx));

        assertEquals(new BigDecimal("0.12"), repository.get(lowBalanceAccount.getAcountNumber()).getAccountBalance());
        assertEquals(new BigDecimal("99.23"), repository.get(ACCOUNT_ID_2).getAccountBalance());
    }

    @Test
   public void nonExistingSourceAccountThrowsException() {
        final String nonExistingAccount = "999";
        Account nonExistingAcc = new Account(nonExistingAccount, "0.12");
        TransactionRepresentation trx = new TransactionRepresentation(nonExistingAccount, accountB.getAcountNumber(), new BigDecimal("10.00"));

        TransactionDto transactionDto = transactionService.from(trx);
        assertThrows(IllegalAccountOperationException.class, () -> transactionService.transfer(transactionDto));
    }

    @Test
    public void nonExistingTargetAccountThrowsException() {
        final String nonExistingAccount = "999";
        Account nonExistingAcc = new Account(nonExistingAccount, "0.12");
        //TransactionDto trx = new TransactionDto(accountA, nonExistingAcc, new BigDecimal("10.00"));

        TransactionRepresentation trx = new TransactionRepresentation(accountA.getAcountNumber(), nonExistingAccount, new BigDecimal("10.00"));

        TransactionDto transactionDto = transactionService.from(trx);
        assertThrows(IllegalAccountOperationException.class, () -> transactionService.transfer(transactionDto));
    }
}
