package com.linguagens.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linguagens.api.mapper.LinguagemInputMapper;
import com.linguagens.api.mapper.LinguagemModelMapper;
import com.linguagens.api.model.LinguagemModel;
import com.linguagens.api.model.input.LinguagemInput;
import com.linguagens.domain.model.Linguagem;
import com.linguagens.domain.repository.LinguagemRepository;
import com.linguagens.domain.service.CatalogoFotoLinguagemService;
import com.linguagens.domain.service.LinguagemService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RequestMapping("/linguagens")
@RestController
public class LinguagemController {
	
	private LinguagemRepository linguagemRepository;
	private LinguagemService linguagemService;
	private LinguagemModelMapper linguagemModelMapper;
	private LinguagemInputMapper linguagemInputMapper;
	
	private CatalogoFotoLinguagemService catalogoFotoLinguagemService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LinguagemModel adicionar(@RequestParam MultipartFile arquivo,
			@Valid LinguagemInput linguagemInput) throws IOException {
		
		Linguagem linguagem = linguagemInputMapper.toEntity(linguagemInput);
		
		Path path_Foto = catalogoFotoLinguagemService.salvar(arquivo);
		linguagem.setPathFoto(path_Foto.toString());
		
		linguagem = linguagemService.salvar(linguagem);
		
		return linguagemModelMapper.toModel(linguagem);
	}
	
	@PutMapping("/{linguagemId}")
	@ResponseStatus(HttpStatus.OK)
	public LinguagemModel atualizar(@RequestParam MultipartFile arquivo, @PathVariable Long linguagemId,
			@Valid LinguagemInput linguagemInput) throws IOException {
		
		linguagemService.buscarOuFalhar(linguagemId);
		
		Linguagem linguagem = linguagemInputMapper.toEntity(linguagemInput);
		linguagem.setId(linguagemId);
		
		Path path_Foto = catalogoFotoLinguagemService.modificar(linguagemId, arquivo);
		linguagem.setPathFoto(path_Foto.toString());
		
		Linguagem linguagemModifica = linguagemService.salvar(linguagem);
		
		return linguagemModelMapper.toModel(linguagemModifica);
	}
	
	@GetMapping
	public List<LinguagemModel> listar() {
		List<Linguagem> linguagens = linguagemRepository.findAll();
		
		return	linguagemModelMapper.toCollectionModel(linguagens);
	}
	
	@GetMapping("/{linguagemId}")
	public LinguagemModel buscar(@PathVariable Long linguagemId) {
		Linguagem linguagem = linguagemService.obter(linguagemId);
		
		return linguagemModelMapper.toModel(linguagem);
	}
	
	@DeleteMapping("/{linguagemId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long linguagemId) {
		linguagemService.buscarOuFalhar(linguagemId);
		
		linguagemService.excluir(linguagemId);
	}

}