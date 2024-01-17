package com.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @param <R>
 * @param <E>
 */
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public abstract class AbstractController<R extends AbstractRepostory<E, Integer>, E> {

    @Autowired
    private R repository;

    @GetMapping(path = "/lastupdated")
    public @ResponseBody Map<String, Map<String, LocalDateTime>> getLastUpdatedTime() {
        LocalDateTime lastUpdatedTime = repository.getLastUpdatedTime();
        Map<String, LocalDateTime> timeMap = new HashMap<>();
        timeMap.put("lastupdatedTime", lastUpdatedTime);
        Map<String, Map<String, LocalDateTime>> response = new HashMap<>();
        response.put("time", timeMap);
        return response;
    }

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
    Id delete(@PathVariable("id") int id) {
        repository.deleteById(id);
        return new Id(id);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<E> getAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/range")
    public @ResponseBody
    Page<E> getRange(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest);
    }
}
