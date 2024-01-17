package com.application;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractEntity<IdType extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private IdType id;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public AbstractEntity(IdType id) {
        this.id = id;
    }
}
