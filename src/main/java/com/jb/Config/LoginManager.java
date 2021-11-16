package com.jb.Config;

import com.jb.Exception.AdminException;
import com.jb.Exception.CompanyException;
import com.jb.Exception.CustomerException;
import com.jb.Exception.UserException;
import com.jb.Service.AdminService;
import com.jb.Service.ClientService;
import com.jb.Service.CompanyService;
import com.jb.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoginManager {

    public final AdminService adminService;
    public final CompanyService companyService;
    public final CustomerService customerService;

    public enum ClientType {
        Admin, Company, Customer
    }

    public ClientService Login(ClientType clientType, String email, String password) throws UserException, CompanyException, CustomerException, AdminException {
        ClientService result=null;
        switch (clientType) {
            case Admin:
                result = (adminService.login(email, password))? adminService:null;
                break;
            case Company:
                result = (companyService.login(email, password))? companyService:null;
                break;
            case Customer:
                result = (customerService.login(email, password))? customerService:null;
                break;
            default:
                throw new UserException("Unable to login");
        }
        if (result == null) {
            throw new UserException("User not found");
        }
        return result;
    }

}

