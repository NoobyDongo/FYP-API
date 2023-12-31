package com.application.product;

import com.application.AbstractController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull
    @Size(min=5)
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Size(min=5)
    @Column(name="`desc`", nullable = false)
    private String desc;
    
    @NotNull
    @Min(value = (long) 0.1)
    @Column(nullable = false)
    private double price;
    
    private String image;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private ProductType productType;
    
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Origin origin;
}

@Controller
@RequestMapping(path = "/product")
class ProductController extends AbstractController<ProductRepository, Product>{}

interface ProductRepository extends CrudRepository<Product, Integer> {}

