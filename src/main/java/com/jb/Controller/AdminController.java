package com.jb.Controller;

import com.jb.Authorization.JWTUtil;
import com.jb.Authorization.UserDetails;
import com.jb.Beans.Company;
import com.jb.Beans.Customer;
import com.jb.Config.LoginManager;
import com.jb.Exception.AdminException;
import com.jb.Exception.CompanyException;
import com.jb.Exception.CouponException;
import com.jb.Exception.CustomerException;
import com.jb.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final JWTUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (adminService.login(userDetails.getEmail(), userDetails.getPassword())) {
                String myToken = jwtUtil.generateToken(new UserDetails(userDetails.getEmail(), LoginManager.ClientType.Admin));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (AdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // @PreAuthorize("hasAuthority('company:write')")
    @PostMapping("company/add")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws CompanyException {
        if (jwtUtil.validateToken(token)) {
            String newToken = jwtUtil.generateToken(new UserDetails(
                    jwtUtil.extractUserEmail(token),
                    LoginManager.ClientType.Admin));
            System.out.println("added company token: " + newToken);
            return ResponseEntity.ok()
                    .header("Authorization", newToken)
                    .body(adminService.addCompany(company));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("company/update") //http://localhost:8080/company/update
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.updateCompany(company));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

   // // @PreAuthorize("hasAuthority('company:write')")
    @DeleteMapping("company/delete/{companyID}") //http://localhost:8080/company/delete/1
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws CompanyException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.deleteCompany(companyID));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("company/{companyID}") //http://localhost:8080/company/1
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyID) throws CompanyException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.getOneCompany(companyID));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    // @PreAuthorize("hasAuthority('company:write')")
    @GetMapping("allCompanies") //http://localhost:8080/company/allCompanies
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            try {
                return ResponseEntity.ok()
                        .header("Authorization", jwtUtil.generateToken(new UserDetails(
                                jwtUtil.extractUserEmail(token),
                                LoginManager.ClientType.Admin)))
                        .body(adminService.getAllCompanies());
            } catch (CompanyException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("customer/add")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            String newToken = jwtUtil.generateToken(new UserDetails(
                    jwtUtil.extractUserEmail(token),
                    LoginManager.ClientType.Admin));
            System.out.println("added customer token: " + newToken);
            return ResponseEntity.ok()
                    .header("Authorization", newToken)
                    .body(adminService.addCustomer(customer));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("customer/update") //http://localhost:8080/customer/update
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.updateCustomer(customer));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("customer/delete/{customerID}") //http://localhost:8080/customer/delete/1
    public ResponseEntity deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws CustomerException, CouponException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.deleteCustomer(customerID));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("customer/{customerID}") //http://localhost:8080/customer/1
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerID) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.getOneCustomer(customerID));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("allCustomers") //http://localhost:8080/customer/allCustomers
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws CustomerException {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwtUtil.generateToken(new UserDetails(jwtUtil.extractUserEmail(token), LoginManager.ClientType.Admin)))
                    .body(adminService.getAllCustomers());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}

