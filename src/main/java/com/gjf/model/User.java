package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String nickname;

    private String password;

    private String email;

    private String phone;

    private String gender;

    private String school;

    private String campus;

    private String number;

    private String headImg;

    private Byte status;

    private Date timeCreate;

    private Date timeModified;
}