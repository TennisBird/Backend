package org.tennis_bird.core.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.checkerframework.common.reflection.qual.ClassBound;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity implements UserDetails {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "mail_address", nullable = false)
    private String mailAddress;
    @Column(name = "telephone_number", nullable = false)
    private String telephoneNumber;
    @Column(name = "mail_verified", nullable = false)
    private boolean emailVerified;
    @Column(name = "avatar_path")
    private String avatarPath;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO implement better role system
        return List.of(new SimpleGrantedAuthority("DEFAULT_USER"));
    }

    // TODO 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
