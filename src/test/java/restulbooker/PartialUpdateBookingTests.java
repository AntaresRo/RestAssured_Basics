package restulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.BaseTest;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTests extends BaseTest {


    @Test
    public void partialUpdateBookingTest(){

        Response response = createBooking();
        response.print();

        int bookingid = response.jsonPath().getInt("bookingid");



        JSONObject body = new JSONObject();
        body.put("firstname", "Sir");
        body.put("lastname", "TestALot");


        Response responsePatch = RestAssured.given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON)
                .body(body.toString()).patch("https://restful-booker.herokuapp.com/booking/" + bookingid);


        Assert.assertEquals(responsePatch.getStatusCode(), 200, "Status code should be 200, but it's not");
        responsePatch.print();

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responsePatch.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Sir", "firstname in response is not expected");

        String actualLastName = responsePatch.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "TestALot", "lastname in response is not expected");

        int price = responsePatch.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = responsePatch.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responsePatch.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-11-13", "checkin in response is not expected");

        String actualCheckout = responsePatch.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-11-13", "checkout in response is not expected");

        String actualAdditionalneeds = responsePatch.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();



    }
}
