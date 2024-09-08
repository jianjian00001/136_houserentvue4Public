package com.example.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("roleMenu")
public class RoleMenu {
    private int id;
    private int roleid;
    private int menuid;
}
