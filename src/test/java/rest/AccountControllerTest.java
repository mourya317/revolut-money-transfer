package rest;

import revolut.model.Account;
import revolut.repository.BankRepository;
import revolut.rest.AccountController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
public class AccountControllerTest extends JerseyTest {
    private static final String ACCOUNT_NUMBER = "acountNumber";
    private static final String ACCOUNT_BALANCE = "accountBalance";

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
    public void validCallShouldReturn_200() {
        expect().statusCode(200).contentType(ContentType.JSON)
                .when().get("/accounts/1");
    }

    @Test
    public void nonExistingAccountShouldReturn_204_NoContentStatus() {
        expect().statusCode(204)
                .when().get("/accounts/100");
    }

    @Test
    public void shouldReturnAllAccounts() {
        given().when()
                .get("/accounts")
                .then()
                .body("size()", is(3));
    }

    @Test
    public void shouldReturnSpecificAccountExactData() {
        given().when()
                .get("/accounts/1")
                .then()
                .body(ACCOUNT_NUMBER, equalTo("1"))
                .body(ACCOUNT_BALANCE, equalTo(100.1f));
    }

    @Test
    public void shouldBeAbleToAddNewAccount() {
        Account newAccount = new Account("4", "5.0");

        given().contentType("application/json").body(newAccount)
                .when()
                .post("/accounts")
                .then()
                .body(ACCOUNT_NUMBER, equalTo("4"))
                .body(ACCOUNT_BALANCE, equalTo(5.0f));
    }

    @Override
    public Application configure() {
        return new ResourceConfig(AccountController.class);
    }

    public static void initializeTestData() {

        BankRepository repository = BankRepository.getInstance();

        repository.clearAll();

        repository.addAccount(new Account("1", "100.10"));
        repository.addAccount(new Account("2", "90.22"));
        repository.addAccount(new Account("3", "20.22"));

    }
}
