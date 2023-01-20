package com.dbd.nanal.controller;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

    @NotEmpty
    private String userName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    private String img;

    private String Introduction;

    private Boolean isPrivate;



}
