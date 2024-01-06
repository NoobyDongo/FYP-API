package com.application.product;

import com.application.AbstractSummaryController;
import com.application.AbstractSummaryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(name = "`desc`", nullable = false)
    private String desc;

    @NotNull
    @Min(value = (long) 0.1)
    @Column(nullable = false)
    private double price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;
    
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
@NoArgsConstructor
class ProductSummary extends AbstractSummaryEntity<Product> {

    private Integer id;
    private String name, desc;
    private List<ProductImage> images;
    private double price;
    private Integer productType, origin;

    public ProductSummary(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.desc = p.getDesc();
        this.images = p.getImages();
        this.price = p.getPrice();
        this.productType = p.getProductType().getId();
        this.origin = p.getOrigin().getId();
    }

    @Override
    public Product mapTo() {
        return new Product(id, name, desc, price, images, new ProductType(this.productType), new Origin(this.origin));
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

interface ProductRepository extends JpaRepository<Product, Integer> {
}
