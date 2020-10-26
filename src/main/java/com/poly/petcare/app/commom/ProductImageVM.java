package com.poly.petcare.app.commom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductImageVM {
    private Long id;
    private String link;
    private Date createdDate;
}
