package br.sc.rafael.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;
import br.sc.rafael.rest.utils.BarrigaUtils;

public class SaldoTest extends BaseTest {
	
	@Test
	public void deveCalcularSaldoConta(){
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para saldo");
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+ CONTA_ID +"}.saldo", is("534.00"))
			
		;
	}
}
