package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;


    @BeforeMethod
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }

    protected Response createBooking() {
        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2023-11-13");
        bookingdates.put("checkout", "2023-11-13");

        JSONObject body = new JSONObject();
        body.put("firstname", "Razvan");
        body.put("lastname", "Ares");
        body.put("totalprice", 150);
        body.put("depositpaid", true);



        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Breakfast");

        Response response = RestAssured.given().spec(spec).contentType(ContentType.JSON)
                .body(body.toString())
                .post("/booking");
        return response;
    }
}
