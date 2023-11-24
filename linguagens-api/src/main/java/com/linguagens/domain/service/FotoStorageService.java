package com.linguagens.domain.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	InputStream recuperar(Path arquivoPath);

	Path armazenar(NovaFoto novaFoto);

	void remover(Path arquivoPath);
	
	default Path substituir(Path pathFotoExistente, NovaFoto novaFoto) {
		Path arquivoPath = this.armazenar(novaFoto);
		
		if (pathFotoExistente != null) {
			this.remover(pathFotoExistente);
		}
		
		return arquivoPath;
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
	}

}
