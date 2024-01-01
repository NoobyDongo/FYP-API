package com.application.product;

import com.application.AbstractController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@NoArgsConstructor
public class ProductType{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public ProductType(Integer id) {
        this.id = id;
    }
}

@Controller
@RequestMapping(path = "/producttype")
class ProductTypeController extends AbstractController<ProductTypeRepository, ProductType>{}

interface ProductTypeRepository extends CrudRepository<ProductType, Integer>{}