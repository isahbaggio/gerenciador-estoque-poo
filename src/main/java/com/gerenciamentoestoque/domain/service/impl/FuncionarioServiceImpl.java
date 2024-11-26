package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.Funcionario;
import com.gerenciamentoestoque.domain.repository.FuncionarioRepository;
import com.gerenciamentoestoque.domain.service.FuncionarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioServiceImpl implements FuncionarioService
{
	@Autowired
	private FuncionarioRepository repository;

	@Override
	public List<Funcionario> list()
	{
		return repository.findAll();
	}

	@Override
	public Funcionario findById(Long id)
	{
		return repository.findById(id).orElse(null);
	}

	@Override
	public Funcionario save(Funcionario funcionario)
	{
		return repository.save(funcionario);
	}

	@Override
	public void delete(Long id)
	{
		repository.deleteById(id);
	}
}
