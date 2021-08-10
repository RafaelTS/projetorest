package br.sc.rafael.tests.refac;

import static br.sc.rafael.rest.utils.BarrigaUtils.getIdContaPeloNome;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;

public class ContasTest extends BaseTest {
	
	
	@Test
	public void deveIncluirContaComSucesso() {
		Map<String, String> contaInserida = new HashMap<String, String>();
		contaInserida.put("nome", "Conta inserida");
		given()
			.body(contaInserida)
		//	.body("{ \"nome\": \"Conta inserida\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;		
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		Integer CONTA_ID = getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{ \"nome\": \"Conta alterada\"}")
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
			.body("nome", is("Conta alterada"))
		;
	}
	

	@Test
	public void naoDeveInserirContaComMesmoNome() {
		given()
			.body("{ \"nome\": \"Conta mesmo nome\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
	
}
