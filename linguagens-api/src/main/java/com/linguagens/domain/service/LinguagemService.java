package com.linguagens.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linguagens.domain.exceptions.LinguagemNaoEncontradaException;
import com.linguagens.domain.exceptions.NegocioException;
import com.linguagens.domain.model.Linguagem;
import com.linguagens.domain.repository.LinguagemRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class LinguagemService {
	
	private LinguagemRepository linguagemRepository;
	
	public Linguagem obter(Long linguagemId) {
		Optional<Linguagem> linguagem = linguagemRepository.findById(linguagemId);
		
		if (!linguagem.isPresent()) {
			throw new LinguagemNaoEncontradaException(linguagemId);
		}
		
		return linguagem.get();
	}
	
	public void buscarOuFalhar(Long linguagemId) {
		Optional<Linguagem> linguagem = linguagemRepository.findById(linguagemId);
		
		if (!linguagem.isPresent()) {
			throw new LinguagemNaoEncontradaException(linguagemId);
		}
	}
	
	@Transactional
	public Linguagem salvar(Linguagem linguagem) {
		Optional<Linguagem> linguagemExistente = linguagemRepository.findByTituloIgnoreCase(linguagem.getTitulo());
		
		if (linguagemExistente.isPresent()) {
			if (!linguagemExistente.get().equals(linguagem)) {
				throw new NegocioException(
						String.format("Já existe uma linguagem cadastrada com o título %s", linguagem.getTitulo()));
			}
		}
		
		return linguagemRepository.save(linguagem);
	}
	
	@Transactional
	public void excluir(Long linguagemId) {		
		linguagemRepository.deleteById(linguagemId);
	}

}
