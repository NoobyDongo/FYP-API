package com.application.product;

import com.application.AbstractController;
import com.application.AbstractNamedEntity;
import com.application.AbstractRepostory;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@NoArgsConstructor
public class Origin extends AbstractNamedEntity<Integer>{
    public Origin(Integer id) {
        super(id);
    }
}

@Controller
@RequestMapping(path = "/origin")
class OriginController extends AbstractController<OriginRepository, Origin> {
}

interface OriginRepository extends AbstractRepostory<Origin, Integer> {
}
