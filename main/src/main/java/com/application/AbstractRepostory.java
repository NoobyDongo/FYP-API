package com.application;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepostory<E, ID> extends JpaRepository<E, ID> {
    @Query("SELECT MAX(e.updatedAt) FROM #{#entityName} e")
    LocalDateTime getLastUpdatedTime();
}
