package com.linguagens.api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.linguagens.api.model.LinguagemModel;
import com.linguagens.domain.model.Linguagem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class LinguagemModelMapper {

	private ModelMapper modelMapper;
	
	public LinguagemModel toModel(Linguagem linguagem) {
		return modelMapper.map(linguagem, LinguagemModel.class);
	}
	
	public List<LinguagemModel> toCollectionModel(List<Linguagem> linguagens) {
		List<LinguagemModel> linguagensModel = new ArrayList<>();
		
		for (Linguagem linguagem : linguagens) {
			linguagensModel.add(this.toModel(linguagem));
		}
		
		return linguagensModel;
	}
}
