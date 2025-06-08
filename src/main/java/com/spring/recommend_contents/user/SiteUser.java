package com.spring.recommend_contents.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Builder
    public SiteUser() { }
    public SiteUser(String email) {
        this.email = email;
    }
}
