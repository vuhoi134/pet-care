package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @LastModifiedDate
    @Column(name="lastModified_datetime")
    private LocalDateTime lastModifiedDateTime;

    @CreatedDate
//    @Temporal(TemporalType.DATE)
    @Column(name="created_datetime")
    private LocalDateTime createdDateTime;
}
