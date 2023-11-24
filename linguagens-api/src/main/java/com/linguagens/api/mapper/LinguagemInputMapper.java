package com.linguagens.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.linguagens.api.model.input.LinguagemInput;
import com.linguagens.domain.model.Linguagem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class LinguagemInputMapper {
	
	private ModelMapper modelMapper;
	
	public Linguagem toEntity(LinguagemInput linguagemInput) {
		return modelMapper.map(linguagemInput, Linguagem.class);
	}

}
