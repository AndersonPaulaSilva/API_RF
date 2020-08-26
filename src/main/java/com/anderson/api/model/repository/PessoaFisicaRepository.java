package com.anderson.api.model.repository;

import com.anderson.api.model.entity.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Integer> {
}
