package revolut.dto;

import java.math.BigDecimal;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
public class TransactionRepresentation {
    private final String source;
    private final String target;
    private final BigDecimal amount;

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionRepresentation(){
        source = "";
        target = "";
        amount = BigDecimal.ZERO;
    }

    public TransactionRepresentation(String source, String target, BigDecimal amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }


}
