package com.linguagens.api.model.input;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.linguagens.core.validation.FileContentType;
import com.linguagens.core.validation.FileSize;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinguagemInput {

	@NotBlank
	@Size(min = 1, max = 60)
	private String titulo;
	
	@NotNull
	@Digits(integer = 2, fraction = 0, message = "Deve conter um número inteiro de 0 a 5")
	private Integer ranking;
	
	@NotNull
	@FileSize(max = "1MB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;
	
}
