package com.lgm.demo.model;

import com.lgm.demo.model.enumeration.ESex;
import com.lgm.demo.security.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 150)
    private String password;
    @Size(max = 30)
    private String name;
    @Size(max = 30)
    private String surname;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    // I am saving list of Longs as String because idk how else to save it in db, look at getter and setter
    private String adminOfCompetitions = "";
    private String profileImagePath;
    private String bio;
    @Enumerated
    private ESex sex;

    public User() {}

    public User(String username, String email, String password, String name, String surname, ESex sex) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
    }

    public User(String username, String email, String password, String name, String surname, ESex sex, String bio, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.bio = bio;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role : roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return grantedAuthorities;
    }

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

    public void setAdminOfCompetitions(List<Long> vals) {
        StringBuilder sb = new StringBuilder("");
        for(Long l : vals){
            sb.append(l).append(";");
        }
        adminOfCompetitions = sb.toString();
    }

    public List<Long> getAdminOfCompetitions() {
        List<Long> ans = new ArrayList<>();
        if(adminOfCompetitions == null)
            return ans;
        String[] strs = adminOfCompetitions.split(";");
        for(String s : strs){
            if(s.equals("")) continue;
            ans.add(Long.valueOf(s));
        }
        return ans;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
