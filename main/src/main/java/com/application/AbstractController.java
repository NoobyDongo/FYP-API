package com.application;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @param <R>
 * @param <E>
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public abstract class AbstractController<R extends JpaRepository<E, Integer>, E> {

    @Autowired
    private R repository;

    @GetMapping("/{id}")
    public @ResponseBody
    E getById(@PathVariable("id") int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    E add(@Valid @RequestBody E e) {
        e = repository.save(e);
        repository.flush();
        return e;
    }
    
    @DeleteMapping(path = "/remove/{id}")
    public @ResponseBody
    String delete(@PathVariable("id") int id) {
        repository.deleteById(id);
        return "deleted";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<E> getAll() {
        return repository.findAll();
    }
}
