package com.poly.petcare.domain.repository;

import com.poly.petcare.domain.entites.User;
import com.poly.petcare.domain.entites.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
//    @Query(value = "SELECT * FROM dbo_user u JOIN dbo_user_role ur " +
//            " ON u.id  = ur.user_id " +
//            " WHERE ur.id = ?1",
//            nativeQuery = true)
//
//    List<User> findUserNameByRoleNameId(Long id);

//
//    @Query(value = "SELECT r.role_name FROM tbl_role r JOIN user_role u " +
//            " ON r.role_id = u.role_id " +
//            " WHERE u.user_id = ?1",
//            nativeQuery = true)
//    List<String> findRoleNamesByUserId(int id);
}
