package api;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUsableMethods;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJSON {
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";

		String response = given().header("Content-Type", "application/json").body(Payload.AddBook(isbn, aisle)).when()
				.post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);

		// Delete the added book
		given().body(Payload.deleteBook(id)).when().delete("Library/DeleteBook.php").then().log().all().assertThat()
				.statusCode(200);
	}

	@DataProvider(name="BooksData")
	public Object[][] getData(){
		return new Object[][] {{"abcd", "1234"}, {"muth", "2345"}, {"sind", "2721"}};
	}
}
