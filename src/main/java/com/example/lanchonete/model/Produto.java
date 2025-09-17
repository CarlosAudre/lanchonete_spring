package com.example.lanchonete.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "produto")


public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    private String descricao;
    private double preco;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
}
