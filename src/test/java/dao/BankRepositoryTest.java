package dao;

import revolut.exceptions.IllegalAccountOperationException;
import revolut.model.Account;
import revolut.repository.BankRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
@RunWith(MockitoJUnitRunner.class)
public class BankRepositoryTest {

    private BankRepository bankRepository = BankRepository.getInstance();


    @Before
    public void setUp(){
        bankRepository.clearAll();
    }

    @Test
    public void addingAccountShouldSucceed() {
        Account someAccount = new Account();

        bankRepository.addAccount(someAccount);

        assertEquals(1, bankRepository.getAllAccounts().size());
        assertEquals(someAccount, bankRepository.get(someAccount.getAcountNumber()));
    }

    @Test(expected = IllegalAccountOperationException.class)
    public void addingDuplicateAccountShouldFail() {
        String accountId = "1234";
        Account testAccount = new Account(accountId, "10000");

        bankRepository.addAccount(testAccount);

        Account accountWithDuplicatedId = new Account(accountId, "0");

        bankRepository.addAccount(accountWithDuplicatedId);
    }

    @Test
    public void shouldBePossibleToRemoveAllAccounts() {
        int numberOfAccounts = 5;

        insertAccountsIntoRepository(numberOfAccounts);

        assertEquals(numberOfAccounts, bankRepository.getAllAccounts().size());

        bankRepository.clearAll();

        assertEquals(0, bankRepository.getAllAccounts().size());
    }

    @Test
    public void shouldReturnExistingAccount() {
        final String existingId = "1";
        Account account = new Account(existingId, "10");

        bankRepository.addAccount(account);

        Account retrievedAccount = bankRepository.get(existingId);

        assertEquals(account, retrievedAccount);
    }

    @Test
    public void shouldReturnNULLForNonExistingAccount() {

        Account retrievedAccount = bankRepository.get("2");

        assertNull(retrievedAccount);
    }



    private void insertAccountsIntoRepository(int numberOfAccounts) {
        IntStream.range(0, numberOfAccounts).forEach(a -> bankRepository.addAccount(new Account()));
    }

}
