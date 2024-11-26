package com.gerenciamentoestoque.domain.exceptions;

public class FornecedorNaoEncontradoException extends EntidadeNaoEncontradaException
{
	private static final long serialVersionUID = 1L;
	public FornecedorNaoEncontradoException(String message)
	{
		super(message);
	}
	public FornecedorNaoEncontradoException(Long fornedorId)
	{
		this(String.format("Não existe um cadastro de fornecedor com código %d", fornedorId));
	}
}
