package io.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user",
   uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
   }
)
public class User implements UserDetails {
   @Id
   private String userId;
   private String email;
   private String name;
   private String password;
   private Roles roles=Roles.USER;
   private String profileImageUrl;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<SimpleGrantedAuthority> authorities=new HashSet<>();
      authorities.add(new SimpleGrantedAuthority(roles.name()));
      return authorities;
   }

   @Override
   public String getUsername() {
      return email;
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
}
