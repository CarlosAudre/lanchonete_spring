package com.example.lanchonete.service;

import com.example.lanchonete.model.Cliente;
import com.example.lanchonete.model.ClienteDTO;
import com.example.lanchonete.model.Status;
import com.example.lanchonete.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(ClienteDTO dto) {
        Cliente cliente = Cliente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .build();
        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
        if(clienteExistente.isPresent()){
            throw new RuntimeException("CPF já cadastrado!");
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarOsClientes() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(Long id, ClienteDTO dto) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(dto.nome());
                    cliente.setTelefone(dto.telefone());
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void removerCliente(Long id) {
        Optional<Cliente> clienteX = clienteRepository.findById(id);
        if (clienteX.isPresent()){
            Cliente cliente = clienteX.get();
            boolean todosOsPedidosFinalizados = cliente.getPedidos().stream()
                    .allMatch(pedido -> pedido.getStatus() == Status.FINALIZADO);
            if (todosOsPedidosFinalizados){
                clienteRepository.deleteById(id);
            }else{
                throw new IllegalStateException("Não é possível remover o cliente, pois ainda existem pedidos pendentes");
            }
        }
        else{
            throw new EntityNotFoundException("Cliente não encontrado");
        }
    }
}
