package com.application.business;

import com.application.AbstractBaseController;
import com.application.AbstractBaseEntity;
import com.application.AbstractRepostory;
import com.application.company.Inventory;
import com.application.company.InventoryRepository;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
class InvoiceProductKey implements Serializable {

    @Column(name = "product_id", nullable = false)
    private Integer product;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoice;

    @Column(name = "inventory_id", nullable = false)
    private Integer inventory;
}

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProduct extends AbstractBaseEntity {

    @EmbeddedId
    InvoiceProductKey id;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @MapsId("invoice")
    @JoinColumn(name = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;

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
    private Double price;
}

@Data
class InvoiceProductAddDto {

    private InvoiceProductKey id;
    private Integer product;
    private Integer invoice;
    private Integer inventory;
    private Double price;
    private Integer quantity;
}

@Controller
@RequestMapping(path = "/invoiceproduct")
class InvoiceProductController
        extends AbstractBaseController<InvoiceProductRepository, InvoiceProduct, InvoiceProductKey> {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/{id}")
    public @ResponseBody
    List<InvoiceProduct> getById(@PathVariable("id") Integer id) {
        var ip = repository.findByIdInvoice(id);
        return ip;
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    InvoiceProduct add(@Valid @RequestBody InvoiceProductAddDto dto) {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setProduct(productRepository.findById(dto.getProduct())
                .orElseThrow(() -> new EntityNotFoundException("Product not found")));
        invoiceProduct.setInventory(inventoryRepository.findById(dto.getInventory())
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found")));
        invoiceProduct.setInvoice(invoiceRepository.findById(dto.getInvoice())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found")));

        if (dto.getId() != null) {
            invoiceProduct.setId(dto.getId());
            if (invoiceProduct.getId().getProduct().intValue() != dto.getProduct().intValue() || invoiceProduct.getId().getInventory().intValue() != dto.getInventory().intValue()) {
                repository.deleteById(dto.getId());
                InvoiceProductKey key = new InvoiceProductKey();
                key.setInvoice(dto.getId().getInvoice());
                key.setProduct(dto.getProduct());
                key.setInventory(dto.getInventory());
                invoiceProduct.setId(key);
            }
        } else {
            InvoiceProductKey key = new InvoiceProductKey();
            key.setInvoice(dto.getInvoice());
            key.setProduct(dto.getProduct());
            key.setInventory(dto.getInventory());
            invoiceProduct.setId(key);
        }
        invoiceProduct.setQuantity(dto.getQuantity());
        invoiceProduct.setPrice(dto.getPrice());
        return repository.save(invoiceProduct);
    }
}

interface InvoiceProductRepository extends AbstractRepostory<InvoiceProduct, InvoiceProductKey> {

    List<InvoiceProduct> findByIdInvoice(Integer invoiceId);
}
