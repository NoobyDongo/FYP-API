package com.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public abstract class AbstractController<R extends AbstractRepostory<E, ID>, E, ID> extends AbstractBaseController<R, E, ID> {

    @GetMapping("/{id}")
    public @ResponseBody
    E getById(@PathVariable("id") ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    E add(@Valid @RequestBody E e) {
        e = repository.save(e);
        repository.flush();
        return e;
    }
}
