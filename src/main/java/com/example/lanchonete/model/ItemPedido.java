package com.example.lanchonete.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_pedido")

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private double precoTotal;
    @ManyToOne
    @JoinColumn(name = "pedido_id") // cria a FK no banco
    private Pedido pedido;
}
