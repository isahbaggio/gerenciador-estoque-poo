package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.Produto;
import com.gerenciamentoestoque.domain.repository.ProdutoRepository;
import com.gerenciamentoestoque.domain.service.ProdutoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService
{

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public List<Produto> list()
	{
		return produtoRepository.findAll();
	}

	@Override
	public Produto findByNome(String nome)
	{
		return produtoRepository.findByNome(nome);
	}

	@Override
	public Produto findById(Long id)
	{
		return produtoRepository.findById(id).orElse(null);
	}

	@Override
	public Produto save(Produto produto)
	{
		return produtoRepository.save(produto);
	}

	@Override
	public void delete(Long id)
	{
		produtoRepository.deleteById(id);
	}
}
