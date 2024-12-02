package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.ClienteNaoEncontradoException;
import com.gerenciamentoestoque.domain.exceptions.NegocioException;
import com.gerenciamentoestoque.domain.model.Cliente;
import com.gerenciamentoestoque.domain.service.ClienteService;
import com.gerenciamentoestoque.domain.util.TelefoneUtils;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        List<Cliente> clientes = clienteService.list().stream()
            .map(cliente -> {
                cliente.setTelefone(TelefoneUtils.formatterTelefone(cliente.getTelefone()));
                return cliente;
            })
            .collect(Collectors.toList());

        if (clientes == null) {
            clientes = new ArrayList<>();
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente());
        return "clientes/listar";
    }

    @GetMapping("/atualizar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado ao tentar editar.");
        }
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", Cliente.builder().build());
        return "clientes/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Cliente cliente) {
        try {
            clienteService.save(cliente);
        } catch (Exception e) {
            throw new NegocioException("Erro ao salvar cliente.");
        }
        return "redirect:/clientes";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        try {
            Cliente clienteAtual = clienteService.findById(id);
            BeanUtils.copyProperties(cliente, clienteAtual, "id");
            clienteService.save(clienteAtual);
        } catch (ClienteNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado ao tentar atualizar.");
        } catch (Exception e) {
            throw new NegocioException("Erro ao atualizar cliente.");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        try {
            if (clienteService.findById(id) == null) {
                throw new ClienteNaoEncontradoException("Cliente não encontrado ao tentar excluir.");
            }
            clienteService.delete(id);
        } catch (ClienteNaoEncontradoException e) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado ao tentar excluir.");
        } catch (Exception e) {
            throw new NegocioException("Erro ao excluir cliente.");
        }
        return "redirect:/clientes";
    }
}