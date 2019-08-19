package revolut.repository;

import revolut.exceptions.IllegalAccountOperationException;
import revolut.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class BankRepository {

    //in-memory bank accounts
    private static final BankRepository BANK_REPOSITORY = new BankRepository(new ConcurrentHashMap<>());
    private final Map<String , Account> accounts;

    public BankRepository(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public static BankRepository getInstance(){
        return BANK_REPOSITORY;
    }

    public Collection<Account> getAllAccounts(){
        return accounts.values();
    }

    public Account get(String accountID){
        return accounts.get(accountID);
    }

    public Account addAccount(Account account){
        Account isExistsAlready = accounts.putIfAbsent(account.getAcountNumber(), account);
        if(isExistsAlready != null){
            throw new IllegalAccountOperationException("Account with id "+account.getAcountNumber()+" already exists");
        }

        return accounts.get(account.getAcountNumber());
    }

    public void clearAll(){
        synchronized (accounts){
            accounts.clear();
        }
    }
}
