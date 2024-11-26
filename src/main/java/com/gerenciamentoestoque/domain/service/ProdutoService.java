package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.Produto;
import java.util.List;

public interface ProdutoService
{
	List<Produto> list();
	Produto findById(Long id);
	Produto findByNome(String nome);
	Produto save(Produto produto);
	void delete(Long id);
}
