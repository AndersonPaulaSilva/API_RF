package com.anderson.api.rest;

import com.anderson.api.model.entity.PessoaFisica;
import com.anderson.api.model.repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Api/Cpf")
public class PessoaFisicaController {

    private final PessoaFisicaRepository repository;

    @Autowired
    public PessoaFisicaController(PessoaFisicaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PessoaFisica> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaFisica salvar(@RequestBody @Valid PessoaFisica pessoaFisica){
        return repository.save(pessoaFisica);
    }

    @GetMapping("{id}")
    public PessoaFisica buscarPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Fisica não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map(pessoaFisica -> {
                repository.delete(pessoaFisica);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Fisica não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid PessoaFisica pessoaFisicaAtualizado) {
        repository
                .findById(id)
                .map(pessoaFisica -> {
                    pessoaFisica.setNome(pessoaFisicaAtualizado.getNome());
                    pessoaFisica.setCpf(pessoaFisicaAtualizado.getCpf());
                    return repository.save(pessoaFisica);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Fisica não encontrado") );
    }
}
