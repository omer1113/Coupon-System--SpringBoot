package com.jb.Beans;

import com.jb.Enum.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Table(name="Coupon")
public class Coupon
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    private int companyID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String title;

    private String description;


    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double price;

    //private String image;
}
