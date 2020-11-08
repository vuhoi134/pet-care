package com.poly.petcare.domain.services;

import com.poly.petcare.domain.entites.Product;
import com.poly.petcare.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServices extends BaseServices {

//    public ResponseEntity<?> search(String name) {
//        List<Product> list = productRepository.findByName(name);
//        if (list.size() == 0) {
//            throw new ResourceNotFoundException("List Empty");
//        }
//        List<ProductResponses> responsesList = new ArrayList<>();
//        for (Product product : list) {
//            ProductResponses responses = modelMapper.productResponses(product);
//            responsesList.add(responses);
//        }
//        return ResponseEntity.ok(responsesList);
//    }

//
//    public ResponseEntity<?> searchByRoleID(Long id) {
//        List<User> list = userRoleRepository.findUserNameByRoleNameId(id);
//        if (list.size() == 0) {
//            throw new ResourceNotFoundException("List Empty");
//        }
//
//        List<UserDTO> dtoList = new ArrayList<>();
//        for (User user : list) {
//            UserDTO dto = new UserDTO();
//            dto.setUserName(user.getUserName());
//            dtoList.add(dto);
//        }
//        return ResponseEntity.ok(dtoList);
//    }

}
