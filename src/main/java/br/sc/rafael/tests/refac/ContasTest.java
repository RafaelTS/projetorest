package br.sc.rafael.tests.refac;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ContasTest extends BaseTest {
	
	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<String, String>();
//		login.put("email", "rafaeltorress@gmail.com");
	//	login.put("senha", "123");
		login.put("email", "kelly.rabelo@gmail.com");
		login.put("senha", "momota01");
		
		String TOKEN = given()
			.body(login)
		.when()
			.contentType(ContentType.JSON)
			.post("/login")
		.then()
			.statusCode(200)
			.extract().path("token");
			;
			
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
		
	}
	
	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.body("{ \"nome\": \"Conta inserida\"}")
		.when()
			.post("/addConta")
		.then()
			.statusCode(201)
		;
		
	}
	
}
