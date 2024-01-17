package com.application.product;

import com.application.AbstractController;
import com.application.AbstractNamedEntity;
import com.application.AbstractRepostory;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@NoArgsConstructor
public class ProductType extends AbstractNamedEntity<Integer>{
    public ProductType(Integer id) {
        super(id);
    }
}

@Controller
@RequestMapping(path = "/producttype")
class ProductTypeController extends AbstractController<ProductTypeRepository, ProductType> {
}

interface ProductTypeRepository extends AbstractRepostory<ProductType, Integer> {
}