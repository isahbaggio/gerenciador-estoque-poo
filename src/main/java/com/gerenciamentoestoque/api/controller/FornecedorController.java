package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.FornecedorNaoEncontradoException;
import com.gerenciamentoestoque.domain.exceptions.NegocioException;
import com.gerenciamentoestoque.domain.model.Fornecedor;
import com.gerenciamentoestoque.domain.model.enums.CategoriaProduto;
import com.gerenciamentoestoque.domain.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {
    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public String listar(Model model) {
        List<Fornecedor> fornecedores = fornecedorService.list().stream()
            .sorted((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()))
            .collect(Collectors.toList());

        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("fornecedor", new Fornecedor());
        model.addAttribute("categorias", CategoriaProduto.values());
        return "fornecedores/listar";
    }

    @GetMapping("/atualizar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Fornecedor fornecedor = fornecedorService.findById(id);
        if (fornecedor == null) {
            throw new FornecedorNaoEncontradoException("Fornecedor não encontrado ao tentar editar.");
        }
        model.addAttribute("fornecedor", fornecedor);
        model.addAttribute("categorias", CategoriaProduto.values());
        return "fornecedores/atualizar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", Fornecedor.builder().build());
        model.addAttribute("categorias", CategoriaProduto.values());
        return "fornecedores/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Fornecedor fornecedor) {
        try {
            fornecedorService.save(fornecedor);
        } catch (Exception e) {
            throw new NegocioException("Erro ao salvar fornecedor.");
        }
        return "redirect:/fornecedores";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Fornecedor fornecedor) {
        try {
            Fornecedor fornecedorAtual = fornecedorService.findById(id);
            BeanUtils.copyProperties(fornecedor, fornecedorAtual, "id");
            fornecedorService.save(fornecedorAtual);
        } catch (FornecedorNaoEncontradoException e) {
            throw new FornecedorNaoEncontradoException("Fornecedor não encontrado ao tentar atualizar.");
        } catch (Exception e) {
            throw new NegocioException("Erro ao atualizar fornecedor.");
        }
        return "redirect:/fornecedores";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        try {
            if (fornecedorService.findById(id) == null) {
                throw new FornecedorNaoEncontradoException("Fornecedor não encontrado ao tentar excluir.");
            }
            fornecedorService.delete(id);
        } catch (FornecedorNaoEncontradoException e) {
            throw new FornecedorNaoEncontradoException("Fornecedor não encontrado ao tentar excluir.");
        } catch (Exception e) {
            throw new NegocioException("Erro ao excluir fornecedor.");
        }
        return "redirect:/fornecedores";
    }
}