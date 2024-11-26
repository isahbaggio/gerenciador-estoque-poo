package com.gerenciamentoestoque.domain.model;

import com.gerenciamentoestoque.domain.model.enums.CategoriaProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produto
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private BigDecimal preco;

	private LocalDate dataFabricacao;

	private LocalDate dataValidade;

	@Enumerated(EnumType.STRING)
	private CategoriaProduto categoria;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;
}
