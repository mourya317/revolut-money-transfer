package rest;

import revolut.dto.TransactionRepresentation;
import revolut.model.Account;
import revolut.repository.BankRepository;
import revolut.rest.TransactionController;
import io.restassured.RestAssured;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
public class TransactionControllerTest extends JerseyTest {


    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9998;
    }

    @Before
    public void initializeData() {
        initializeTestData();
    }

    @Test
    public void validTransactionShouldSucceed() {
        TransactionRepresentation txn = new TransactionRepresentation("2", "3", new BigDecimal("10.00"));

        given().contentType("application/json").body(txn)
                .when()
                .post("/transaction/transact")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo("2"))
                .body("[0].balance", equalTo(80.22f))
                .body("[1].id", equalTo("3"))
                .body("[1].balance", equalTo(30.22f));
    }

    @Override
    public Application configure() {
        return new ResourceConfig(TransactionController.class);
    }

    public static void initializeTestData() {

        BankRepository repository = BankRepository.getInstance();

        repository.clearAll();

        repository.addAccount(new Account("1", "100.10"));
        repository.addAccount(new Account("2", "90.22"));
        repository.addAccount(new Account("3", "20.22"));

    }
}
