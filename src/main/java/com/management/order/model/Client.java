package com.management.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "clients")
@Getter @Setter
@Builder
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(length = 50)
    private String name;

    @Column(length = 30)
    private String manager;

    @Column(length = 20)
    private String tel;

    @Column(length = 200)
    private String address;

    @JsonIgnore
    @Column
    private boolean deleted = false;

    public Client() {

    }
}
