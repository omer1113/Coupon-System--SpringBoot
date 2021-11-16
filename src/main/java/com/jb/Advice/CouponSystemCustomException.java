package com.jb.Advice;


import com.jb.Exception.CompanyException;
import com.jb.Exception.CouponException;
import com.jb.Exception.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@ControllerAdvice  //aop -> exception
public class CouponSystemCustomException {
    @ExceptionHandler(value = {CompanyException.class})
    //what to return in response
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception e){
        return new ErrorDetail("Company not found",e.getMessage());
    }


    @ExceptionHandler(value = {CouponException.class})
    //what to return in response
    @ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
    public ErrorDetail handleUpdateException(Exception e){
        return new ErrorDetail("Coupon not found",e.getMessage());
    }


    @ExceptionHandler(value = {CustomerException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail handleUserException(Exception e){
        return new ErrorDetail("Customer not found",e.getMessage());
    }
}
