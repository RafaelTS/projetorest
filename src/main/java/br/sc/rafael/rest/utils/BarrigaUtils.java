package br.sc.rafael.rest.utils;

import io.restassured.RestAssured;

public class BarrigaUtils {
	
	public static Integer getIdContaPeloNome(String nome) {
		return RestAssured.get("/contas?nome="+nome).then().extract().path("id[0]");
		
	}
	
	public static Integer getIdMovimentacaoPeloNome(String nome) {
		return RestAssured.get("/transacoes?nome="+nome).then().extract().path("id[0]");
	}
}
