package api;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class AutomationPractice {

	@Test
	public void test() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		// CreateToken
		String response = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("auth").then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String token = js.getString("token");
		System.out.println(token);

		// Booking - GetBookingIds
		String res = given().when().get("booking").then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(res);
		// filter by checkin/checkout

		given().queryParam("checkin", "2023-03-13").queryParam("checkout", "2024-05-21").when().get("booking").then()
				.assertThat().statusCode(200).log().all().extract().response();

		// ping - health checkup
		String expectedResponse = "Created";
		String res1 = given().when().get("ping").then().log().all().extract().response().asString();
		JsonPath js1 = new JsonPath(res1);
		Assert.assertTrue(expectedResponse.equalsIgnoreCase(res1));

		// EndtoEnd flow create/update/get/partial-update/ delete
		// create booking
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com")
				.setContentType(ContentType.JSON).build();

		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		RequestSpecification req = given().log().all().spec(requestSpec).body(Payload.createBook());
		
		Response resp = req.when().post("booking").then().log().all().spec(responseSpec).extract().response();

		String responseString = resp.asString();
		System.out.println(responseString);
		
		// Update:
		
	}
}
