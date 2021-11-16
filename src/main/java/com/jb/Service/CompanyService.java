package com.jb.Service;

import com.jb.Beans.Company;
import com.jb.Beans.Coupon;
import com.jb.Enum.Category;
import com.jb.Exception.CompanyException;
import com.jb.Exception.CouponException;
import com.jb.Repo.CompanyRepository;
import com.jb.Repo.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyService extends ClientService {
    private int companyID;
    //@Autowired
    private final CompanyRepository companyRepository;
    //  @Autowired
    private final CouponRepository couponRepository;
    //    @Autowired
    private final AdminService adminService;

    public boolean login(String email, String password) throws CompanyException {
        if (!companyRepository.existsByEmailAndPassword(email, password)) {
            throw new CompanyException("Incorrect email/password");

        } else if (companyRepository.existsByEmailAndPassword(email, password)){
            this.companyID = companyRepository.getOneByEmailAndPassword(email, password).getId();
        }
        return true;
    }

    public boolean addCoupon(Coupon coupon) throws CouponException, CompanyException {
        if (couponRepository.existsByCompanyIDAndTitle(coupon.getCompanyID(), coupon.getTitle())) {
            throw new CouponException("A coupon with that title already exists at the company");
        }else {
            coupon.setCompanyID(companyID);
            couponRepository.save(coupon);
            Company company = companyRepository.getOne(companyID);
            company.getCoupons().add(coupon);
            adminService.updateCompany(company);
        }
        return true;
    }

    public boolean updateCoupon(Coupon coupon) throws CouponException {
        if ((coupon.getCompanyID()!=this.companyID)
        ||!couponRepository.existsByCompanyIDAndTitle(coupon.getCompanyID(),coupon.getTitle()))
        {
            throw new CouponException("Coupon not found");
        }
        couponRepository.saveAndFlush(coupon);
        return true;
    }

    public boolean deleteCoupon(int couponID) throws CouponException {
        if (!couponRepository.existsById(couponID)) {
            throw new CouponException("Coupon not found");
        }
        else if (couponRepository.findById(couponID).getCompanyID() != this.companyID) {
            couponRepository.deleteCustomerCouponsByCouponID(couponID);
            couponRepository.deleteCompanyCouponsByCouponID(couponID);
            couponRepository.deleteById(couponID);
            return true;
        }
        return false;

    }

    public Coupon getOneCoupon(int couponID) throws CouponException {
        if (!couponRepository.existsById(couponID)) {
            throw new CouponException("Could not find a coupon by that ID");
        }
        return couponRepository.findById(couponID);
    }

    public List<Coupon> getCompanyCoupons() throws CouponException {
        if(couponRepository.findByCompanyID(companyID)==null){
            throw new CouponException("The company does not own any coupons");
        }
        return couponRepository.findByCompanyID(companyID);
    }

    public List<Coupon> getCouponsByCategory(Category category) throws CouponException {
        if (couponRepository.findByCompanyIDAndCategory(companyID, category).isEmpty()) {
            throw new CouponException("No Company coupon of the category "+category+"was found ");
        }
        return couponRepository.findByCompanyIDAndCategory(companyID, category);
    }

    public List<Coupon> getCompanyCouponsByMaxPrize(double maxPrice) throws CouponException {
        if (couponRepository.findByCompanyIDAndPriceLessThan(companyID, maxPrice).isEmpty()) {
            throw new CouponException("No company coupon under the price of " + maxPrice + " was found");
        }
        return couponRepository.findByCompanyIDAndPriceLessThan(companyID, maxPrice);
    }

    public Company getCompanyDetails() throws CompanyException {
        return companyRepository.findById(companyID);
    }
}
