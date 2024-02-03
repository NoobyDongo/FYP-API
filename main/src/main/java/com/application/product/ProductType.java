package com.application.product;

import com.application.AbstractController;
import com.application.AbstractNamedEntity;
import com.application.AbstractRepostory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductType extends AbstractNamedEntity<Integer>{
    
    @JsonIgnore
    @OneToMany(mappedBy = "producttype", cascade = CascadeType.REMOVE)
    private List<Product> product;
    
    
    public ProductType(Integer id) {
        super(id);
    }
}

@Controller
@RequestMapping(path = "/producttype")
class ProductTypeController extends AbstractController<ProductTypeRepository, ProductType, Integer> {
}

interface ProductTypeRepository extends AbstractRepostory<ProductType, Integer> {
}