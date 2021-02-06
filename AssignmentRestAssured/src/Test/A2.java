package Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import Payload.Pay;

public class A2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//POST API
		String var = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Pay.payload()).when().post("/maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(var);
		String placeid = js.getString("place_id");
		
		System.out.println(placeid);
		
		//PUT API
		String addres = "GOA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+ placeid + "\",\r\n" + 
				"\"address\":\""+ addres + "\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"").when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		
		//GET API
		String var2 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response().asString();
		JsonPath  js2 = new JsonPath(var2);
		String add = js2.getString("address");
		System.out.println(add);
		
		//DELETE API
		String state = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"place_id\":\""+ placeid + "\"\r\n" + 
				"}\r\n" + 
				"").when().delete("/maps/api/place/delete/json").then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js3 = new JsonPath(state);
		String check = js3.getString("status");
		System.out.println(check);
	}

}
