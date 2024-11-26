package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.Fornecedor;
import com.gerenciamentoestoque.domain.repository.FornecedorRepository;
import com.gerenciamentoestoque.domain.service.FornecedorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FornecedorClienteImpl implements FornecedorService
{
	@Autowired
	private FornecedorRepository repository;

	@Override
	public List<Fornecedor> list()
	{
		return repository.findAll();
	}

	@Override
	public Fornecedor findById(Long id)
	{
		return repository.findById(id).orElse(null);
	}

	@Override
	public Fornecedor save(Fornecedor fornecedor)
	{
		return repository.save(fornecedor);
	}

	@Override
	public void delete(Long id)
	{
		repository.deleteById(id);
	}
}
