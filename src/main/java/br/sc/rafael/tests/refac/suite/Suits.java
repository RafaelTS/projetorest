package br.sc.rafael.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.sc.rafael.rest.core.BaseTest;
import br.sc.rafael.tests.refac.AuthTest;
import br.sc.rafael.tests.refac.ContasTest;
import br.sc.rafael.tests.refac.MovimentacoesTest;
import br.sc.rafael.tests.refac.SaldoTest;
import io.restassured.RestAssured;

@RunWith(Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacoesTest.class,
	SaldoTest.class,
	AuthTest.class
	
})
public class Suits extends BaseTest{
	
	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<String, String>();

		login.put("email", "rafaeltorress@gmail.com");
		login.put("senha", "123");
		
		String TOKEN = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
			;
			
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
		
		RestAssured.get("/reset").then().statusCode(200);
		
	}

}
