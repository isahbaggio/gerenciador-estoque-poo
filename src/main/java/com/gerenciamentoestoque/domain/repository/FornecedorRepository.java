package com.gerenciamentoestoque.domain.repository;

import com.gerenciamentoestoque.domain.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>
{
}
