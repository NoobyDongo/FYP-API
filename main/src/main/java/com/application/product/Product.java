package com.application.product;

import com.application.AbstractNamedEntity;
import com.application.AbstractSummaryController;
import com.application.AbstractSummaryEntity;
import com.application.business.InvoiceProduct;
import com.application.company.InventoryProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AbstractNamedEntity<Integer> {

    @NotNull
    @Column(name = "`desc`", nullable = false, columnDefinition = "LONGTEXT")
    private String desc;

    @NotNull
    @Min(value = (long) 0.1)
    @Column(nullable = false)
    private double price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<InventoryProduct> inventoryProduct;
    
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<InvoiceProduct> invoiceProduct;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "producttype_id")
    private ProductType producttype;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Origin origin;

    public Product(Integer id, String name, String desc, double price, List<ProductImage> images,
            ProductType productType, Origin origin) {
        super(id, name);
        this.desc = desc;
        this.price = price;
        this.images = images;
        this.producttype = productType;
        this.origin = origin;
    }
}

@Data
@Entity
class ProductImage extends AbstractNamedEntity<Integer> {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
class ProductSummary implements AbstractSummaryEntity<Product> {

    private Integer id;
    private String name, desc;
    private List<ProductImage> images;
    private double price;
    private ProductType producttype;
    private Origin origin;

    public ProductSummary(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.desc = p.getDesc();
        this.images = new ArrayList();
        this.price = p.getPrice();
        this.producttype = p.getProducttype();
        this.origin = p.getOrigin();
    }

    @Override
    public Product mapTo() {
        return new Product(id, name, desc, price, images, producttype, origin);
    }
}

@Controller
@RequestMapping(path = "/product")
class ProductController extends AbstractSummaryController<ProductRepository, Product, Integer, ProductSummary> {

    @Override
    @Transactional
    protected Integer _delete(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        repository.delete(product);
        return id;
    }

    @Override
    protected ProductSummary buildDTO(Product entity) {
        return new ProductSummary(entity);
    }
}
