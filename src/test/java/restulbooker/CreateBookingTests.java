package restulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.BaseTest;
import org.example.Booking;
import org.example.BookingId;
import org.example.Bookingdates;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {


    @Test
    public void createBookingTest(){

        Response response = createBooking();
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Razvan", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Ares", "lastname in response is not expected");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-11-13", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-11-13", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();



    }

    @Test
    public void createBookingWithPOJOTest(){
        Bookingdates bookingdates = new Bookingdates("2023-11-13","2023-11-13");
        Booking booking = new Booking("Test", "Testulescu", 250, bookingdates,true, "Breakfast");

        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
        response.print();

        BookingId bookingId = response.as(BookingId.class);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        Assert.assertEquals(bookingId.getBooking().toString(),booking.toString());



    }


}
