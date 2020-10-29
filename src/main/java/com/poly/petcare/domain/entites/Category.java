package com.poly.petcare.domain.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "dbo_category")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    private Long id;
    @Column(name = "level")
    private Integer level;

    @Column(name = "name")
    private String name;
    @Column(name = "states")
    private String states;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<CategoryAttribute> listAttribute = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private Category parentId;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>();
}
