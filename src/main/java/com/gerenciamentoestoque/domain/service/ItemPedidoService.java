package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.ItemPedido;
import java.util.List;

public interface ItemPedidoService
{
	List<ItemPedido> list();
	ItemPedido findById(Long id);
	ItemPedido save(ItemPedido itemPedido);
	void delete(Long id);
	List<ItemPedido> findByPedidoId(Long pedidoId);
}
