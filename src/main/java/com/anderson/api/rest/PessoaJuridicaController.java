package com.anderson.api.rest;

import com.anderson.api.model.entity.PessoaJuridica;
import com.anderson.api.model.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Api/Cnpj")
public class PessoaJuridicaController {

    private final PessoaJuridicaRepository repository;

    @Autowired
    public PessoaJuridicaController(PessoaJuridicaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PessoaJuridica> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaJuridica salvar(@RequestBody @Valid PessoaJuridica pessoaJuridica){
        return repository.save(pessoaJuridica);
    }

    @GetMapping("{id}")
    public PessoaJuridica acharPorId(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Juridica não encontrada") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map(pessoaJuridica -> {
                repository.delete(pessoaJuridica);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Juridica não encontrada") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid PessoaJuridica pessoaJuridicaAtualizado) {
        repository
                .findById(id)
                .map(pessoaJuridica -> {
                    pessoaJuridica.setNomeEmpresa(pessoaJuridicaAtualizado.getNomeEmpresa());
                    pessoaJuridica.setCnpj(pessoaJuridicaAtualizado.getCnpj());
                    return repository.save(pessoaJuridica);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa Juridica não encontrada") );
    }
}
