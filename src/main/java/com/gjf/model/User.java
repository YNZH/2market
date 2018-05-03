package com.gjf.model;

import com.gjf.validator.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Email
    private String email;
    @Phone
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