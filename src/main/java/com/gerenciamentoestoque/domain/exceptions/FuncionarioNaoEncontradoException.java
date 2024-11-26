package com.gerenciamentoestoque.domain.exceptions;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEncontradaException
{
	private static final long serialVersionUID = 1L;
	public FuncionarioNaoEncontradoException(String message)
	{
		super(message);
	}
	public FuncionarioNaoEncontradoException(Long funcionarioId) {
	  this(String.format("Não existe um cadastro de funcionário com código %d", funcionarioId));
	}
}
