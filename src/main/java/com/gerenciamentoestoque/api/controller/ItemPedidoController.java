package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.ItemPedidoNaoEncontradoException;
import com.gerenciamentoestoque.domain.exceptions.NegocioException;
import com.gerenciamentoestoque.domain.exceptions.PedidoNaoEncontradoException;
import com.gerenciamentoestoque.domain.model.ItemPedido;
import com.gerenciamentoestoque.domain.model.Pedido;
import com.gerenciamentoestoque.domain.model.Produto;
import com.gerenciamentoestoque.domain.service.ItemPedidoService;
import com.gerenciamentoestoque.domain.service.PedidoService;
import com.gerenciamentoestoque.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String listarItensPedido(Model model) {
        model.addAttribute("itensPedido", itemPedidoService.list());
        return "itensPedido/listar";
    }

    @GetMapping("/novo")
    public String novoItemPedido(Model model) {
        model.addAttribute("itemPedido", new ItemPedido());
        model.addAttribute("pedidos", pedidoService.list());
        model.addAttribute("produtos", produtoService.list());
        return "itensPedido/formulario";
    }

    @PostMapping("/salvar")
    public String salvarItemPedido(@ModelAttribute ItemPedido itemPedido,
        @RequestParam("pedidoId") Long pedidoId,
        @RequestParam("produtoId") Long produtoId,
        @RequestParam("quantidade") int quantidade) {
        Pedido pedido = pedidoService.findById(pedidoId);
        Produto produto = produtoService.findById(produtoId);

        if (pedido == null || produto == null) {
            throw new NegocioException("Pedido ou Produto não encontrado.");
        }

        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));

        itemPedidoService.save(itemPedido);
        return "redirect:/itens-pedido";
    }

    @GetMapping("/editar/{id}")
    public String editarItemPedido(@PathVariable Long id, Model model) {
        ItemPedido itemPedido = itemPedidoService.findById(id);
        if (itemPedido == null) {
            throw new ItemPedidoNaoEncontradoException("ItemPedido não encontrado.");
        }

        model.addAttribute("itemPedido", itemPedido);
        model.addAttribute("pedidos", pedidoService.list());
        model.addAttribute("produtos", produtoService.list());
        return "itensPedido/formulario";
    }

    @PostMapping("/remover/{id}")
    public String removerItemPedido(@PathVariable Long id) {
        itemPedidoService.delete(id);
        return "redirect:/itens-pedido";
    }
}
