package api;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileJsonTips {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(GenerateStringFromResource("C:\\Users\\a851335\\Downloads")).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("scope", equalTo("APP")).extract().response()
				.asString();
		System.out.println(response);
	}

	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}
}
