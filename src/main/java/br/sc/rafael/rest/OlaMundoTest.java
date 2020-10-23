package br.sc.rafael.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	
	@Test
	public void testOlaMundo() {
		Response request = RestAssured.request(Method.GET, ("http://restapi.wcaquino.me/olamundo"));
		Assert.assertTrue(request.getBody().asString().equals("Ola mundo!"));
		Assert.assertTrue(request.statusCode() == 200);
		Assert.assertEquals(request.statusCode(), 200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRest() {
		Response request = RestAssured.request(Method.GET, ("http://restapi.wcaquino.me/olamundo"));
		ValidatableResponse validacao = request.then();
		validacao.statusCode(200);
		
		get("http://restapi.wcaquino.me/olamundo").then().statusCode(200);
	}
	
	@Test
	public void devoConhcerMatchersHamcrest() {
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5));
		//assertThat(impares, Matchers.hasSize(5));
	}
	
	@Test
	public void deveValidarBody() {
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
					
		
			
	}

}
