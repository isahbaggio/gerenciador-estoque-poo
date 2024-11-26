package com.gerenciamentoestoque.domain.exceptions;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException
{
	private static final long serialVersionUID = 1L;
	public ItemPedidoNaoEncontradoException(String message)
	{
		super(message);
	}
	public ItemPedidoNaoEncontradoException(Long itemPedidoId) {
	  this(String.format("Não existe um cadastro de item de pedido com código %d", itemPedidoId));
	}
}
