package api;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
	JsonPath js = new JsonPath(Payload.CoursePrice());
	
	//Print No of courses returned by API
	int count = js.getInt("courses.size()");
	System.out.println(count);
	
	//Print Purchase Amount
	int purchaseAmount = js.getInt("dashboard.purchaseAmount");
	System.out.println(purchaseAmount);
	
	//Print Title of the first course
	String title = js.get("courses[0].title");
	System.out.println(title);
	
	//Print All course titles and their respective Prices	
	for (int i=0; i<count; i++) {
		String coursesTitle = js.get("courses["+i+"].title");
		System.out.println(coursesTitle);
		System.out.println(js.getInt("courses["+i+"].price"));
			
	}
	
	//Print no of copies sold by RPA Course
	System.out.println("Print no of copies sold by RPA Course");
	for (int i=0; i<count; i++) {
		String coursesTitle = js.get("courses["+i+"].title");
		if(coursesTitle.equalsIgnoreCase("RPA")) {
			int copies=js.getInt("courses["+i+"].copies");
			System.out.println(copies);
			break;
		}
	}
	
	//Verify if Sum of all Course prices matches with Purchase Amount
	   int totalAmount = js.getInt("dashboard.purchaseAmount");
	   int actualAmount=0;
	   for (int i=0; i<count; i++) {
		  int price = js.getInt("courses["+i+"].price");
		  int copies = js.getInt("courses["+i+"].copies");
		  actualAmount += price*copies;
	   }
	   if(totalAmount==actualAmount) {
		   System.out.println("Both amount are equals");
	   }
}
}