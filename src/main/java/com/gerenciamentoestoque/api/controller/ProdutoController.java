package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.ProdutoNaoEncontradoException;
import com.gerenciamentoestoque.domain.model.Produto;
import com.gerenciamentoestoque.domain.model.enums.CategoriaProduto;
import com.gerenciamentoestoque.domain.service.FornecedorService;
import com.gerenciamentoestoque.domain.service.ProdutoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public String listar(Model model) {
        List<Produto> produtos = produtoService.list();
        model.addAttribute("produtos", produtos);
        return "produtos/listar";
    }

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("produto", new Produto());
		model.addAttribute("categorias", CategoriaProduto.values());
		model.addAttribute("fornecedores", fornecedorService.list().stream()
			.sorted((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()))
			.collect(Collectors.toList()));
		return "produtos/formulario";
	}

	@GetMapping("/atualizar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Produto produto = produtoService.findById(id);
		if (produto == null) {
			throw new ProdutoNaoEncontradoException("Produto não encontrado ao tentar editar.");
		}
		model.addAttribute("produto", produto);
		model.addAttribute("categorias", CategoriaProduto.values());
		model.addAttribute("fornecedores", fornecedorService.list().stream()
			.sorted((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()))
			.collect(Collectors.toList()));
		return "produtos/atualizar";
	}

    @PostMapping
    public String salvar(@ModelAttribute Produto produto) {
        produtoService.save(produto);
        return "redirect:/produtos";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Produto produto) {
        Produto produtoAtual = produtoService.findById(id);
        if (produtoAtual == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado ao tentar atualizar.");
        }
        BeanUtils.copyProperties(produto, produtoAtual, "id");
        produtoService.save(produtoAtual);
        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado ao tentar excluir.");
        }
        produtoService.delete(id);
        return "redirect:/produtos";
    }
}