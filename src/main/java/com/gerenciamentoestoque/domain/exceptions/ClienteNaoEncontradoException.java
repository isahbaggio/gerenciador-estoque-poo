package com.gerenciamentoestoque.domain.exceptions;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException
{
	private static final long serialVersionUID = 1L;
	public ClienteNaoEncontradoException(String message)
	{
		super(message);
	}
	public ClienteNaoEncontradoException(Long clienteId)
	{
		this(String.format("Não existe um cadastro de cliente com código %d", clienteId));
	}
}
