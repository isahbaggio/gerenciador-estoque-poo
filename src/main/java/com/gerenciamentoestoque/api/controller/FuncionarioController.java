package com.gerenciamentoestoque.api.controller;

import com.gerenciamentoestoque.domain.exceptions.FuncionarioNaoEncontradoException;
import com.gerenciamentoestoque.domain.exceptions.NegocioException;
import com.gerenciamentoestoque.domain.model.Funcionario;
import com.gerenciamentoestoque.domain.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public String listar(Model model) {
        List<Funcionario> funcionarios = funcionarioService.list();
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", Funcionario.builder().build());
        return "funcionarios/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Funcionario funcionario) {
        funcionarioService.save(funcionario);
        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Funcionario funcionario) {
        try {
            Funcionario funcionarioAtual = funcionarioService.findById(id);
            BeanUtils.copyProperties(funcionario, funcionarioAtual, "id");
            funcionarioService.save(funcionarioAtual);
        } catch (FuncionarioNaoEncontradoException e) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado");
        } catch (Exception e) {
           throw new NegocioException("Erro ao atualizar funcionário");
        }
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario == null) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado");
        }
        model.addAttribute("funcionario", funcionario);
        return "funcionarios/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        if (funcionarioService.findById(id) == null) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado");
        }
        funcionarioService.delete(id);
        return "redirect:/funcionarios";
    }
}