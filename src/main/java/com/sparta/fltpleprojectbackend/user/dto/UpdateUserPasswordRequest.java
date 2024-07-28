package com.sparta.fltpleprojectbackend.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserPasswordRequest {
  private String oldPassword;
  private String newPassword;
}
