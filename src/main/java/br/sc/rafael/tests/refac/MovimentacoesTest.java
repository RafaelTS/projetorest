package br.sc.rafael.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;
import br.sc.rafael.rest.utils.DateUtils;
import br.sc.rafael.tests.Movimentacao;
import io.restassured.RestAssured;

public class MovimentacoesTest extends BaseTest {
	
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
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.body("descricao", is("Descri��o da movimenta��o"))
			.body("envolvido", is("Envolvido da movimentao��o"))
			.body("tipo", is("REC"))
			.body("valor", is("100.00"))
			.body("status", is(true))
		;
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
					"Data da Movimenta��o � obrigat�rio",
					"Data do pagamento � obrigat�rio",
					"Descri��o � obrigat�rio",
					"Interessado � obrigat�rio",
					"Valor � obrigat�rio",
					"Valor deve ser um n�mero",
					"Situa��o � obrigat�rio"))
		;
	}
	
	@Test
	public void naoDeveCadastrarMovimentacaoFutura() {
		Movimentacao mov = getMovimentacaoValida();
		mov.setData_transacao(DateUtils.getDataDiferencaDias(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("msg", hasItem("Data da Movimenta��o deve ser menor ou igual � data atual"))
		;
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		Integer CONTA_ID = getIdContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void deveDeletarMovimentacao() {
		Integer MOV_ID = getIdMovimentacaoPeloNome("Movimentacao para exclus�o");
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
	
	public Integer getIdContaPeloNome(String nome) {
		return RestAssured.get("/contas?nome="+nome).then().extract().path("id[0]");
	}
	
	public Integer getIdMovimentacaoPeloNome(String nome) {
		return RestAssured.get("/transacoes?nome="+nome).then().extract().path("id[0]");
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(getIdContaPeloNome("Conta para movimentacoes")); 
		//mov.setUsuario_id('usuario_id);
		mov.setDescricao("Descri��o da movimenta��o");
		mov.setEnvolvido("Envolvido da movimentao��o");
		mov.setTipo("REC");
		mov.setData_transacao(DateUtils.getDataDiferencaDias(-1));
		mov.setData_pagamento(DateUtils.getDataDiferencaDias(5));
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
}
