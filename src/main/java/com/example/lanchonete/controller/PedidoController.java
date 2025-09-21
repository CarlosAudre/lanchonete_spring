package com.example.lanchonete.controller;

import java.util.List;
import com.example.lanchonete.model.Pedido;
import com.example.lanchonete.model.PedidoDTO;
import com.example.lanchonete.model.Status;
import com.example.lanchonete.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO dto){
        Pedido pedidoNovo = pedidoService.criarPedido(dto);
        return ResponseEntity.ok(pedidoNovo);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestParam Status status){
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @PatchMapping("/{pedidoId}/itens/{itemId}/adicionar")
    public ResponseEntity<Pedido> adicionarItem(@PathVariable Long pedidoId, @PathVariable Long itemId){
        Pedido pedidoAtualizado = pedidoService.adicionarOuRemoverItem(pedidoId, itemId, true);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @PatchMapping("/{pedidoId}/itens/{itemId}/remover")
    public ResponseEntity<Pedido> removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId){
        Pedido pedidoAtualizado = pedidoService.adicionarOuRemoverItem(pedidoId, itemId, false);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @GetMapping("/status")
    public List<Pedido> filtrarPorStatus(@RequestParam Status status){
        return pedidoService.filtrarPorStatus(status);
    }

    @DeleteMapping("{id}")
    public void cancelarPedido(@PathVariable Long id){
        pedidoService.cancelarPedido(id);
    }


}
