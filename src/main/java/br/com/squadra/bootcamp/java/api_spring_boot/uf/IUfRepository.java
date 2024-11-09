package br.com.squadra.bootcamp.java.api_spring_boot.uf;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUfRepository extends JpaRepository<UfModel, Long> {
}
