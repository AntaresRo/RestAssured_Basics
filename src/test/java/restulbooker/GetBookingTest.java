package restulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {

    @Test
    public void getBookingById(){


        spec.pathParam("id", 5);
        Response response = RestAssured
                            .given().spec(spec)
                            .get("booking/{id}")
                            .then()
                            .extract()
                            .response();


        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it is not");

        String firstName = response.jsonPath().getString("firstname");
        String expectedFirstName = "Susan";

        String lastName = response.jsonPath().getString("lastname");
        String expectedLastName = "Jones";

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(firstName, expectedFirstName,"First name should be: " + expectedFirstName + " but it is " + firstName);
        softAssert.assertEquals(lastName, expectedLastName, "First name should be: " + expectedLastName + " but it is " + lastName);
        softAssert.assertAll();


    }

}
