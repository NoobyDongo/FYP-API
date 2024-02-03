package com.application.business;

import com.application.AbstractBaseController;
import com.application.AbstractBaseEntity;
import com.application.AbstractController;
import com.application.AbstractEntity;
import com.application.AbstractRepostory;
import com.application.company.Inventory;
import com.application.company.InventoryProduct;
import com.application.company.InventoryRepository;
import com.application.product.Product;
import com.application.product.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends AbstractEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = true, updatable = false)
    private Customer customer;
    
    @JsonIgnore
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.REMOVE)
    private List<InvoiceProduct> invoiceProduct;
}

@Controller
@RequestMapping(path = "/invoice")
class InvoiceController extends AbstractController<InvoiceRepository, Invoice, Integer> {
}
