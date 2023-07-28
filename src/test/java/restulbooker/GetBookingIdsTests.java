package restulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests extends BaseTest {

    @Test
    public void getBookingIdsWithoutFilerTest(){

        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();


        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it is not");
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "wat not expecting the list to be empty");
    }

    @Test
    public void getBookingIdsWithFilterTest() {

        spec.queryParam("firstname", "Sally");

        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it is not");
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "wat not expecting the list to be empty");


    }
}
