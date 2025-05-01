package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import files.ReUsableMethods;

public class GetIssue {

	public static void main(String[] args) {


		RestAssured.baseURI="https://muthusundar044.atlassian.net";
		
		String response = given().header("Authorization", "Basic bXV0aHVzdW5kYXIwNDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHRZMHVlRHF1c2dPRTdtZ3hZdWFKTzJOYUpzTkl4TWRia1ZSdXdsMGRJODRlZldfVTlVa2dHa1JKaWoxMmpLYWVGRzhUMERzTkdzZXNzdDVvNk9PTEZzNTlDMXRjaEp5Z0FaR1dxaW1Lb1d6dmxKYnloY3RzR2tEanhPVXJzZVpYaFowSUpWVGhCVkVfUW1DSXRjdEFhUkl2dmZBZGQ2bXozRXhzcDNhVW1NYz0yODQ3RUUxNQ==")
		.when().get("rest/api/3/issue/10005")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		String expectedImg = "background.jpg";
	  JsonPath js = ReUsableMethods.rawToJson(response);
	  String actualImg = js.getString("fields.attachment[0].filename");
	  System.out.println(actualImg);
	  
	  Assert.assertEquals(actualImg, expectedImg);
	  
	  

	}

}
