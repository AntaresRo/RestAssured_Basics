package restulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.BaseTest;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {


    @Test
    public void updateBookingTest(){

        Response response = createBooking();
        response.print();

        int bookingid = response.jsonPath().getInt("bookingid");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2023-11-13");
        bookingdates.put("checkout", "2023-11-13");

        JSONObject body = new JSONObject();
        body.put("firstname", "Test");
        body.put("lastname", "John");
        body.put("totalprice", 150);
        body.put("depositpaid", true);



        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        Response responsePut = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON)
                .body(body.toString()).put("/booking/" + bookingid);


        Assert.assertEquals(responsePut.getStatusCode(), 200, "Status code should be 200, but it's not");
        responsePut.print();

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responsePut.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Test", "firstname in response is not expected");

        String actualLastName = responsePut.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "John", "lastname in response is not expected");

        int price = responsePut.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = responsePut.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responsePut.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-11-13", "checkin in response is not expected");

        String actualCheckout = responsePut.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-11-13", "checkout in response is not expected");

        String actualAdditionalneeds = responsePut.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();



    }
}
