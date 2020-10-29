package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "dbo_output")
public class Output {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "export_date")
    private Date import_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "output")
    private List<OutputDetail> outputDetails;
}
