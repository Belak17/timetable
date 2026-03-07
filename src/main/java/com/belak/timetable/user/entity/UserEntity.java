package com.belak.timetable.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;
    private String lastname ;
    private String firstname ;

    @Column(unique = true , nullable = false)
    private String email ;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    public enum Role {
        USER, ADMIN , PROFESSOR , ADMINISTRATOR , STUDENT
    }
    // Implementation de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.
                name()));
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
