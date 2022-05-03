package br.sc.rafael.rest.utils;

import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Others {

    @Test
    public void deveRetornarStatusCustomer(){

        given()
        .when()
            .get("https://customer-toggle.financeiro.qa.aws.intranet.pagseguro.uol/settlement-contracts/customers/63196353-22b9-472d-b3fb-46d8533c8e71/toggle/")
        .then()
            .log().all()
            .statusCode(200)
        ;
    }
}
