package com.jb.Controller;

import com.jb.Authorization.JWTUtil;
import com.jb.Authorization.UserDetails;
import com.jb.Beans.Coupon;
import com.jb.Config.LoginManager;
import com.jb.Enum.Category;
import com.jb.Exception.CouponException;
import com.jb.Exception.CustomerException;
import com.jb.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    //@CrossOrigin
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (customerService.login(userDetails.getEmail(), userDetails.getPassword())) {
                String myToken = jwtUtil.generateToken(new UserDetails(userDetails.getEmail(), LoginManager.ClientType.Customer));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CustomerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

   // @CrossOrigin
    @PostMapping("purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Customer)))
                    .body(customerService.purchaseCoupon(coupon));
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("category/{category}")
    public ResponseEntity<?> getCategoryCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Customer)))
                    .body(customerService.getCustomerCouponsByCategory(category));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("allCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Customer)))
                    .body(customerService.getCustomerCoupons());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("maxPrice/{maxPrice}")
    public ResponseEntity<?> getMaxPriceCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Customer)))
                    .body(customerService.getCustomerCouponsByMaxPrice(maxPrice));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Customer)))
                    .body(customerService.getCustomerDetails());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
