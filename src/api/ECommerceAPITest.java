package api;

import POJO.LoginReqForEComm;
import POJO.LoginResponseForECommerce;
import POJO.OrderDetailReqForEComm;
import POJO.OrdersReqForEComm;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommerceAPITest {

	public static void main(String[] args) {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginReqForEComm loginreq = new LoginReqForEComm();
		loginreq.setUserEmail("muthusundar044@gmail.com");
		loginreq.setUserPassword("Sundar@04");

		RequestSpecification reqlogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginreq);

		LoginResponseForECommerce loginresponse = reqlogin.when().post("/api/ecom/auth/login").then().assertThat()
				.statusCode(200).extract().response().as(LoginResponseForECommerce.class);

		System.out.println(loginresponse.getToken());
		String token = loginresponse.getToken();
		System.out.println(loginresponse.getUserId());
		String userId = loginresponse.getUserId();

		// Add product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "qwerty")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "115")
				.param("productDescription", "Addias Originals").param("productFor", "women").multiPart("productImage",
						new File("C:\\Users\\a851335\\OneDrive - Eviden\\Documents\\Learning\\RestApi\\Test.jpg"));

		String addProductResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().log().all()
				.assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		System.out.println(productId);

		// Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		OrderDetailReqForEComm orderdetail = new OrderDetailReqForEComm();
		orderdetail.setCountry("India");
		orderdetail.setProductOrderedId(productId);

		List<OrderDetailReqForEComm> ls = new ArrayList<OrderDetailReqForEComm>();
		ls.add(orderdetail);
		OrdersReqForEComm orderreq = new OrdersReqForEComm();
		orderreq.setOrders(ls);

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orderreq);
		String responseAddOrder = createOrderReq.when().post("api/ecom/order/create-order").then().log().all()
				.assertThat().statusCode(201).extract().response().asString();
		System.out.println(responseAddOrder);
		JsonPath js1 = new JsonPath(responseAddOrder);
		String orderID = js1.get("orders[0]");

		
		// Get order 
		RequestSpecification getOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		RequestSpecification getOrderReq = given().log().all().spec(getOrderBaseReq).queryParam("id", orderID);
		String orderDetails = getOrderReq.when().get("/api/ecom/order/get-orders-details").then().assertThat().log().all()
		.statusCode(200).extract().response().asString();
		
		System.out.println(orderDetails);
		
		// Delete Product
		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		RequestSpecification deleteOrderReq = given().log().all().spec(deleteOrderBaseReq).pathParam("productid",
				productId);
		String deleteResponse = deleteOrderReq.when().delete("api/ecom/product/delete-product/{productid}").then().assertThat()
				.statusCode(200).extract().response().asString();
		System.out.println(deleteResponse);
		
	}

}
