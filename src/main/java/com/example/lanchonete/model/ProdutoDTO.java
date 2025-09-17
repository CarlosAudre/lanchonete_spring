package com.example.lanchonete.model;

public record ProdutoDTO(
        String nome,
        String descricao,
        double preco,
        Categoria categoria
) {}
