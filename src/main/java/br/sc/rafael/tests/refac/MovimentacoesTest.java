package br.sc.rafael.tests.refac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.sc.rafael.rest.core.BaseTest;
import br.sc.rafael.rest.utils.BarrigaUtils;
import br.sc.rafael.rest.utils.DateUtils;
import br.sc.rafael.tests.Movimentacao;

public class MovimentacoesTest extends BaseTest {
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();

		given()
				.body(mov)
				.when()
				.post("/transacoes")
				.then()
				.statusCode(201)
				.body("descricao", is("Descrição da movimentação"))
				.body("envolvido", is("Envolvido da movimentaoção"))
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
						"Data da Movimentação é obrigatório",
						"Data do pagamento é obrigatório",
						"Descrição é obrigatório",
						"Interessado é obrigatório",
						"Valor é obrigatório",
						"Valor deve ser um número",
						"Situação é obrigatório"))
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
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
	}

	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta com movimentacao");
		
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
		Integer MOV_ID = BarrigaUtils.getIdMovimentacaoPeloNome("Movimentacao para exclus�o");
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}

	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(BarrigaUtils.getIdContaPeloNome("Conta para movimentacoes"));
		//mov.setUsuario_id('usuario_id);
		mov.setDescricao("Descrição da movimentação");
		mov.setEnvolvido("Envolvido da movimentaoção");
		mov.setTipo("REC");
		mov.setData_transacao(DateUtils.getDataDiferencaDias(-1));
		mov.setData_pagamento(DateUtils.getDataDiferencaDias(5));
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
	

}
