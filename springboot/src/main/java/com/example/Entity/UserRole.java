package com.example.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("userRole")
public class UserRole {
    private int id;
    private int userid;
    private int roleid;
}
