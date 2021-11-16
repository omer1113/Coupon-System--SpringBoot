package com.jb.Controller;

import com.jb.Authorization.JWTUtil;
import com.jb.Authorization.UserDetails;
import com.jb.Beans.Coupon;
import com.jb.Config.LoginManager;
import com.jb.Enum.Category;
import com.jb.Exception.CompanyException;
import com.jb.Exception.CouponException;
import com.jb.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final JWTUtil jwtUtil;


   // @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (companyService.login(userDetails.getEmail(), userDetails.getPassword())) {
                String myToken = jwtUtil.generateToken(new UserDetails(userDetails.getEmail(), LoginManager.ClientType.Company));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CompanyException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

   // @CrossOrigin
   @PostMapping("addCoupon")
   public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException, CompanyException {
       if (jwtUtil.validateToken(token)) {
           return ResponseEntity.ok()
                   .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                   .body(companyService.addCoupon(coupon));
       } else {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
   }

    //@CrossOrigin
    @PutMapping("update")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.updateCoupon(coupon));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

   // @CrossOrigin
    @DeleteMapping("deleteCoupon/{couponID}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws CouponException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.deleteCoupon(id));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("{couponID}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws CouponException, SQLException, CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.getOneCoupon(id));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("allCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(companyService.getCompanyCoupons());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("category/{category}")
    public ResponseEntity<?> getCategoryCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws CompanyException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.getCouponsByCategory(category));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("maxPrice/{maxPrice}")
    public ResponseEntity<?> getMaxPriceCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws CompanyException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.getCompanyCouponsByMaxPrize(maxPrice));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Company)))
                    .body(companyService.getCompanyDetails());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}



