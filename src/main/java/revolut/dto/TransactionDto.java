package revolut.dto;

import revolut.model.Account;

import java.math.BigDecimal;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class TransactionDto {

    private final Account srcAccount;
    private final Account destAccount;
    private final BigDecimal txnAmount;

    public TransactionDto(Account srcAccount, Account destAccount, BigDecimal txnAmount) {
        this.srcAccount = srcAccount;
        this.destAccount = destAccount;
        this.txnAmount = txnAmount;
    }

    public Account getSrcAccount() {
        return srcAccount;
    }

    public Account getDestAccount() {
        return destAccount;
    }

    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "srcAccountId='" + srcAccount + '\'' +
                ", destAccountId='" + destAccount + '\'' +
                ", txnAmount=" + txnAmount +
                '}';
    }


}
