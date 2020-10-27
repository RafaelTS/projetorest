package br.sc.rafael.rest.core;

import io.restassured.http.ContentType;

public interface Constantes {
	
	//String APP_BASE_URL = "https://seubarriga.wcaquino.me";
	String APP_BASE_URL = "https://srbarriga.herokuapp.com/";
	Integer APP_PORT = 443;
	String APP_BASE_PATH = "";
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT = 25000l;

}
