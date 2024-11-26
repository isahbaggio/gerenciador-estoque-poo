package com.gerenciamentoestoque.domain.service;

import com.gerenciamentoestoque.domain.model.Cliente;
import java.util.List;


public interface ClienteService
{
    List<Cliente> list();
    Cliente findById(Long id);
    Cliente save(Cliente cliente);
    void update(Cliente cliente);
    void delete(Long id);
}
