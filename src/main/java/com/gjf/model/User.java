package com.gjf.model;

import com.gjf.validator.annotation.Phone;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @Author: GJF
 * @Date : 2018/04/18
 * Time   : 16:59
 */
@Data
public class User {
    private int id;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "昵称不能为空") @Size(min = 9,message = "密码长度大于9")
    private String password;
    @Email(message = "邮箱格式不正确")
    private String email;
    @Phone
    private String phone;
    private String gender;
    @NotBlank(message = "学校不能为空")
    private String school;
    @NotBlank(message = "校区不能为空")
    private String campus;
    @NotBlank(message = "学号不能为空")
    private String number;
    private String headImg;
    private short status;
    private Date timeCreated;
    private Date timeModified;

}
