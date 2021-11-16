package com.jb.Controller;

import com.jb.Beans.Customer;
import com.jb.Config.LoginManager;
import com.jb.Enum.Category;
import com.jb.Exception.CustomerException;
import com.jb.Repo.CouponRepository;
import com.jb.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class GuestController {
    private final AdminService adminService;
    private final CouponRepository couponRepository;
    private final LoginManager loginManager;

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAllCategoryCoupons(@PathVariable Category category) {
        return ResponseEntity.ok(couponRepository.findByCategory(category));
    }

    @GetMapping("coupon/{couponID}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int couponID) {
        return ResponseEntity.ok(couponRepository.findById(couponID));
    }

    @GetMapping("companies/{companyID}")
    public ResponseEntity<?> getAllCompanyCoupons(@PathVariable int companyID) {
        return ResponseEntity.ok(couponRepository.findByCompanyID(companyID));
    }


    //@CrossOrigin
    @PostMapping("register") //http://localhost:8080/customer/add
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws CustomerException {
        return new ResponseEntity<>(adminService.addCustomer(customer) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

}