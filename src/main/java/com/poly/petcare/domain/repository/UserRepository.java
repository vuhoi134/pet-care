package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUserName(String username);
    User findByUserName(String username);
    User findByUserNameAndPassWord(String username,String password);

    @Query(value="select * from dbo_user u left join dbo_user_role ur on u.id=ur.user_id left join dbo_profile p on u.id=p.user_id where ur.role_id=2 and u.status=:status",nativeQuery = true)
    Page<User> findAllByStatus(@Param("status") String status, Pageable page);
}
