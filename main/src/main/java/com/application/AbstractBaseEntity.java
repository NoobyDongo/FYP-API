package com.application;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
@NoArgsConstructor
public class AbstractBaseEntity {
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
