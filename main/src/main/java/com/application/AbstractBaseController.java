package com.application;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public abstract class AbstractBaseController<R extends AbstractRepostory<E, ID>, E, ID>{

    
    //protected E buildDto(E entity);
    
    @Autowired
    protected R repository;

    @GetMapping(path = "/lastupdated")
    public @ResponseBody Map<String, Map<String, LocalDateTime>> getLastUpdatedTime() {
        LocalDateTime lastUpdatedTime = repository.getLastUpdatedTime();
        Map<String, LocalDateTime> timeMap = new HashMap<>();
        timeMap.put("lastupdatedTime", lastUpdatedTime);
        Map<String, Map<String, LocalDateTime>> response = new HashMap<>();
        response.put("time", timeMap);
        return response;
    }
    
    @DeleteMapping(path = "/remove/{id}")
    public @ResponseBody
    Id deleteWithPath(@PathVariable("id") ID id) {
        var customDelete = _delete(id);
        if (customDelete == null) {
            repository.deleteById(id);
            return new Id(id);
        }
        return new Id(customDelete);
    }
    
    @DeleteMapping(path = "/remove")
    public @ResponseBody
    Id deleteWithBody(@RequestBody ID id) {
        var customDelete = _delete(id);
        if (customDelete == null) {
            repository.deleteById(id);
            return new Id(id);
        }
        return new Id(customDelete);
    }

    protected ID _delete(ID id) {
        return null;
    }
    
    @GetMapping(path = "/count")
    public @ResponseBody long count() {
        return repository.count();
    }
    
    @PostMapping("/search")
    public @ResponseBody Page<E> search(@RequestBody SearchDto body) {

        SearchSpecificationBuilder builder = new SearchSpecificationBuilder();
        if (body.getCriteriaList() != null) {
            System.out.println("body.getCriteriaList()" + body.getCriteriaList().size());
            body.getCriteriaList().forEach(x -> {
                builder.with(x);
            });
        }
        List<Sort.Order> orders = new ArrayList<>();
        if (body.getSort() != null) {
            for (SortDto sortDto : body.getSort()) {
                orders.add(new Sort.Order(Sort.Direction.fromString(sortDto.getDirection()), sortDto.getProperty()));
            }
        }

        PageRequest pageReq = PageRequest.of(body.getPage(), body.getSize(), Sort.by(orders));
        return repository.findAll(builder.build(), pageReq);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<E> getAll() {
        return repository.findAll();
    }
}
