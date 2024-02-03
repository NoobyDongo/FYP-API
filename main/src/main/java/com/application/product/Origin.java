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
public class Origin extends AbstractNamedEntity<Integer>{
    
    @JsonIgnore
    @OneToMany(mappedBy = "origin", cascade = CascadeType.REMOVE)
    private List<Product> product;
    
    public Origin(Integer id) {
        super(id);
    }
}

@Controller
@RequestMapping(path = "/origin")
class OriginController extends AbstractController<OriginRepository, Origin, Integer> {
}

interface OriginRepository extends AbstractRepostory<Origin, Integer> {
}
