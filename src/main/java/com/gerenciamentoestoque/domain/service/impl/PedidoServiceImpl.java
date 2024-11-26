package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.Pedido;
import com.gerenciamentoestoque.domain.repository.PedidoRepository;
import com.gerenciamentoestoque.domain.service.PedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService
{
	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public List<Pedido> list()
	{
		return pedidoRepository.findAll();
	}

	@Override
	public Pedido findById(Long id)
	{
		return pedidoRepository.findById(id).orElse(null);
	}

	@Override
	public Pedido save(Pedido pedido)
	{
		return pedidoRepository.save(pedido);
	}

	@Override
	public void delete(Long id)
	{
		pedidoRepository.deleteById(id);
	}
}
