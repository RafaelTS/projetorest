package br.sc.rafael.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;

public class BarrigaTest extends BaseTest {
	
	private String TOKEN;
	
	@Before
	public void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "eduardo@gmail.com");
		login.put("senha", "123456");
		
		TOKEN = given()
			.body(login)
		.when()
		
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token");
			;	
	}
	
	@Test
	public void naoDeveAcessarApiSemToken() {
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.log().all()
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta qualquer\"}")
			
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
		
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta alterada\"}")
		.when()
			.post("/contas/155458")
		.then()
			.statusCode(200)
			.body("nome", is("conta alterada"))
		;
	}
	
	@Test
	public void naoDeveInserirContaComMesmoNome() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{ \"nome\": \"conta alterada\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome"))
		;
	}
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
		;
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems("rodar o post e copiar as mensgens"))
		;
	}
	
	@Test
	public void naoDeveCadastrarMovimentacaoFutura() {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao("15/05/2099");
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("msg", hasItem("Data de movimentação deve ser menor ou igual a data atual"))
		;
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		given()
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.delete("/contas/")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void deveCalcularSaldoConta() {
		given()
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.delete("/transacoes/15155")
		.then()
			.statusCode(204)
		;
	}
	
	
	
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setId(11221); //quando passar login volto na aula de inclusao de conta para recuperar o id
		//mov.setUsuario_id('usuario_id);
		mov.setDescricao("Descrição da movimentação");
		mov.setEnvolvido("Envolvido da movimentaoção");
		mov.setTipo("REC");
		mov.setData_transacao("10/10/2000");
		mov.setData_pagamento("10/10/2010");
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
}
