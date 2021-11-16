package com.jb.Threads;

import com.jb.Beans.Coupon;
import com.jb.Exception.CouponException;
import com.jb.Repo.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableAsync
@RequiredArgsConstructor
public class CouponPeriod {
    private final CouponRepository couponRepository;

    @Async
    @Scheduled(cron = "1 0 0 * * ?", zone = "Asia/Jerusalem")
    public void dailyWorkPeriod() throws CouponException {
        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon item : coupons) {
            if (item.getEndDate().isBefore(LocalDate.now())){
                couponRepository.deleteCustomerCouponsByCouponID(item.getId());
                couponRepository.deleteCompanyCouponsByCouponID(item.getId());
                couponRepository.deleteById(item.getId());
                System.out.println("The coupon titled "+item.getTitle()+" was deleted.");
            }
        }
    }
}
