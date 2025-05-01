package api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class basics {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";

//		Add Place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("scope", equalTo("APP")).extract().response()
				.asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		String newAddress = "70 Summer walk, Africa";

		System.out.println(placeId);

		// Update place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json")
				.then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

//		Get Place
		String getPlaceResponse = given().log().all()
				.queryParam("key", "qaclick123")
				.queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);

		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);

		Assert.assertEquals(actualAddress, newAddress);

		// Delete the place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "    \"place_id\":\""+placeId+"\"\r\n"
				+ "}\r\n"
				+ "")
		.when().delete("maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200);
	}
}
