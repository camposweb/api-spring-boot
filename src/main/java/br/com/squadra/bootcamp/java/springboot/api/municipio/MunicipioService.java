package br.com.squadra.bootcamp.java.springboot.api.municipio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipioService {

	@Autowired
	private IMunicipioRepository municipioRepository;

	public List<MunicipioModel> listarMunicipios() {
		return this.municipioRepository.findAll();
	}
	
}
