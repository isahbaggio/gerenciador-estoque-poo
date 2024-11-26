package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.Funcionario;
import java.util.List;

public interface FuncionarioService
{
	List<Funcionario> list();
	Funcionario findById(Long id);
	Funcionario save(Funcionario funcionario);
	void delete(Long id);
}
