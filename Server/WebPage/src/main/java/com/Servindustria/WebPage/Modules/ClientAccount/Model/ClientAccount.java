package com.Servindustria.WebPage.Modules.ClientAccount.Model;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Employee.Model.Employee;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name="user_account", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class ClientAccount implements UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 150)
        private String email;

        @Column(nullable = false, length = 255)
        private String password;

        @Enumerated(EnumType.STRING)
        private Role role;

        @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
        private Client client;

        
        @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
        private Employee employee;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority((role.name())));
        }

        @Override
        public String getUsername() {
                return this.email;
        }
}