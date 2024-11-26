package com.gerenciamentoestoque.domain.repository;

import com.gerenciamentoestoque.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>
{
}
