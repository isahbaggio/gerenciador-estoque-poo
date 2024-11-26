import com.gerenciamentoestoque.domain.exceptions.ItemPedidoNaoEncontradoException;
import com.gerenciamentoestoque.domain.model.ItemPedido;
import com.gerenciamentoestoque.domain.model.Pedido;
import com.gerenciamentoestoque.domain.model.Produto;
import com.gerenciamentoestoque.domain.service.ClienteService;
import com.gerenciamentoestoque.domain.service.FuncionarioService;
import com.gerenciamentoestoque.domain.service.PedidoService;
import com.gerenciamentoestoque.domain.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.list();
        model.addAttribute("pedidos", pedidos);
        return "pedidos/listar";
    }

    @GetMapping("/novo")
    public String novoPedido(Model model, HttpSession session) {
        Pedido pedido = new Pedido();
        List<ItemPedido> itensPedido = new ArrayList<>();

        session.setAttribute("pedido", pedido);
        session.setAttribute("itensPedido", itensPedido);

        carregarDadosFormulario(model, pedido, itensPedido);

        return "pedidos/formulario";
    }

    @PostMapping("/adicionarItem")
    public String adicionarItemAoPedido(@ModelAttribute("itemPedido") ItemPedido itemPedido,
        HttpSession session, Model model)
    {
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        List<ItemPedido> itensPedido = (List<ItemPedido>) session.getAttribute("itensPedido");

        itemPedido.setProduto(produtoService.findById(itemPedido.getProduto().getId()));
        itemPedido.setPedido(pedido);
        itensPedido.add(itemPedido);

        carregarDadosFormulario(model, pedido, itensPedido);

        return "pedidos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarPedido(@ModelAttribute Pedido pedido,
        @RequestParam("produtoId") Long produtoId,
        @RequestParam("quantidade") int quantidade)
    {
        Produto produto = produtoService.findById(produtoId);
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(produto.getPreco().multiply(new BigDecimal(quantidade)));
        itemPedido.setPedido(pedido);

        List<ItemPedido> itensPedido = new ArrayList<>();
        itensPedido.add(itemPedido);
        pedido.setItensPedido(itensPedido);

        pedidoService.save(pedido);

        return "redirect:/pedidos";
    }

    @PostMapping("/atualizar")
    public String atualizarPedido(@ModelAttribute Pedido pedido,
        @RequestParam("produtoId") Long produtoId,
        @RequestParam("quantidade") int quantidade)
    {
        Produto produto = produtoService.findById(produtoId);
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(produto.getPreco().multiply(new BigDecimal(quantidade)));
        itemPedido.setPedido(pedido);

        Pedido pedidoExistente = pedidoService.findById(pedido.getId());
        if (pedidoExistente != null) {

            BeanUtils.copyProperties(pedidoExistente, pedido, "id");

            List<ItemPedido> itensPedido = pedidoExistente.getItensPedido();
            itensPedido.add(itemPedido);
            pedidoExistente.setItensPedido(itensPedido);
            pedidoService.save(pedidoExistente);
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Long id, Model model, HttpSession session) {
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            throw new ItemPedidoNaoEncontradoException("Pedido não encontrado ao tentar editar.");
        }
        List<ItemPedido> itensPedido = pedido.getItensPedido();

        session.setAttribute("pedido", pedido);
        session.setAttribute("itensPedido", itensPedido);

        carregarDadosFormulario(model, pedido, itensPedido);

        return "pedidos/atualizar";
    }

    private void carregarDadosFormulario(Model model, Pedido pedido, List<ItemPedido> itensPedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteService.list());
        model.addAttribute("funcionarios", funcionarioService.list());
        model.addAttribute("produtos", produtoService.list());
        model.addAttribute("itensPedido", itensPedido);
        model.addAttribute("itemPedido", new ItemPedido());
    }
}