package com.linguagens.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.linguagens.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${uploadingfiles.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public InputStream recuperar(Path arquivoPath) {
		try {
			
			return Files.newInputStream(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo.", e);
		}
	}

	@Override
	public Path armazenar(NovaFoto novaFoto) {
		Path arquivoPath = null;
		
		try {
			arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(),
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
		
		return arquivoPath;
	}
	
	@Override
	public void remover(Path arquivoPath) {
		try {
			
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}
	
}
