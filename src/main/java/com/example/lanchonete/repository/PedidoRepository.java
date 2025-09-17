package com.example.lanchonete.repository;

import com.example.lanchonete.model.Pedido;
import com.example.lanchonete.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(Status status);

}