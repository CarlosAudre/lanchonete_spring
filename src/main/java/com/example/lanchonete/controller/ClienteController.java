package com.example.lanchonete.controller;

import com.example.lanchonete.model.Cliente;
import com.example.lanchonete.model.ClienteDTO;
import com.example.lanchonete.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO dto){
        Cliente clienteNovo = clienteService.cadastrarCliente(dto);
        return ResponseEntity.ok(clienteNovo);
    }

    @GetMapping
    public List<Cliente> listarClientes(){
        return clienteService.listarOsClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO dto){
        Cliente atualizado = clienteService.atualizarCliente(id, dto);
        if(atualizado != null){
            return ResponseEntity.ok(atualizado);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void removerCliente(@PathVariable Long id){
        clienteService.removerCliente(id);
    }
}
