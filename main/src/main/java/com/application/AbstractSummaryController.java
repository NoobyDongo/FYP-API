package com.application;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @param <R>
 * @param <E>
 * @param <DTO>
 */
public abstract class AbstractSummaryController<R extends JpaRepository<E, Integer>, E, DTO extends AbstractSummaryEntity<E>> extends AbstractController<R, E> {

    protected abstract DTO buildDTO(E entity);

    @GetMapping("/{id}/simple")
    public @ResponseBody
    DTO getByIdSimple(@PathVariable("id") int id) {
        return buildDTO(getById(id));
    }

    @PostMapping(path = "/add/simple")
    public @ResponseBody
    DTO addSimple(@RequestBody DTO e) {
        return buildDTO(add(e.mapTo()));
    }

    @GetMapping(path = "/all/simple")
    public @ResponseBody
    Iterable<DTO> getAllSimple() {
        //https://stackoverflow.com/a/69705144, thx to magicmn
        return StreamSupport.stream(getAll().spliterator(), false).map(this::buildDTO).collect(Collectors.toList());
    }
}
