package com.jb.Service;

import com.jb.Beans.Coupon;
import com.jb.Beans.Customer;
import com.jb.Enum.Category;
import com.jb.Exception.CouponException;
import com.jb.Exception.CustomerException;
import com.jb.Repo.CouponRepository;
import com.jb.Repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService extends ClientService {
    private int customerID;
    //@Autowired
    private final CustomerRepository customerRepository;
    //@Autowired
    private final CouponRepository couponRepository;


    public boolean login(String email, String password) throws CustomerException {
        if (!customerRepository.existsByEmailAndPassword(email, password)) //if the customer doesn't exist
        {
            throw new CustomerException("You have entered an incorrect password/email address");
        }if (customerRepository.existsByEmailAndPassword(email, password)){
            this.customerID=customerRepository.getOneByEmailAndPassword(email, password).getId();
            System.out.println("Welcome to the coupon system customer");
        }
        return true;
    }



    public List<Coupon> getCustomerCoupons() throws CustomerException {
            Customer customer=customerRepository.getOne(customerID);
            if(customer.getCoupons().isEmpty())
            {
                throw new CustomerException("No coupons were purchased by this customer");
            }
            return customer.getCoupons();
    }

    public List<Coupon> getCustomerCouponsByCategory(Category category) throws CustomerException {
        List<Coupon> categoryCoupons = new ArrayList<>();
        Customer customer = customerRepository.getOne(customerID);
        List<Coupon> originalCoupons = customer.getCoupons();
        if(originalCoupons.isEmpty()){
            throw new CustomerException("Customer doesn't own any coupons");
        }
        for (Coupon item : originalCoupons) {
            if (item.getCategory().equals(category)) {
                categoryCoupons.add(item);
            }
        }
        return categoryCoupons;
    }
    public boolean purchaseCoupon(Coupon coupon) throws CouponException {
        if (!(coupon.getAmount() > 0)) {
            throw new CouponException("The coupon " + coupon.getTitle() + " is unavailable- There are no coupons left at stock.");
        }
        Customer customer = customerRepository.getOne(customerID);
        List<Coupon> coupons = customer.getCoupons();
        for (Coupon item : coupons) {
            if (item.equals(coupon)) {
                throw new CouponException("Unable to purchase the coupon- you already purchased it");
            } else if (coupon.getEndDate().isBefore(LocalDate.now())) {
                throw new CouponException("Coupon expired");
            }
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.saveAndFlush(coupon);
        coupons.add(coupon);
        customer.setCoupons(coupons);
        customerRepository.saveAndFlush(customer);
        return true;
    }
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws CouponException {
        List<Coupon> upToMaxPriceCoupons = new ArrayList<>();
        Customer customer = customerRepository.getOne(customerID);
        List<Coupon> originalCoupons = customer.getCoupons();
        if (originalCoupons.isEmpty()) {
            throw new CouponException("Customer doesn't own any coupons");
        }
        else {
            for (Coupon item : originalCoupons) {
                if (item.getPrice() <= maxPrice) {
                    upToMaxPriceCoupons.add(item);
                }
            }
            if (upToMaxPriceCoupons.isEmpty()) {
                throw new CouponException("There are no coupons below this price");
            }
        }
        return upToMaxPriceCoupons;
    }

    public Customer getCustomerDetails() throws CustomerException {
        return customerRepository.findById(customerID);
    }

}
