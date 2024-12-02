package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.ItemPedido;
import com.gerenciamentoestoque.domain.repository.ItemPedidoRepository;
import com.gerenciamentoestoque.domain.service.ItemPedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService
{
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Override
	public List<ItemPedido> list()
	{
		return itemPedidoRepository.findAll();
	}

	@Override
	public ItemPedido findById(Long id)
	{
		return itemPedidoRepository.findById(id).orElse(null);
	}

	@Override
	public ItemPedido save(ItemPedido itemPedido)
	{
		return itemPedidoRepository.save(itemPedido);
	}

	@Override
	public void delete(Long id)
	{
		itemPedidoRepository.deleteById(id);
	}

	@Override
	public List<ItemPedido> findByPedidoId(Long pedidoId)
	{
		return itemPedidoRepository.findByPedidoId(pedidoId);
	}
}
