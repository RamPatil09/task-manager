package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "org_name")
    private String org_name;
    @Column(name = "reg_number")
    private String reg_number;
    @Column(name = "business_email")
    private String business_email;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private boolean approved;
}
