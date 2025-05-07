package com.Servindustria.WebPage.User.Model;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="UserAccount", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserAccount implements UserDetails{

        @Id
        @GeneratedValue
        private Integer id;
        @Basic 
        private String name;
        private String last_name;
        private String telephone;
        private String birthDay;
        @Column(nullable = false)
        private String email;
        private String password;
        private String doc;
        @Enumerated(EnumType.STRING)
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority((role.name())));
        }

        @Override
        public String getUsername() {
                return this.email;
        }
}