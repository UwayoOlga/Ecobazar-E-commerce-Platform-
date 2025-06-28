package rw.ac.auca.ecommerce.core.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * The class AbstractAuditEntity.
 *
 * @author Jeremie Ukundwa Tuyisenge
 * @version 1.0
 */
@Getter @Setter
@MappedSuperclass
public abstract class AbstractAuditEntity {
    @CreationTimestamp
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
}
