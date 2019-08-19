package revolut.service;

import revolut.dto.AccountRepresentation;
import revolut.dto.TransactionDto;
import revolut.exceptions.InsufficientFundsException;
import revolut.model.Account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class BankTransaction implements TransactionWrapper {

    @Override
    public List<AccountRepresentation> doTransfer(TransactionDto transactionDto) {
        Account src = transactionDto.getSrcAccount();
        Account dest = transactionDto.getDestAccount();
        BigDecimal amount = transactionDto.getTxnAmount();

        if(src.getAccountBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException("Transaction failed due to insufficient funds.");
        }

        src.withdraw(amount);
        dest.deposit(amount);

        return Collections.unmodifiableList(Arrays.asList(Account.to(src), Account.to(dest)));
    }

}
