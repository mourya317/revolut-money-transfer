package revolut.dto;

import java.math.BigDecimal;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class AccountRepresentation {
    private final String id;
    private final BigDecimal balance;

    public AccountRepresentation(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public AccountRepresentation(){
        this.id = "";
        this.balance = BigDecimal.ZERO;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountRepresentation{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }

}
