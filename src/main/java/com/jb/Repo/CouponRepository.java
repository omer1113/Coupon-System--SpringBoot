package com.jb.Repo;

import com.jb.Beans.Coupon;
import com.jb.Enum.Category;
import com.jb.Exception.CouponException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findById(int couponID);
    List<Coupon> findByCompanyID(int companyID);
    List<Coupon> findByCompanyIDAndCategory(int companyID, Category category);
    boolean existsByCompanyIDAndTitle(int companyID, String title);
    List<Coupon> findByCompanyIDAndPriceLessThan(int companyID, double maxPrice);
    List<Coupon> findByCategory(Category category);

    //boolean existsByEmailAndPassword(String email, String password) throws CustomerException;
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `couponsystem`.`company_coupons` WHERE company_id=:companyID", nativeQuery = true)
    void deleteCompanyCouponsByCompanyID(int companyID);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `couponsystem`.`customer_coupons` WHERE coupons_id=:couponID", nativeQuery = true)
    void deleteCustomerCouponsByCouponID(int couponID) throws CouponException;

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `couponsystem`.`company_coupons` WHERE coupons_id=:couponID", nativeQuery = true)
    void deleteCompanyCouponsByCouponID(int couponID) throws CouponException;
}
