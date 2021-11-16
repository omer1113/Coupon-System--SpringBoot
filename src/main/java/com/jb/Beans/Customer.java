package com.jb.Beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Table(name="Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String LastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Singular
    private List<Coupon> coupons;

}
