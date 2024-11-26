package com.gerenciamentoestoque.domain.repository;

import com.gerenciamentoestoque.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
}
