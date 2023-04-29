package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Sportsman1;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/sportsmans")
public class testController {


    private List<Sportsman1> SPORTSMEN = Stream.of(
            new Sportsman1(1L, "Sasha", "Lyapanov"),
            new Sportsman1(2L, "Tigran", "Melkonyan"),
            new Sportsman1(3L, "Alina", "Belova")
    ).collect(Collectors.toList());


    @GetMapping()
    public List<Sportsman1> getAll() {
        return SPORTSMEN;
    }

    @GetMapping("/{id}")
    public Sportsman1 getById(@PathVariable Long id) {
        return SPORTSMEN.stream().filter(sportsman -> sportsman.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/create")
    public Sportsman1 create(Sportsman1 sportsman) {
        this.SPORTSMEN.add(sportsman);
        return sportsman;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        this.SPORTSMEN.removeIf(sportsman -> sportsman.getId().equals(id));
    }


}
