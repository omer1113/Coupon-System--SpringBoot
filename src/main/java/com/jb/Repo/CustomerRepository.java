package com.jb.Repo;

import com.jb.Beans.Customer;
import com.jb.Exception.CustomerException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
    Customer getOneByEmailAndPassword(String email, String password);


    /*
    public void addCustomer(Customer customer) throws SQLException;
    public void updateCustomer(Customer customer) throws SQLException;
    public void deleteCustomer(int customerID) throws SQLException;
    public List<Coupon> getCustomerCoupons(int customerID) throws SQLException;
    boolean existsByEmailAndPassword(String email, String password);
    Customer getEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    List<Coupon> findByIdAndCoupons(int customerID, Coupon coupon) throws CouponException;




     */
    //List<Customer> getAllCustomers() throws CustomerException;

    Customer findById(int customerID) throws CustomerException;

    Customer findByEmail(String email) throws CustomerException;
}
