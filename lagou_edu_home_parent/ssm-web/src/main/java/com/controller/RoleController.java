package com.controller;

import com.domain.Menu;
import com.domain.ResponseResult;
import com.domain.Role;
import com.domain.RoleMenuVo;
import com.service.MenuService;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAllRole")
    public ResponseResult findAllUserByPage(@RequestBody Role role){
        List<Role> allRole = roleService.findAllRole(role);
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有角色成功",allRole);
        return responseResult;
    }


    @Autowired
    private MenuService menuService;
    //查询所有的父子菜单信息(分配菜单的第一个接口)
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid(){
        // -1表示查询所有的父级菜单
        List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

        Map<String,Object> map = new HashMap<>();
        map.put("parentMenuList",subMenuListByPid);

        ResponseResult result = new ResponseResult(true,200,"查询所有父子信息菜单成功",map);
        return result;
    }


    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId){
        List<Integer> list = roleService.findMenuByRoleId(roleId);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询角色关联的菜单信息成功", list);
        return responseResult;

    }


    @RequestMapping("/roleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo){
        roleService.roleContextMenu(roleMenuVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "为角色分配菜单成功", null);
        return responseResult;
    }


    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id){
        roleService.deleteRole(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "删除角色成功", null);
        return responseResult;
    }
}
