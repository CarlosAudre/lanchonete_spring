package com.example.lanchonete.repository;


import com.example.lanchonete.model.Categoria;
import com.example.lanchonete.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findById(Long id);

    void deleteById(Long id);

    List<Produto> findByCategoria(Categoria categoria);





}
