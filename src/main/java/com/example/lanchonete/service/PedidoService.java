package com.example.lanchonete.service;

import com.example.lanchonete.model.*;
import com.example.lanchonete.repository.ClienteRepository;
import com.example.lanchonete.repository.ItemPedidoRepository;
import com.example.lanchonete.repository.PedidoRepository;
import com.example.lanchonete.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Transactional
    public Pedido criarPedido(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .status(Status.PENDENTE)
                .hora(LocalDateTime.now())
                .valorTotal(0.0)
                .build();

        double valorTotal = 0.0;

        for(ItemPedidoDTO itemDTO : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            double precoUnitario = produto.getPreco();
            double precoTotal = precoUnitario * itemDTO.quantidade();

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemDTO.quantidade())
                    .precoUnitario(precoUnitario)
                    .precoTotal(precoTotal)
                    .build();

            pedido.getItens().add(item);

            valorTotal += precoTotal;
        }

        pedido.setValorTotal(valorTotal);
        return pedidoRepository.save(pedido);
    }


    public Pedido atualizarStatus(Long id, Status status) {
        Optional<Pedido> pedidox = pedidoRepository.findById(id);
        if (pedidox.isEmpty()){
            throw new RuntimeException("Pedido não encontrado");
        }
        Pedido pedido = pedidox.get();
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);

    }

    @Transactional
    public Pedido adicionarOuRemoverItem(Long pedidoId, Long itemId, boolean adicionar) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if(adicionar){
            item.setQuantidade(item.getQuantidade() +1);
        }
        else{
            int novaQuantidade = item.getQuantidade() - 1;
            if(novaQuantidade <= 0){
                pedido.getItens().remove(item);
                itemPedidoRepository.deleteById(itemId);
            }
            else{
                item.setQuantidade(novaQuantidade);
            }
        }

        item.setPrecoTotal(item.getPrecoUnitario() * item.getQuantidade());

        double novoTotal = pedido.getItens().stream()
                .mapToDouble(ItemPedido::getPrecoTotal).sum();

        pedido.setValorTotal(novoTotal);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> filtrarPorStatus(Status status) {
        return pedidoRepository.findByStatus(status);
    }

    /*  public List<Pedido> filtrarPorStatus(Status status) {
      List<Pedido> pedidos = pedidoRepository.findAll();
      List<Pedido> pedidosFiltradosPorStatus  =  pedidos.stream()
                .filter(pedido -> pedido.getStatus() == status)
                .collect(Collectors.toList());
       return pedidosFiltradosPorStatus;
    }
 */

    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado"));

        if(pedido.getStatus() == Status.PENDENTE){
            pedidoRepository.delete(pedido);
        }
        else{
            throw new RuntimeException("Esse pedido não pode ser cancelado");
        }
    }
}
