package com.application.product;

import com.application.AbstractSummaryController;
import com.application.AbstractSummaryEntity;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 5)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 5)
    @Column(name = "`desc`", nullable = false)
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

@Data
@EqualsAndHashCode(callSuper = false)
class ProductSummary extends AbstractSummaryEntity<Product> {

    private Integer id;
    private String name, desc, image;
    private double price;
    private Integer productType, origin;

    public ProductSummary(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.desc = p.getDesc();
        this.image = p.getImage();
        this.price = p.getPrice();
        this.productType = p.getProductType().getId();
        this.origin = p.getOrigin().getId();
    }

    @Override
    public Product mapTo() {
        return new Product(id, name, desc, price, image, new ProductType(this.productType), new Origin(this.origin));
    }
}

@Controller
@RequestMapping(path = "/product")
class ProductController extends AbstractSummaryController<ProductRepository, Product, ProductSummary> {

    @Override
    protected ProductSummary buildDTO(Product entity) {
        return new ProductSummary(entity);
    }
}

interface ProductRepository extends CrudRepository<Product, Integer> {
}
