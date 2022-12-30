package ExcelDrivenFramework.ExcelDrivenFramework;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExcelDriven {
	public static String id ;
	@Test
	public static void addBook() throws IOException {
		DataDriven d = new DataDriven();
		ArrayList<String> data = DataDriven.getdata("addBook", "RestAssured");

		Map<String, Object> map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));

		RestAssured.baseURI = "http://216.10.245.166";
		Response resp = given().header("Content-Type", "application/json")
				.body(map)
				.when().post("/Library/Addbook.php").then().assertThat().log().all().statusCode(200).extract()
				.response();
		JsonPath js = ReusableMethods.rawToJson(resp);
		 id = js.get("ID");
		System.out.println(id);

	}
	
	@Test
	
	public void deleteBook() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String resp = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"ID\" : \""+id+"\"\r\n" + "}")
				.when().post("/Library/DeleteBook.php ").then().assertThat().statusCode(200).extract().response().asString();
		System.out.println("Final message =="+ resp);
	}

	
}
