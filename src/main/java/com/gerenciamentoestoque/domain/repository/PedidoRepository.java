package com.gerenciamentoestoque.domain.repository;

import com.gerenciamentoestoque.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>
{
}
