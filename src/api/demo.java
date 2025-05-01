package api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class demo {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//add place
		String res = given().log().all().queryParam("key", "qaclick123")
				.body("{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
						+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Muthu Sundar\",\r\n"
						+ "  \"phone_number\": \"(+91) 733 893 3937\",\r\n"
						+ "  \"address\": \"29, Housing board, cohen 09\",\r\n" + "  \"types\": [\r\n"
						+ "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n"
						+ "  \"website\": \"http://google.com\",\r\n" + "  \"language\": \"French-IN\"\r\n" + "}\r\n"
						+ "")
				.header("Content-Type", "application/json").when().post("maps/api/place/add/json").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		System.out.println(res);
		JsonPath js = new JsonPath(res);
		String placeId = js.get("place_id");
		System.out.println(placeId);
		
		// get place
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Content-Type", "application/json")
				.addQueryParam("key", "qaclick123").addQueryParam("place_id", placeId).build();
		RequestSpecification req1 = given().log().all().spec(req);
		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
		Response response = req1.when().get("maps/api/place/get/json").then().log().all().spec(resp).extract().response();
		System.out.println(response.asString());
	}
}