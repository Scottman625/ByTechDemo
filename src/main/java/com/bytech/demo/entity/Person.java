package com.bytech.demo.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


//    @GeneratedValue
//    @Column(name="_id")
//    private UUID _id;
    @Column(unique = true,name="account")
    private String account;


    @Column(name="name")
    private String name;

//    @PrePersist
//    public void prePersist() {
//        if (_id == null) {
//            _id = UUID.randomUUID();
//        }
//    }

    public Person(){}


        public Person(String account, String name) {
            this.account = account;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

//        public UUID get_id() {
//            return _id;
//        }
//
//        public void set_id(UUID _id) {
//            this._id = _id;
//        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String username) {
            this.account = username;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }


}
