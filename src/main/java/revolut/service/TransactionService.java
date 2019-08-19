package revolut.service;

import revolut.dto.AccountRepresentation;
import revolut.dto.TransactionDto;
import revolut.dto.TransactionRepresentation;
import revolut.exceptions.IllegalAccountOperationException;
import revolut.model.Account;
import revolut.repository.BankRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
public class TransactionService {

    private static final Object lock = new Object();
    private static final TransactionService INSTANCE = new TransactionService(BankRepository.getInstance());

    private final BankRepository repository;
    private final BankTransaction bankTransaction;

    private TransactionService(BankRepository repository){
        this.repository = repository;
        this.bankTransaction = new BankTransaction();
    }

    public List<AccountRepresentation> transfer(final TransactionDto txn){
        Account fromAccount = txn.getSrcAccount();
        Account toAccount = txn.getDestAccount();

        if(fromAccount == null || toAccount == null){
            throw new IllegalAccountOperationException("Invalid source/destination Account.");
        }

        //Account hashed by unique account id
        int h1 = fromAccount.hashCode();
        int h2 = toAccount.hashCode();
        if(h1<h2){
            synchronized (fromAccount){
                synchronized (toAccount){
                    return bankTransaction.doTransfer(txn);
                }
            }
        }else if(h1>h2){
            synchronized (toAccount){
                synchronized (fromAccount){
                    return bankTransaction.doTransfer(txn);
                }
            }
        }else{
            //handle case for hash collision
            synchronized (lock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        return bankTransaction.doTransfer(txn);
                    }
                }
            }
        }
    }

    public TransactionDto from(TransactionRepresentation transactionRepresentation){
        return new TransactionDto(
                repository.get(transactionRepresentation.getSource()),
                repository.get( transactionRepresentation.getTarget()),
                transactionRepresentation.getAmount().setScale(2,BigDecimal.ROUND_HALF_DOWN));
    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }
}