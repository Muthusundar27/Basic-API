package api;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		
		String [] expected = {"Selenium Webdrivefr Java", "Cypress", "Protractor"};

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("access_token");

		GetCourse Gc = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println(Gc.getInstructor());
		System.out.println(Gc.getLinkedIn());
		System.out.println(Gc.getCourses().getApi().get(0).getCourseTitle());
		
		List<Api> apiCourses = Gc.getCourses().getApi();
		
		for (int i=0; i<apiCourses.size(); i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		ArrayList a = new ArrayList();
		List<WebAutomation> webCourses = Gc.getCourses().getWebAutomation();

		for (int i = 0; i < webCourses.size(); i++) {
			System.out.println(webCourses.get(i).getCourseTitle());
			a.add(webCourses.get(i).getCourseTitle());

		}
		List<String> expectedCoures = Arrays.asList(expected);
		Assert.assertTrue(a.equals(expectedCoures), "Expected courses is not present");
		
		
	}

}
