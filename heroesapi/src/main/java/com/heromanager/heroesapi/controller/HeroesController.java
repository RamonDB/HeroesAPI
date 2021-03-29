package com.heromanager.heroesapi.controller;

import com.heromanager.heroesapi.document.Heroes;
import com.heromanager.heroesapi.repository.HeroesRepository;
import com.heromanager.heroesapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j; // Log para o Java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.heromanager.heroesapi.constans.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
@Slf4j
public class HeroesController {
    HeroesService heroesService;
    HeroesRepository heroesRepository;

    private static final org.slf4j.Logger logHeroes =
            org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    public HeroesController (HeroesService heroesService, HeroesRepository heroesRepository){
        this.heroesRepository=heroesRepository;
        this.heroesService=heroesService;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems(){
        logHeroes.info("Requesting the list of all Heroes");
        return heroesService.findAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    public Mono <ResponseEntity<Heroes>> findByIdHero(@PathVariable String id){
        logHeroes.info("Requesting one Hero by ID {}", id);
        return heroesService.findById(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code=HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        logHeroes.info("Creating a new Hero");
        return heroesService.save(heroes);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL+"/{id}")
    @ResponseStatus(code = HttpStatus.CONTINUE)
    public Mono<HttpStatus> deleteHeroById (@PathVariable String id) {
        logHeroes.info("Deleting hero by ID {}", id);
        heroesService.deleteById(id);
        return Mono.just(HttpStatus.CONTINUE);
    }


}
