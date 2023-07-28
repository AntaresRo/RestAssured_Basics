package restulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {


    @Test
    public void partialUpdateBookingTest(){

        Response response = createBooking();
        response.print();

        int bookingid = response.jsonPath().getInt("bookingid");


        Response responseDelete = RestAssured.given().auth().preemptive().basic("admin", "password123")
                                .delete("https://restful-booker.herokuapp.com/booking/" + bookingid);


        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 200, but it's not");
        responseDelete.print();
        Assert.assertEquals(responseDelete.getBody().asString(), "Created", "Body should be 'Created'");




    }
}
