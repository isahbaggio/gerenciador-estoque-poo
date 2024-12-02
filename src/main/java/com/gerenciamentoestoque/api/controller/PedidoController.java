package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.NegocioException;
import com.gerenciamentoestoque.domain.exceptions.PedidoNaoEncontradoException;
import com.gerenciamentoestoque.domain.model.ItemPedido;
import com.gerenciamentoestoque.domain.model.Pedido;
import com.gerenciamentoestoque.domain.service.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.list());
        return "pedidos/listar";
    }

    @GetMapping("/novo")
    public String novoPedido(Model model) {
        Pedido pedido = new Pedido();
        carregarDadosFormulario(model, pedido);
        return "pedidos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarPedido(@ModelAttribute Pedido pedido, HttpSession session){
        Pedido pedidoExistente = null;
        if (Objects.nonNull(pedido.getId())) {
            pedidoExistente = pedidoService.findById(pedido.getId());
        }

        if (Objects.nonNull(pedidoExistente)) {
            BeanUtils.copyProperties(pedido, pedidoExistente, "id");
            pedidoService.save(pedidoExistente);
        } else {
            pedidoService.save(pedido);
        }

        session.removeAttribute("pedido");
        session.removeAttribute("itensPedido");

        return "redirect:/pedidos";
    }


    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado para edição.");
        }
        carregarDadosFormulario(model, pedido);
        return "pedidos/formulario";
    }

    @PostMapping("/removerItem/{pedidoId}/{itemId}")
    public String removerItemPedido(@PathVariable Long pedidoId, @PathVariable Long itemId, HttpSession session, Model model) {
        Pedido pedido = pedidoService.findById(pedidoId);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado.");
        }

        List<ItemPedido> itensPedido = pedido.getItensPedido();
        boolean removed = itensPedido.removeIf(item -> item.getId().equals(itemId));

        if (!removed) {
            throw new NegocioException("Item não encontrado no pedido.");
        }

        Pedido pedidoAtualizado = new Pedido();
        BeanUtils.copyProperties(pedido, pedidoAtualizado, "itensPedido");
        pedidoAtualizado.setId(pedido.getId());
        pedidoAtualizado.setItensPedido(itensPedido);
        pedidoService.save(pedidoAtualizado);
        session.setAttribute("pedido", pedidoAtualizado);

        carregarDadosFormulario(model, pedidoAtualizado, itensPedido);

        return "pedidos/formulario";
    }


    @PostMapping("/remover/{id}")
    public String removerPedido(@PathVariable Long id) {
        pedidoService.delete(id);
        return "redirect:/pedidos";
    }

    private void carregarDadosFormulario(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteService.list());
        model.addAttribute("itensPedido", itemPedidoService.findByPedidoId(pedido.getId()));
        model.addAttribute("funcionarios", funcionarioService.list());
    }

    private void carregarDadosFormulario(Model model, Pedido pedido, List<ItemPedido> itensPedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteService.list());
        model.addAttribute("itensPedido", itensPedido);
        model.addAttribute("funcionarios", funcionarioService.list());
    }
}
