package com.jb.Service;

import com.jb.Beans.Company;
import com.jb.Beans.Coupon;
import com.jb.Beans.Customer;
import com.jb.Exception.AdminException;
import com.jb.Exception.CompanyException;
import com.jb.Exception.CouponException;
import com.jb.Exception.CustomerException;
import com.jb.Repo.CompanyRepository;
import com.jb.Repo.CouponRepository;
import com.jb.Repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService extends ClientService {
    //Autowired
    private final CompanyRepository companyRepository;
    //@Autowired
    private final CustomerRepository customerRepository;

    private final   CouponRepository couponRepository;

    public boolean login(String email, String password) throws AdminException {
        if (!email.equals("admin@admin.com") || !password.equals("admin")) {
            throw new AdminException("Wrong email or password");
        } else {
            System.out.println("Welcome Admin");
        }
        return true;
    }

    public boolean addCompany(Company company) throws CompanyException {
        if (companyRepository.existsByName(company.getName())) {
            throw new CompanyException("Name already taken");
        } else if (companyRepository.existsByEmail(company.getEmail())) {
            throw new CompanyException("Email already taken");
        }
        companyRepository.save(company);
        return true;
    }

    public boolean updateCompany(Company company) throws CompanyException {
        if (!companyRepository.existsById(company.getId())) {
            throw new CompanyException("Company not found.");
        }
        companyRepository.saveAndFlush(company);
        return true;
    }

    public boolean deleteCompany(int companyID) throws CompanyException, CouponException {
        if (!customerRepository.existsById(companyID)) {
            throw new CompanyException("Company ID not found.");
        }
        Company company = getOneCompany(companyID);
        List<Coupon> coupons = company.getCoupons();
        for (Coupon item : coupons) {
            couponRepository.deleteCustomerCouponsByCouponID(item.getId());
            couponRepository.deleteCompanyCouponsByCouponID(item.getId());
            couponRepository.deleteById(item.getId());
        }
        companyRepository.deleteById(companyID);
        return true;
    }

    public Company getOneCompany(int companyID) throws CompanyException {
        if (!companyRepository.existsById(companyID)) {
            throw new CompanyException("Company not found");
        }
        return companyRepository.findById(companyID);
    }

    public List<Company> getAllCompanies() throws CompanyException {
        if (companyRepository.findAll().isEmpty()) {
            throw new CompanyException("No companies were found");
        }
        return companyRepository.findAll();
    }


    public boolean addCustomer(Customer customer) throws CustomerException {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerException("Email already exists");
        }
        customerRepository.save(customer);
        return true;
    }

    public boolean updateCustomer(Customer customer) throws CustomerException {
        if (!customerRepository.existsById(customer.getId())) {
            throw new CustomerException("Customer not found");
        }
        customerRepository.saveAndFlush(customer);
        return true;
    }

    public boolean deleteCustomer(int customerID) throws CustomerException, CouponException {
        if (!customerRepository.existsById(customerID)) {
            throw new CustomerException("Customer not found");
        }
        Customer customer = getOneCustomer(customerID);
        List<Coupon> coupons = customer.getCoupons();
        for (Coupon item : coupons) {
            couponRepository.deleteCustomerCouponsByCouponID(item.getId());
        }
        customerRepository.deleteById(customerID);
        return true;
    }

    public Customer getOneCustomer(int customerID) throws CustomerException {
        if (!customerRepository.existsById(customerID)) {
            throw new CustomerException("Company not found");
        }
        return customerRepository.findById(customerID);
    }

    public List<Customer> getAllCustomers() throws CustomerException {
        if (customerRepository.findAll().isEmpty()) {
            throw new CustomerException("No customer was found");
        }
        return customerRepository.findAll();
    }

}
