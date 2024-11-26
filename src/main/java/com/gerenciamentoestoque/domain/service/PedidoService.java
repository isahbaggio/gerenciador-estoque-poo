package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.Pedido;
import java.util.List;

public interface PedidoService
{
	List<Pedido> list();
	Pedido findById(Long id);
	Pedido save(Pedido pedido);
	void delete(Long id);
}
