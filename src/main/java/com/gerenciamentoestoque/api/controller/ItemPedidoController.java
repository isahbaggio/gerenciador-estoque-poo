package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.ItemPedidoNaoEncontradoException;
import com.gerenciamentoestoque.domain.model.ItemPedido;
import com.gerenciamentoestoque.domain.service.ItemPedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/itempedidos")
public class ItemPedidoController {
    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public String listar(Model model) {
        List<ItemPedido> itemPedidos = itemPedidoService.list();
        model.addAttribute("itemPedidos", itemPedidos);
        return "itempedidos/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("itemPedido", ItemPedido.builder().build());
        return "itempedidos/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute ItemPedido itemPedido) {
        itemPedidoService.save(itemPedido);
        return "redirect:/itempedidos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ItemPedido itemPedido = itemPedidoService.findById(id);
        if (itemPedido == null) {
            throw new ItemPedidoNaoEncontradoException("ItemPedido não encontrado");
        }
        model.addAttribute("itemPedido", itemPedido);
        return "itempedidos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        if (itemPedidoService.findById(id) == null) {
            throw new ItemPedidoNaoEncontradoException("ItemPedido não encontrado");
        }
        itemPedidoService.delete(id);
        return "redirect:/itempedidos";
    }
}