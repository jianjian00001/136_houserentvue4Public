package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Dao.UserDao;
import com.example.Entity.*;
import com.example.Service.RoleService;
import com.example.Service.UserRoleService;
import com.example.Service.UserService;
import com.example.annotation.PassToken;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    @PassToken
    public ResponseResult login(String username, String password){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user=userService.getOne(queryWrapper);
        if(user==null){
            return new ResponseResult(100,"账户和密码错误");
        }
        if(user.getStatus()==1){
            return new ResponseResult(100,"该用户已禁用，请联系管理员");
        }
        user.setPassword(null);
        String token = JwtUtil.sign(user.getId());
//对头部的数据进行处理
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("token",token);
        hashMap.put("user",user);
        return new ResponseResult(200,"登录数据成功",hashMap);
    }

    @GetMapping("/insertUser")
    public ResponseResult insertUser(String username,String password){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        int total=userService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"已存在该用户");
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatetime(new Date());
        userService.save(user);
        User userByName = userDao.getUserByName(username);
        UserRole userRole = new UserRole();
        userRole.setRoleid(4);
        userRole.setUserid(userByName.getId());
        userRoleService.save(userRole);
        return new ResponseResult(200,"注册用户成功");
    }

    @GetMapping("/getperson")
    public ResponseResult getperson(HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        System.out.println(token);
        int userid=JwtUtil.returnUserId(token);
        User user=userService.getById(userid);
        user.setPassword(null);
        return new ResponseResult(200,"获取用户信息成功",user);
    }

//    获取全部菜单栏数据
@RequestMapping("/getAllPermission")
public ResponseResult getAllPermission(HttpServletRequest httpServletRequest) throws Exception {
    String token = httpServletRequest.getHeader("token");
    int userid=JwtUtil.returnUserId(token);
    List<Menu> list=userService.getAllPermission(userid);
    return new ResponseResult(200,"获取数据成功",list);
}

//    @RequestMapping("/getAllRole")
//    public ResponseResult getAllRole(HttpServletRequest httpServletRequest) throws Exception {
//        String token = httpServletRequest.getHeader("token");
//        int userid=JwtUtil.returnUserId(token);
//        List<Role> list=userService.getAllRole(userid);
//        return new ResponseResult(200,"获取数据成功",list);
//    }

    @GetMapping("/resetPassword")
    @Transactional
    public ResponseResult resetPassword(int id){
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",id);
        updateWrapper.set("password","123456");
        userService.update(null, updateWrapper);
        return new ResponseResult(200,"重置密码成功，密码为123456");
    }

    @DeleteMapping("/deleteOneUser")
    public ResponseResult deleteOneUser(int id){
        userService.removeById(id);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",id);
        userRoleService.remove(queryWrapper);
        return new ResponseResult(200,"删除用户成功");
    }


//    分页全部
    @GetMapping("/getPageUser")
    public ResponseResult getPageUser(int page,int size,String username){
        Page<User> answerVOPage = new Page<>(page,size);
        IPage<User> answerVOIPage = userService.getPageUser(answerVOPage,username);
        return new ResponseResult(200,"获取数据成功",answerVOIPage);
    }

    @PostMapping("/adduser")
    public ResponseResult addUser(@Validated @RequestBody User user){
        List<User> userList=userService.list();
        for(int i=0;i<userList.size();i++){
            System.out.println(userList.get(i).getUsername());
            if(userList.get(i).getUsername().equals(user.getUsername())){
                return new ResponseResult(100,"用户名已经存在");
            }
        }
        user.setCreatetime(new Date());
        userService.save(user);
        return new ResponseResult(200,"添加用户成功");
    }


    @PostMapping("/updateUserById")
    public ResponseResult updateUserById(@RequestBody User user){
        userService.updateById(user);
        return new ResponseResult(200,"更改数据成功");
    }

    @PostMapping("/updatesecert")
    public ResponseResult updatesecert(@RequestBody Map hashMap,HttpServletRequest httpServletRequest){
        System.out.println(hashMap);
        String password= (String) hashMap.get("oldpassword");
        String newpassword= (String) hashMap.get("newpassword");
        String renewpassword= (String) hashMap.get("renewpassword");
        String token=httpServletRequest.getHeader("token");
        System.out.println(token);
        int userid=JwtUtil.returnUserId(token);
        User user=userService.getById(userid);
        if(!user.getPassword().equals(password)){
            return new ResponseResult(100,"原密码输入错误，请重新输入");
        }
        if(!newpassword.equals(renewpassword)){
            return new ResponseResult(100,"修改密码不一致，请重新输入");
        }
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("password",renewpassword);
        updateWrapper.eq("id",userid);
        userService.update(null,updateWrapper);
        return new ResponseResult(200,"修改密码成功");
    }


    @GetMapping("/getUserID")
    public ResponseResult getUserID(int id){
        User user=userService.getById(id);
        return new ResponseResult(200,"获取数据成功",user);
    }

    @PostMapping("/updateUser")
    public ResponseResult updateUser(@Validated @RequestBody User user){
//        根据user查询
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User user1=userService.getOne(queryWrapper);
        if (user1==null){
            userService.updateById(user);
        }
        if(user1.getId()!=user.getId()){
            return new ResponseResult(100,"已存在该用户了,禁止修改用户名");
        }
        userService.updateById(user);
        return new ResponseResult(200,"更新数据成功");
    }

    @Autowired
    private RoleService roleService;
    @GetMapping("/getCheckedRoles")
    public ResponseResult getCheckedRoles(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",id);
        List<UserRole> userRoleList=userRoleService.list(queryWrapper);
        List<Role> roleList=new ArrayList<>();
        for (UserRole item:userRoleList){
            Role role=roleService.getById(item.getRoleid());
            if(role!=null){
                roleList.add(role);
            }
        }
        return new ResponseResult(200,"获取角色数据成功",roleList);
    }

    @PostMapping("/updateUserRole")
    @Transactional
    public ResponseResult updateUserRole(int userid,@RequestBody String[] role){
        System.out.println(userid);
        System.out.println(role);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("userid",userid);
//        删除userrole全部数据
        userRoleService.remove(queryWrapper);
        for (String item:role){
            QueryWrapper queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("rolename",item);
            Role role1=roleService.getOne(queryWrapper1);
            UserRole userRole=new UserRole();
            userRole.setUserid(userid);
            userRole.setRoleid(role1.getId());
            userRoleService.save(userRole);
        }
        return new ResponseResult(200,"更改角色成功");
    }



    @RequestMapping( "/addpicture")
    @PassToken
    public String uploadFile(@RequestParam MultipartFile file,String pic,String id) throws IOException {
        if(!pic.equals("")){
            String[] fields =pic.split("/");
            System.out.println(fields);
            String filePath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\person\\"+fields[fields.length-1];
            System.out.println(filePath);
            File fileOld = new File(filePath);
//            路径是个文件且不为空时删除文件
            fileOld.delete();
        }
        String filePath =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\person" ;

        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);

//        更新上传图片数据
        String picturePath="http://localhost:8888/images/person/" + newFileName;
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("pic",picturePath);
        updateWrapper.eq("id",id);
        userService.update(null,updateWrapper);

        return "http://localhost:8888/images/person/" + newFileName;
    }


    @RequestMapping("/insert")
    @PassToken
    @Transactional
    public ResponseResult insert(String username,String password){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        int total=userService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"已存在该用户了");
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatetime(new Date());

        boolean flag=userService.save(user);
        if (!flag){
            return new ResponseResult(100,"添加数据失败");
        }
        User user1=userService.getOne(queryWrapper);
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("rolekey","yonghu");
        Role role= roleService.getOne(queryWrapper1);
        UserRole userRole=new UserRole();
        userRole.setUserid(user1.getId());
        userRole.setRoleid(role.getId());
        boolean userRoleFlag=userRoleService.save(userRole);
        if (!userRoleFlag){
            return new ResponseResult(100,"添加用户数据失败");
        }

        return new ResponseResult(200,"注册用户成功");
    }


    @GetMapping("/getUserBookUserID")
    public ResponseResult getUserBookUserID(int id){
        User user=userService.getById(id);
        user.setPassword(null);
        return new ResponseResult(200,"获取数据成功",user);
    }
}
