package com.linguagens.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linguagens.domain.model.Linguagem;


public interface LinguagemRepository extends JpaRepository<Linguagem, Long> {

	Optional<Linguagem> findByTituloIgnoreCase(String titulo);
	
}
