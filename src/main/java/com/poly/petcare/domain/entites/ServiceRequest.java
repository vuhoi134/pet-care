package com.poly.petcare.domain.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "dbo_service_request")
public class ServiceRequest extends BaseEntity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code",unique = true)
    private String code;

    @OneToMany(mappedBy = "serviceRequest")
    private List<ServiceRequestDetail> serviceRequestDetails;
}
