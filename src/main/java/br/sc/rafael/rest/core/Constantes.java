package br.sc.rafael.rest.core;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

public interface Constantes {

	//http://restapi.wcaquino.me
	String APP_BASE_URL = "https://barrigarest.wcaquino.me/";
	Integer APP_PORT = 443;
	String APP_BASE_PATH = "";

	ContentType APP_CONTENT_TYPE = ContentType.JSON;

	Long MAX_TIMEOUT = 15000l;

}
