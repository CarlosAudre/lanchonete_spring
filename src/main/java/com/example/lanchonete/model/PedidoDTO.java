package com.example.lanchonete.model;

import java.util.List;

public record PedidoDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens
) {}
