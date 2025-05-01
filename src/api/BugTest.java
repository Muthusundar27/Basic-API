package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {
	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://muthusundar044.atlassian.net/";
		//create bug
		String createResponse=given()
		.header("Content-Type", "application/json")
		.header("Authorization", "Basic bXV0aHVzdW5kYXIwNDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHRZMHVlRHF1c2dPRTdtZ3hZdWFKTzJOYUpzTkl4TWRia1ZSdXdsMGRJODRlZldfVTlVa2dHa1JKaWoxMmpLYWVGRzhUMERzTkdzZXNzdDVvNk9PTEZzNTlDMXRjaEp5Z0FaR1dxaW1Lb1d6dmxKYnloY3RzR2tEanhPVXJzZVpYaFowSUpWVGhCVkVfUW1DSXRjdEFhUkl2dmZBZGQ2bXozRXhzcDNhVW1NYz0yODQ3RUUxNQ==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"MST\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \" to open PDF.\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
		.log().all()
		.when().post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(createResponse);
		String issueId = js.getString("id");
		System.out.println(issueId);
		
		//add attachments
		given().pathParam("key", issueId)
		.header("X-Atlassian-Token"," no-check")
		.header("Authorization","Basic bXV0aHVzdW5kYXIwNDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHRZMHVlRHF1c2dPRTdtZ3hZdWFKTzJOYUpzTkl4TWRia1ZSdXdsMGRJODRlZldfVTlVa2dHa1JKaWoxMmpLYWVGRzhUMERzTkdzZXNzdDVvNk9PTEZzNTlDMXRjaEp5Z0FaR1dxaW1Lb1d6dmxKYnloY3RzR2tEanhPVXJzZVpYaFowSUpWVGhCVkVfUW1DSXRjdEFhUkl2dmZBZGQ2bXozRXhzcDNhVW1NYz0yODQ3RUUxNQ==")
		.multiPart("file", new File("C:\\Users\\a851335\\OneDrive - Eviden\\Pictures\\Screenshots\\Screenshot 2024-04-12 142036.png"))
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		// get added details
		given().log().all().
		header("Authorization", "Basic bXV0aHVzdW5kYXIwNDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHRZMHVlRHF1c2dPRTdtZ3hZdWFKTzJOYUpzTkl4TWRia1ZSdXdsMGRJODRlZldfVTlVa2dHa1JKaWoxMmpLYWVGRzhUMERzTkdzZXNzdDVvNk9PTEZzNTlDMXRjaEp5Z0FaR1dxaW1Lb1d6dmxKYnloY3RzR2tEanhPVXJzZVpYaFowSUpWVGhCVkVfUW1DSXRjdEFhUkl2dmZBZGQ2bXozRXhzcDNhVW1NYz0yODQ3RUUxNQ==")
		.pathParam("key", issueId)
		.when().get("rest/api/3/issue/{key}").then().log().all().assertThat().statusCode(200).extract().response();
	}
	
}
