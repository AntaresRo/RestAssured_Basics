package restulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.BaseTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheck(){


        given().
                spec(spec).
        when().
                get("/ping").
        then().
                assertThat().
                statusCode(201);
    }


    @Test
    public void headersAndCookies(){

        Response response = RestAssured.given(spec).get("/ping");
        Headers headers = response.getHeaders();
        //System.out.println(headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1);

        Cookies cookies = response.getDetailedCookies();
        System.out.println();
        System.out.println("Cookies: " + cookies);


    }

}
