package com.devteam.biblioteca.web.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	DADO_INELEGIVEL("/dado-inelegivel", "Dado de entrada inelegivel"),
	PROPRIEDADE_IGNORADA("/propriedade-ignorada", "Dado de entrada ignorado"),
	PROPRIEDADE_INVALIDA("/propriedade-invalida", "Propriedade de entrada inválida"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido."),
	URL_INVALIDA("/url-invalida", "Url digitada inválida."),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
	

}
