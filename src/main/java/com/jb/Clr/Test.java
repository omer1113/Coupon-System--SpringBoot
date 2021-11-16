package com.jb.Clr;

import com.jb.Beans.Company;
import com.jb.Beans.Coupon;
import com.jb.Beans.Customer;
import com.jb.Config.LoginManager;
import com.jb.Enum.Category;
import com.jb.Service.AdminService;
import com.jb.Service.CompanyService;
import com.jb.Service.CustomerService;
import com.jb.Utils.LocalHost;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    private final LoginManager loginManager;

    public void run(String... args) throws Exception {
        System.out.println(LocalHost.SERVER_INFO);

        Coupon coupon1 = Coupon.builder().
                category(Category.ELECTRICITY)
                .title("TV")
                .description("40 inch Plasma")
                .startDate(LocalDate.of(2021, 8, 5))
                .endDate(LocalDate.of(2021, 8, 20))
                .amount(20)
                .price(1000)
            //    .image("TV")
                .build();
        Coupon coupon2 = Coupon.builder().
                category(Category.FOOD)
                .title("Supermarket")
                .description("pasta")
                .startDate(LocalDate.of(2021, 5, 5))
                .endDate(LocalDate.of(2022, 5, 5))
                .amount(50)
                .price(45)
           //     .image("supermarket")
                .build();

        Coupon coupon3 = Coupon.builder().
                category(Category.VACATION)
                .title("Italy")
                .description("1 hotel suit by the lake")
                .startDate(LocalDate.of(2021, 7, 1))
                .endDate(LocalDate.of(2022, 7, 1))
                .amount(20)
                .price(500)
         //       .image("/Assets/italyVacation.jpg")
                .build();

        Coupon coupon4 = Coupon.builder().
                category(Category.RESTAURANT)
                .title("Dinner at a restaurant")
                .description("meal, desert and 2 drink after 6pm")
                .startDate(LocalDate.of(2021, 10, 1))
                .endDate(LocalDate.of(2022, 04, 1))
                .amount(15)
                .price(50)
          //      .image("file:///C:/Users/Omer/my/src/Assets/italianRestaurant.jpg")
                .build();

        Company company1 = Company.builder().name("Company1")
                .email("company1@company.com")
                .password("1company1")
                .build();

        Company company2 = Company.builder().name("Company2")
                .email("company2@company.com")
                .password("2company2")
                .build();

        Company company3 = Company.builder().name("Company3")
                .email("company3@company.com")
                .password("3company3")
                .build();

        Company company4 = Company.builder().name("Company4")
                .email("company4@company.com")
                .password("4company4")
                .build();


        Customer customer1 = Customer.builder()
                .firstName("Customer 1")
                .LastName("Last name 1")
                .email("customer1@customer.com")
                .password("1customer1")
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Customer 2")
                .LastName("Last name 2")
                .email("customer2@customer.com")
                .password("2customer2")
                .build();

        Customer customer3 = Customer.builder()
                .firstName("Customer 3")
                .LastName("Last name 3")
                .email("customer3@customer.com")
                .password("3customer3")
                .build();

        Customer customer4 = Customer.builder()
                .firstName("Customer 4")
                .LastName("Last name 4")
                .email("customer4@customer.com")
                .password("4customer4")
                .build();

        Customer customer5 = Customer.builder()
                .firstName("Customer 5")
                .LastName("Last name 5")
                .email("customer5@customer.com")
                .password("5customer5")
                .build();

        AdminService myAdmin = (AdminService) loginManager.Login(LoginManager.ClientType.Admin, "admin@admin.com", "admin");

        myAdmin.addCompany(company1);
        myAdmin.addCompany(company2);
        myAdmin.addCompany(company3);
        myAdmin.addCompany(company4);

        company1 = myAdmin.getOneCompany(1);
        company2 = myAdmin.getOneCompany(2);
        company2 = myAdmin.getOneCompany(3);
        company4 = myAdmin.getOneCompany(4);

        myAdmin.addCustomer(customer1);
        myAdmin.addCustomer(customer2);
        myAdmin.addCustomer(customer3);
        myAdmin.addCustomer(customer4);
        myAdmin.addCustomer(customer5);

        customer1 = myAdmin.getOneCustomer(1);
        customer2 = myAdmin.getOneCustomer(2);
        customer3 = myAdmin.getOneCustomer(3);

        customer3.setEmail("customer34@customer.com");
        myAdmin.updateCustomer(customer3);

        CompanyService myCompany = (CompanyService) loginManager.Login(LoginManager.ClientType.Company, "company1@company.com", "1company1");

        myCompany.addCoupon(coupon1);
        myCompany.addCoupon(coupon2);
        myCompany.addCoupon(coupon3);
        myCompany.addCoupon(coupon4);

        coupon1 = myCompany.getOneCoupon(1);
        coupon2 = myCompany.getOneCoupon(2);
        coupon3 = myCompany.getOneCoupon(3);
        coupon4 = myCompany.getOneCoupon(4);

        coupon3.setPrice(70);
        myCompany.updateCoupon(coupon3);

        CustomerService myCustomer = (CustomerService) loginManager.Login(LoginManager.ClientType.Customer, "customer1@customer.com", "1customer1");

        myCustomer.purchaseCoupon(coupon1);
        myCustomer.purchaseCoupon(coupon3);
        myCustomer.purchaseCoupon(coupon4);
        myCustomer.purchaseCoupon(coupon2);

        /*
        CustomerService myCustomer = (CustomerService) loginManager.Login(LoginManager.ClientType.Customer, "customer1@customer.com", "1customer1");
        AdminService myAdmin = (AdminService) loginManager.Login(LoginManager.ClientType.Admin, "admin@admin.com", "admin");
        CompanyService myCompany = (CompanyService) loginManager.Login(LoginManager.ClientType.Company, "company1@company.com", "1company1");

         */

    }
}
