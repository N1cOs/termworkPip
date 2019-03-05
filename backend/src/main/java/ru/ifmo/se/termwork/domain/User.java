package ru.ifmo.se.termwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ifmo.se.termwork.security.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "app_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public class User implements UserDetails, Serializable {

    @Id
    @JsonView(View.Default.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @JsonView(View.Default.class)
    private String name;

    @JsonView(View.Default.class)
    private String surname;

    @JsonView(View.Default.class)
    private String patronymic;

    @JsonView(View.Expanded.class)
    private String email;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private String password;

    @JsonView(View.Expanded.class)
    private String phone;

    @JsonIgnore
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority")
    )
    private Set<Authority> authorities = new HashSet<>();

    public User(int id, Set<Authority> authorities){
        this.id = id;
        this.authorities = authorities;
    }

    public void setRoles(Role... roles){
        for(Role role : roles){
            this.authorities.add(role.getAuthority());
        }
    }

    public boolean hasRole(Role role){
        return authorities.contains(role.getAuthority());
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }

    public static class View{

        public class Default{}

        public class Expanded extends Default{}
    }
}
