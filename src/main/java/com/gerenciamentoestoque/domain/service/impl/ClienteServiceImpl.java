package com.gerenciamentoestoque.domain.service.impl;

import com.gerenciamentoestoque.domain.model.Cliente;
import com.gerenciamentoestoque.domain.repository.ClienteRepository;
import com.gerenciamentoestoque.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService
{
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> list()
    {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id)
    {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente)
    {
        return clienteRepository.save(cliente);
    }

    @Override
    public void update(Cliente cliente)
    {
        if (clienteRepository.existsById(cliente.getId())) {
            clienteRepository.save(cliente);
        }
    }

    @Override
    public void delete(Long id)
    {
        clienteRepository.deleteById(id);
    }
}