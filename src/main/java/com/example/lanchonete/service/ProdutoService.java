package com.example.lanchonete.service;

import com.example.lanchonete.model.Categoria;
import com.example.lanchonete.model.Produto;
import com.example.lanchonete.model.ProdutoDTO;
import com.example.lanchonete.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public Produto registrarUmProduto(ProdutoDTO dto) {
        Produto produto = Produto.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .categoria(dto.categoria())
                .build();
        return produtoRepository.save(produto);
    }


    public List<Produto> listarOsProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarPorCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public Produto atualizarProduto(Long id, ProdutoDTO dto) {
        return produtoRepository.findById(id) //Retorna um produto
                .map(produto -> {
                    produto.setNome(dto.nome());
                    produto.setPreco(dto.preco());
                    produto.setCategoria(dto.categoria());
                    produto.setDescricao(dto.descricao());
                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        }
    }
}
