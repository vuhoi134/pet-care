package com.poly.petcare.domain.repository.product.impl;

import com.poly.petcare.app.dtos.response.ProductResponse;
import com.poly.petcare.domain.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public List<ProductResponse> listProduct(int page, int limit) {
        String query="select p.id,p.code,p.name ,p.description_long,p.description_short,ps.price,ps.quantity_store as quantity,ps.expiry_date,b.name as brandName,u.value as unitValue,c.name as categoryName\n"
                +"from dbo_product as p left join dbo_product_store as ps on p.id=ps.product_id left join dbo_brand as b on b.id = p.brand_id left join dbo_unit as u on u.id = p.unit_id left join dbo_category as c on c.id = p.category_id\n"
                +"where ps.quantity_store > 0\n"
                +"limit ?,?";
        List<ProductResponse> list= (List<ProductResponse>) jdbcTemplate.query(query,new Object[]{page,limit},new BeanPropertyRowMapper<>(ProductResponse.class));
        return list;
    }
}
