package com.example.lanchonete.controller;


import com.example.lanchonete.model.Categoria;
import com.example.lanchonete.model.Produto;
import com.example.lanchonete.model.ProdutoDTO;
import com.example.lanchonete.service.ProdutoService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> registrarProduto(@RequestBody ProdutoDTO dto){
        Produto salvo = produtoService.registrarUmProduto(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public List<Produto> listarProdutos(){
        return produtoService.listarOsProdutos();
    }


    @GetMapping("/categoria/{categoria}")
    public List<Produto> listarPorCategoria(@PathVariable Categoria categoria){ //Pega o valor a url
        return produtoService.listarPorCategoria(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto){
        Produto atualizado = produtoService.atualizarProduto(id, dto);
        if(atualizado != null){
            return ResponseEntity.ok(atualizado);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
    }
}
