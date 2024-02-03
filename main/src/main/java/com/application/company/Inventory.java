package com.application.company;

import com.application.AbstractController;
import com.application.AbstractNamedEntity;
import com.application.business.InvoiceProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends AbstractNamedEntity<Integer> {

    @NotNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.REMOVE)
    private List<InventoryProduct> inventoryProduct;
    
    @JsonIgnore
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.REMOVE)
    private List<InvoiceProduct> invoiceProduct;
}

@Controller
@RequestMapping(path = "/inventory")
class InventoryController extends AbstractController<InventoryRepository, Inventory, Integer> {
}