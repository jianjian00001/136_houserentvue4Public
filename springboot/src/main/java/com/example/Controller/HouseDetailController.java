package com.example.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Entity.House;
import com.example.Entity.HouseDetail;
import com.example.Service.HouseDetailService;
import com.example.Service.HouseService;
import com.example.utils.JwtUtil;
import com.example.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/houseDetail")
@RestController
public class HouseDetailController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("/getPageHouseDetail")
    public ResponseResult getPageHouse(int page,int size,String housename){
        Page<HouseDetail> answerVOPage = new Page<>(page,size);
        IPage<HouseDetail> answerVOIPage = houseDetailService.getPage(answerVOPage,housename);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }

    @RequestMapping("/getPageHouseDetailByPerson")
    public ResponseResult getPageHouseDetailByPerson(int page, int size, String housename, HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("token");
        int userid= JwtUtil.returnUserId(token);
        Page<HouseDetail> answerVOPage = new Page<>(page,size);
        IPage<HouseDetail> answerVOIPage = houseDetailService.getPageHouseDetailByPerson(answerVOPage,housename,userid);

        return new ResponseResult(200,"获取分页数据成功",answerVOIPage);
    }


    @Autowired
    private HouseDetailService houseDetailService;

    @GetMapping("/getHouseDetailById")
    public ResponseResult getHouseDetailById(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",id);
        HouseDetail houseDetail=houseDetailService.getOne(queryWrapper);
        House house= houseService.getById(houseDetail.getHouseid());
        if(houseDetail==null){
            return new ResponseResult(100,"房屋摆设暂时不存在，可以先输入");
        }
        houseDetail.setHousename(house.getHousename());
        return new ResponseResult(200,"获取房屋详情成功",houseDetail);
    }

    @GetMapping("/getHouseDetailByHouseID")
    public ResponseResult getHouseDetailByHouseID(int id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",id);
        HouseDetail houseDetail=houseDetailService.getOne(queryWrapper);
        if(houseDetail==null){
            return new ResponseResult(100,"房屋摆设暂时不存在，可以先输入");
        }
        House house= houseService.getById(houseDetail.getHouseid());
        houseDetail.setHousename(house.getHousename());
        return new ResponseResult(200,"获取房屋详情成功",houseDetail);
    }

    @PostMapping("/addHouseDetail")
    public ResponseResult addHouseDetail(@RequestBody @Validated HouseDetail houseDetail){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("houseid",houseDetail.getHouseid());
        int total=houseDetailService.count(queryWrapper);
        if(total>0){
            return new ResponseResult(100,"该房屋详情已经存在了，请重新选择");
        }
        houseDetail.setCreatetime(new Date());
        boolean flag=houseDetailService.save(houseDetail);
        if (!flag){
            return new ResponseResult(200,"添加数据失败");
        }
        return new ResponseResult(200,"添加数据成功");
    }

    @PostMapping("/updateHouseDetail")
    public ResponseResult updateHouseDetail(@RequestBody @Validated HouseDetail houseDetail){
        HouseDetail houseDetail1=houseDetailService.getById(houseDetail.getId());
        if(houseDetail1.getHouseid()!=houseDetail.getHouseid()){
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("houseid",houseDetail.getHouseid());
            int total=houseDetailService.count(queryWrapper);
            if(total>0){
                return new ResponseResult(100,"已经存在了，修改失败，请选择房屋不存在摆设的数据");
            }
        }
        houseDetailService.updateById(houseDetail);
        return new ResponseResult(200,"修改数据成功");
    }

    @DeleteMapping("/deleteHouseDetail")
    public ResponseResult deleteHouseDetail(int id){
        houseDetailService.removeById(id);
        return new ResponseResult(200,"删除房屋数据成功");
    }


}
