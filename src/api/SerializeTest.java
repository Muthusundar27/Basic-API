package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddBook;
import POJO.Bookdata;

public class SerializeTest {

	public static void main(String[] args) {

       RestAssured.baseURI = "https://rahulshettyacademy.com/";
       
       AddBook p = new AddBook();
       p.setAccuracy(50);
       p.setName("Sundar");
       p.setPhone_number("(+91) 983 893 3937");
       p.setAddress("29, side layout, cohen 09");
       p.setWebsite("website");
       p.setLanguage("ENglish-IN");
       
       List<String> newList = new ArrayList<>();
       newList.add("shoe park");
       newList.add("shop");
       p.setTypes(newList);
       
       Bookdata pl = new Bookdata();
       pl.setLat(-38.383494);
       pl.setLng(33.427362);
       
       p.setLocation(pl);
       
       String res = given().log().all()
    		   .queryParam("key", "qaclick123").body(p)
       .when().post("maps/api/place/add/json")
       .then().log().all().assertThat().statusCode(200).extract().response().asString();
       
       System.out.println(res);
      
       
       

	}

}
