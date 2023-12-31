/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Ison Ho
 * @param <R>
 * @param <E>
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public abstract class AbstractController<R extends CrudRepository<E, Integer>, E> {

    @Autowired
    private R repository;
    
    @GetMapping("/{id}")
    public @ResponseBody E getById(@PathVariable("id") int id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
    
    @PostMapping(path = "/add")
    public @ResponseBody String add(@RequestBody E e) {
        repository.save(e);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<E> getAll() {
        return repository.findAll();
    }
}