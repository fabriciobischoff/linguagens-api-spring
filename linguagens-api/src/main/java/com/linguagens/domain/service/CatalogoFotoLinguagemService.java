package com.linguagens.domain.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.linguagens.domain.model.Linguagem;
import com.linguagens.domain.repository.LinguagemRepository;
import com.linguagens.domain.service.FotoStorageService.NovaFoto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoFotoLinguagemService {
	
	private LinguagemRepository linguagemRepository;
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public Path salvar(MultipartFile arquivo) throws IOException {
		
		String nomeNovoPhoto = fotoStorageService.gerarNomeArquivo(arquivo.getOriginalFilename());
		
		// Somente o nome da foto e o arquivo
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(nomeNovoPhoto)
				.inputStream(arquivo.getInputStream())
				.build();
		
		Path pathFoto = fotoStorageService.armazenar(novaFoto);
		
		return pathFoto;			
	}
	
	@Transactional
	public Path modificar(Long linguagemId, MultipartFile arquivo) throws IOException {
		
		String nomeNovoPhoto = fotoStorageService.gerarNomeArquivo(arquivo.getOriginalFilename());
		Path pathFotoExistente = null;
		
		Optional<Linguagem> linguagemExistente = linguagemRepository
				.findById(linguagemId);
		
		if (linguagemExistente.isPresent()) {
			if (linguagemExistente.get().getPathFoto() != null) {
				pathFotoExistente = Paths.get(linguagemExistente.get().getPathFoto());
			}	
		}

		// Somente o nome da foto e o arquivo
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(nomeNovoPhoto)
				.inputStream(arquivo.getInputStream())
				.build();
	
		Path pathFoto = fotoStorageService.substituir(pathFotoExistente, novaFoto);
		
		return pathFoto;			
	}

}
