package com.gerenciamentoestoque.domain.repository;

import com.gerenciamentoestoque.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>
{
}
