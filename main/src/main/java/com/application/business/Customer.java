package com.application.business;

import com.application.AbstractController;
import com.application.AbstractEntity;
import com.application.AbstractRepostory;
import com.application.product.Product;
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
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AbstractEntity<Integer>{
     
    @NotNull
    @Column(nullable = false)
    private String fname;
    
    @NotNull
    @Column(nullable = false)
    private String lname;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    @NotNull
    @Column(nullable = false)
    private String username;
    
    @NotNull
    @Column(nullable = false)
    private String password;
    
    @Length(min = 8, max = 8)
    private String phone;
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Invoice> invoice;
}

@Controller
@RequestMapping(path = "/customer")
class CustomerController extends AbstractController<CustomerRepository, Customer, Integer> {
}

interface CustomerRepository extends AbstractRepostory<Customer, Integer> {
}