package com.controller;

import com.domain.Menu;
import com.domain.ResponseResult;
import com.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(){
        List<Menu> menuList = menuService.findAllMenu();
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有菜单信息成功",menuList);
        return responseResult;
    }


    //回显菜单信息
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id){
        if(id == -1){
            List<Menu> list = menuService.findSubMenuListByPid(-1);

            Map<String,Object> map = new HashMap();

            map.put("menuInfo",null);
            map.put("parentMenuList",list);

            ResponseResult result = new ResponseResult(true,200,"添加回显成功",map);
            return result;
        }else {
            Menu menu = menuService.findMenuById(id);

            List<Menu> list = menuService.findSubMenuListByPid(-1);

            Map<String,Object> map = new HashMap();

            map.put("menuInfo",menu);
            map.put("parentMenuList",list);
            ResponseResult result = new ResponseResult(true,200,"修改回显成功",map);
            return result;
        }
    }
}
