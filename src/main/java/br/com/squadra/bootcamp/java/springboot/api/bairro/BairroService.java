package br.com.squadra.bootcamp.java.springboot.api.bairro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BairroService {

    @Autowired
    private IBairroRepository bairroRepository;

    public List<BairroModel> listarBairros(){
        return this.bairroRepository.findAll();
    }



}
