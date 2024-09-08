package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.BookHouse;
import com.example.Entity.House;
import com.example.Entity.User;
import com.example.Service.BookTimeService;
import com.example.Service.HouseService;
import com.example.Service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/booktime")
@RestController
public class BookTimeController {

    @Autowired
    private BookTimeService bookTimeService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @PostMapping("/addBookTime")
    public ResponseResult addBookTime(@RequestBody BookHouse bookHouse, HttpServletRequest httpServletRequest){
        bookHouse.setCreatetime(new Date());
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);

        User user=userService.getById(userid);
        if(user.getTel().equals(null)||user.getEmail().equals(null)||
                user.getTel().equals("")||user.getEmail().equals("")){
            return new ResponseResult(100,"预约前须完善电话号码和邮箱");
        }


        House house=houseService.getById(bookHouse.getHouseid());


        if(house.getHouseuserid().equals(userid)){
            return new ResponseResult(100,"不能自己预约自己的房子");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("bookuserid",userid);
        queryWrapper.eq("houseid",bookHouse.getHouseid());
        queryWrapper.eq("booktime",bookHouse.getBooktime());
        int total=bookTimeService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"已经预约了，请不要重复点击");
        }
        bookHouse.setBookuserid(userid);
        bookTimeService.save(bookHouse);
        return new ResponseResult(200,"预约看房时间成功");
    }

    @RequestMapping("/getPageHouseBooktByPerson")
    public ResponseResult getPageHouseBooktByPerson(int page, int size, String housename, HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);

        Page<BookHouse> answerVOPage = new Page<>(page,size);
        IPage<BookHouse> answerVOIPage = bookTimeService.getPageHouseBooktByPerson(answerVOPage,housename,userid);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }


    @RequestMapping("/getPageHouseBooktAll")
    public ResponseResult getPageHouseBooktAll(int page, int size, String housename){
        Page<BookHouse> answerVOPage = new Page<>(page,size);
        IPage<BookHouse> answerVOIPage = bookTimeService.getPageHouseBooktAll(answerVOPage,housename);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }


    @RequestMapping("/getPageHouseBooktByHouse")
    public ResponseResult getPageHouseBooktByHouse(int page, int size, String housename, HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        Page<BookHouse> answerVOPage = new Page<>(page,size);
        IPage<BookHouse> answerVOIPage = bookTimeService.getPageHouseBooktByHouse(answerVOPage,housename,userid);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @DeleteMapping("/deleteHouseBook")
    public ResponseResult deleteHouseBook(int id){
        boolean flag=bookTimeService.removeById(id);
        if(flag){
            return new ResponseResult(200,"取消预约时间成功");
        }
        return new ResponseResult(100,"删除数据失败");
    }

}
