package com.application.company;

import com.application.AbstractBaseController;
import com.application.AbstractBaseEntity;
import com.application.AbstractRepostory;
import com.application.Id;
import com.application.product.Product;
import com.application.product.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
class InventoryProductKey implements Serializable {

    @Column(name = "product_id", nullable = false)
    private Integer product;

    @Column(name = "inventory_id", nullable = false)
    private Integer inventory;
}

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InventoryProduct extends AbstractBaseEntity {

    @EmbeddedId
    InventoryProductKey id;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @MapsId("inventory")
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false)
    private Inventory inventory;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer quantity;

    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer restock;
}

@Data
class InventoryProductAddDto {

    private InventoryProductKey id;
    private Integer product;
    private Integer inventory;
    private Integer restock;
    private Integer quantity;
}

@Controller
@RequestMapping(path = "/inventoryproduct")
class InventoryProductController
        extends AbstractBaseController<InventoryProductRepository, InventoryProduct, InventoryProductKey> {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public @ResponseBody
    List<InventoryProduct> getById(@PathVariable("id") Integer id) {
        var ip = repository.findByIdInventory(id);
        return ip;
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    InventoryProduct add(@Valid @RequestBody InventoryProductAddDto dto) {
        InventoryProduct inventoryProduct = new InventoryProduct();
        inventoryProduct.setProduct(productRepository.findById(dto.getProduct())
                .orElseThrow(() -> new EntityNotFoundException("Product not found")));
        inventoryProduct.setInventory(inventoryRepository.findById(dto.getInventory())
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found")));

        InventoryProductKey key = new InventoryProductKey();
        key.setInventory(dto.getInventory());
        key.setProduct(dto.getProduct());
        inventoryProduct.setId(key);

        if (dto.getId() != null) {
            inventoryProduct.setId(dto.getId());
            if (inventoryProduct.getId().getProduct().intValue() != dto.getProduct().intValue()) {
                repository.deleteById(dto.getId());
                key = new InventoryProductKey();
                key.setInventory(dto.getId().getInventory());
                key.setProduct(dto.getProduct());
                inventoryProduct.setId(key);
            }
        }

        inventoryProduct.setQuantity(dto.getQuantity());
        inventoryProduct.setRestock(dto.getRestock());
        return repository.save(inventoryProduct);
    }

}

interface InventoryProductRepository extends AbstractRepostory<InventoryProduct, InventoryProductKey> {

    List<InventoryProduct> findByIdInventory(Integer inventoryId);
}
