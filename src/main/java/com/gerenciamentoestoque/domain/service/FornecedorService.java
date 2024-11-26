package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.Fornecedor;
import java.util.List;
import org.springframework.stereotype.Service;


public interface FornecedorService
{
	List<Fornecedor> list();
	Fornecedor findById(Long id);
	Fornecedor save(Fornecedor fornecedor);
	void delete(Long id);
}
