package api;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import POJO.AddBook;
import POJO.Bookdata;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuliderTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com/";

		AddBook p = new AddBook();
		p.setAccuracy(50);
		p.setName("Sundar");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29, side layout, cohen 09");
		p.setWebsite("website");
		p.setLanguage("ENglish-IN");

		List<String> newList = new ArrayList<String>();
		newList.add("shoe park");
		newList.add("shop");
		p.setTypes(newList);

		Bookdata pl = new Bookdata();
		pl.setLat(-38.383494);
		pl.setLng(33.427362);

		p.setLocation(pl);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		RequestSpecification req1 = given().spec(req).body(p);

		Response response = req1.when().post("maps/api/place/add/json").then().log().all().spec(resp).extract()
				.response();

		String responseString = response.asString();
		System.out.println(responseString);

	}

}
