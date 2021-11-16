package com.jb.Beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Table(name="Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Singular
    private List<Coupon> coupons;

}
