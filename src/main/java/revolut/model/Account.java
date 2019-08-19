package revolut.model;

import revolut.dto.AccountRepresentation;
import revolut.exceptions.IllegalAccountOperationException;
import revolut.exceptions.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class Account {

    private final String acountNumber;
    private BigDecimal accountBalance;

    public Account(){
        this.acountNumber = UUID.randomUUID().toString();
        this.accountBalance = BigDecimal.ZERO;
    }


    public Account(String acountNumber, String accountBalance){
        this.acountNumber = acountNumber;
        this.accountBalance = new BigDecimal(accountBalance);
    }

    public String getAcountNumber() {
        return acountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

    public BigDecimal deposit(BigDecimal amt){
        validate(amt);
        accountBalance = accountBalance.add(amt);
        return getAccountBalance();
    }

    public BigDecimal withdraw(BigDecimal amt){
        validate(amt);

        if(accountBalance.compareTo(amt) < 0 ){
            throw new InsufficientFundsException("Transaction failed due to insufficient funds.");
        }

        accountBalance = accountBalance.subtract(amt);
        return getAccountBalance();
    }

    private void validate(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0 ){
            throw new IllegalAccountOperationException("The transferred amount should be greater than zero.");
        }
    }

    public static AccountRepresentation to(Account account){
        return new AccountRepresentation(account.getAcountNumber(),account.getAccountBalance());
    }


    @Override
    public String toString() {
        return "Account{" +
                "acountNumber='" + acountNumber + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(acountNumber, account.acountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acountNumber);
    }
}
