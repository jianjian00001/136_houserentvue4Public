package com.example.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@TableName("role")
public class Role {
    private int id;
    @NotBlank(message = "角色名不能为空")
    @NotNull(message = "角色名不能为空")
    private String rolename;
    @NotNull(message = "角色关键字不能为空")
    @NotBlank(message = "角色关键字不能为空")
    private String rolekey;
    private int status;
    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;


}
