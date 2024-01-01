package com.application.product;

import com.application.AbstractController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
@NoArgsConstructor
public class Origin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Origin(Integer id) {
        this.id = id;
    }
}

@Controller
@RequestMapping(path = "/origin")
class OriginController extends AbstractController<OriginRepository, Origin> {
}

interface OriginRepository extends CrudRepository<Origin, Integer> {
}
