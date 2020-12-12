package com.poly.petcare.domain.specification;

import com.poly.petcare.domain.entites.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Date;

public class ProductStoreSpecification {

    /**
     * Lấy ra danh sách product có quantity > 0 và quantity trong khoảng
     * @param quantity
     * @return
     */
    public static Specification<Product> hasQuantity(int quantity, String operation) {
        if (operation.equalsIgnoreCase(">")) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("quantityStore"), quantity);
        }
        else if (operation.equalsIgnoreCase("<")) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("quantityStore"), quantity);
        }
        return null;
    }

    /**
     * Lấy ra danh sách product có ExpiryDate > thời gian hiện tại
     * @param
     * @return
     */
    public static Specification<Product> hasExpiryDate() {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("expiryDate"), new Date().getTime());
    }

    /**
     * Lấy ra danh sách product có trạng thái = true
     * @param
     * @return
     */
    public static Specification<Product> hasStatus() {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("products").get("status"), true);
    }

    /**
     * Lấy ra danh sách product có không có ExpiryDate
     * @param
     * @return
     */
    public static Specification<Product> hasNoExpiryDate() {
        return (root, query, cb) -> cb.equal(root.get("expiryDate"), 0);
    }

    /**
     * Lấy ra danh sách product theo đối tượng tìm kiếm
     * @param key,content
     * @return
     */
    public static Specification<Product> hasProduct(String key,String content,String operation) {
        if(operation.equalsIgnoreCase(":")) {
            return (root, query, cb) -> cb.like(root.get("products").get(key), "%" + content + "%");
        }else if(operation.equalsIgnoreCase("=")){
                Long id = Long.parseLong(content);
                return (root, query, cb) -> cb.equal(root.get("products").get(key), id);
        }
        return null;
    }

//    /**
//     * Lấy ra danh sách product theo categoryAttributeId
//     * @param id
//     * @return
//     */
//    public static Specification<Product> hasProductCategoryAttribute(long id) {
//        return (root, query, cb) -> cb.equal(root.get("products").get("categoryAttributeValues").get("categoryAttribute").get("id"), id);
//    }


    /**
     * Lấy ra danh sách product có price theo khoảng
     * @param price
     * @return
     */
    public static Specification<Product> hasPrice(BigDecimal price, String operation) {
        if (operation.equalsIgnoreCase(">")) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("products").get("price"), price);
        }
        else if (operation.equalsIgnoreCase("<")) {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("products").get("price"), price);
        }
        return null;
    }

    /**
     * Lấy ra danh sách product theo categoryId
     * @param id
     * @return
     */
    public static Specification<Product> hasProductCategory(long id) {
        return (root, query, cb) -> cb.equal(root.get("products").get("category").get("id"), id);
    }

    /**
     * Lấy ra danh sách product theo brandId
     * @param id
     * @return
     */
    public static Specification<Product> hasProductBrand(long id) {
        return (root, query, cb) -> cb.equal(root.get("products").get("brand").get("id"), id);
    }

    /**
     * Lấy ra danh sách product theo codeTag
     * @param codeTag
     * @return
     */
    public static Specification<Product> hasProductByCodeTag(String codeTag) {
        return (root, query, cb) -> cb.equal(root.get("codeTag"), codeTag);
    }

    /**
     * Lấy ra danh sách product theo đối tượng tìm kiếm
     * @param content
     * @return
     */
    public static Specification<Product> hasProductByName(String content) {
            return (root, query, cb) -> cb.like(root.get("products").get("name"), "%" + content + "%");
    }
}
