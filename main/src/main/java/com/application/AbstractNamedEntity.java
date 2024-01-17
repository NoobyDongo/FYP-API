package com.application;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public abstract class AbstractNamedEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @NotNull
    @Column(nullable = false)
    private String name;

    public AbstractNamedEntity(ID id) {
        super(id);
    }
    
    public AbstractNamedEntity(ID id, String name) {
        super(id);
        this.name = name;
    }
}
