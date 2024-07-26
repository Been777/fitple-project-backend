package com.sparta.fltpleprojectbackend.security;

import com.sparta.fltpleprojectbackend.enums.Role;
import com.sparta.fltpleprojectbackend.owner.entity.Owner;
import com.sparta.fltpleprojectbackend.trainer.entity.Trainer;
import com.sparta.fltpleprojectbackend.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

  private final String accountId;
  private final String password;
  private final Role role;

  public UserDetailsImpl(User user) {
    this.accountId = user.getAccountId();
    this.password = user.getPassword();
    this.role = user.getRole();
  }

  public UserDetailsImpl(Owner owner) {
    this.accountId = owner.getAccountId();
    this.password = owner.getPassword();
    this.role = owner.getRole();
  }

  public UserDetailsImpl(Trainer trainer) {
    this.accountId = trainer.getAccountId();
    this.password = trainer.getPassword();
    this.role = trainer.getRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList(); // 권한이 필요하면 여기에 추가
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return accountId;
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

  public Role getRole() {
    return role;
  }
}