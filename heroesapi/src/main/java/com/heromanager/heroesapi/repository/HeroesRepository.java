package com.heromanager.heroesapi.repository;

import com.heromanager.heroesapi.document.Heroes;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan; // Habilitar a leitura dos dados no repository
import org.springframework.data.repository.CrudRepository; // Habilitar requisições básicas de uma API no nosso repositório

@EnableScan
public interface HeroesRepository extends CrudRepository<Heroes, String> {


}
