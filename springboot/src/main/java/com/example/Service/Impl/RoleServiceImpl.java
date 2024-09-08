package com.example.Service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Dao.RoleDao;
import com.example.Entity.Role;
import com.example.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public IPage<Role> getPageRole(Page<Role> page, String rolename) {
        return roleDao.getPageRole(page,rolename);
    }

//    @Override
//    public Role getRoleID(int id) {
//        Role role=roleMapper.selectById(id);
//        return role;
//    }
//
//    @Override
//    public int updateRole(Role role) {
//        int total=roleMapper.updateById(role);
//        return total;
//    }
//
//    @Override
//    public int insertRole(Role role) {
//
//        return roleMapper.insert(role);
//    }
//
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//
//    @Autowired
//    private RoleMenuMapper roleMenuMapper;
//
//    @Override
//    public int deleteOneRole(int id) {
//        QueryWrapper queryWrapper=new QueryWrapper();
//        queryWrapper.eq("roleid",id);
//        int userRoleTotal=userRoleMapper.selectCount(queryWrapper);
//        if(userRoleTotal>0){
//            return 0;
//        }
//        int roleMenuTotal=roleMenuMapper.selectCount(queryWrapper);
//        if (roleMenuTotal>0){
//            return 0;
//        }
//        return roleMapper.deleteById(id);
//    }
//
//    @Override
//    public ResponseResult getAllRoles() {
//        List<Role> roleList=roleMapper.selectList(null);
//        return new ResponseResult(200,"获取全部角色成功",roleList);
//    }
//
//    @Override
//    public ResponseResult updateUserRole(int userid, int roleid) {
//        QueryWrapper queryWrapper=new QueryWrapper();
//        queryWrapper.eq("userid",userid);
//        sysUserRole sysUserRole=userRoleMapper.selectOne(queryWrapper);
//        System.out.println(sysUserRole+"6666666666");
////        不存在数据添加数据
//        if(sysUserRole==null){
//            sysUserRole sysUserRole1=new sysUserRole();
//            sysUserRole1.setUserid(userid);
//            sysUserRole1.setRoleid(roleid);
//            userRoleMapper.insert(sysUserRole1);
//        }else{
//            QueryWrapper queryWrapper1=new QueryWrapper();
//            queryWrapper1.eq("userid",userid);
//            queryWrapper1.eq("roleid",roleid);
//        sysUserRole sysUserRole2=userRoleMapper.selectOne(queryWrapper);
//        sysUserRole sysUserRole3=new sysUserRole();
//        sysUserRole3.setId(sysUserRole2.getId());
//        sysUserRole3.setUserid(userid);
//        sysUserRole3.setRoleid(roleid);
//        userRoleMapper.updateById(sysUserRole3);
//        }
//        return new ResponseResult(200,"分配角色成功");
//    }

}
