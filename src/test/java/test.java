import revolut.dto.AccountRepresentation;
import revolut.dto.TransactionDto;
import revolut.model.Account;
import revolut.service.BankTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 15-08-2019
 */
public class test {
    static Object lock = new Object();
    static final BankTransaction bankTransaction = new BankTransaction();
    public static void main(String[] args) throws InterruptedException {


        Account src = new Account("16666","300");
        Account dest = new Account("2","200");
        Account src1 = new Account("16666","0");

        System.out.println(System.identityHashCode(src)+"-"+src.hashCode());
        System.out.println(System.identityHashCode(src1)+"-"+src1.hashCode());
        System.out.println(System.identityHashCode(dest)+"-"+dest.hashCode());

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        for(int i=0;i<4;i++){
            executorService.submit(() -> {
                List<AccountRepresentation> transfer = transfer(src,dest,10);
                //System.out.println("Transfer 10 from 1->2 src balance:"+transfer);
            });

            executorService.submit(() -> {
                List<AccountRepresentation> transfer = transfer(src,dest,10);
                //System.out.println("Transfer 10 from 1->2 src balance:"+transfer);
            });

            executorService.submit(() -> {
                List<AccountRepresentation> transfer = transfer(dest,src,10);
                //System.out.println("Transfer 10 from 2->1 src balance:"+transfer);
            });
        }
        //executorService.awaitTermination(50000, TimeUnit.DAYS);

        System.out.println();

        //executorService.shutdown();
        executorService.awaitTermination(5,TimeUnit.SECONDS);
        System.out.println("Final balance:");
        System.out.println("balance in acc 1:"+src.getAccountBalance()+" - balance in acc2:"+dest.getAccountBalance());

        System.exit(1);

    }

    private static List<AccountRepresentation> transfer(Account src , Account dest, int amount){
        int h1 = System.identityHashCode(src);
        int h2 = System.identityHashCode(dest);
        /*int h1 = src.hashCode();
        int h2 = dest.hashCode();*/
        if(h1<h2){
            synchronized (src){
                synchronized (dest){
                    return bankTransaction.doTransfer(new TransactionDto(src, dest, new BigDecimal(amount)));
                }
            }
        }else if(h1>h2){
            synchronized (dest){
                synchronized (src){
                    return bankTransaction.doTransfer(new TransactionDto(src,dest,new BigDecimal(amount)));
                }
            }
        }else{
            //handle case for hash collision
            synchronized (lock) {
                synchronized (src) {
                    synchronized (dest) {
                        return bankTransaction.doTransfer(new TransactionDto(src, dest, new BigDecimal(amount)));
                    }
                }
            }
        }
    }
}
