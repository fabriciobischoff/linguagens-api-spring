package com.linguagens.domain.exceptions;

public class LinguagemNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public LinguagemNaoEncontradaException(String message) {
		super(message);
	}
	
	public LinguagemNaoEncontradaException(Long linguagemId) {
		this(String.format("Não existe um cadastro de linguagem com o código %d", linguagemId));
	}

}
