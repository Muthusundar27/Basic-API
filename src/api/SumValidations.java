package api;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidations {

	@Test
	public void sumOfCourses() {
		int sum = 0;
		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count = js.getInt("courses.size()");
		
		for (int i=0; i<count; i++) {
			int prices = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = prices * copies;
			System.out.println(amount);
			sum = amount + sum;
			
			
		}
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(purchaseAmount, sum);
	}
	
}
