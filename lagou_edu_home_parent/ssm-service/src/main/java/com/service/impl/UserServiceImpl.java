package com.service.impl;

import com.dao.UserMapper;
import com.domain.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.UserService;
import com.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> userList = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        userMapper.updateUserStatus(id,status);
    }


    @Override
    public User login(User user) throws Exception {
        User user2 = userMapper.login(user);
        if(user2 != null && Md5.verify(user.getPassword(),"lagou",user2.getPassword())){
            return user2;
        }else{
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> list = userMapper.findUserRelationRoleById(id);
        return list;
    }

    @Override
    public void userContextRole(UserVo userVo) {
        userMapper.deleteUserContextRole(userVo.getUserId());
        for (Integer rid : userVo.getRoleIdList()) {
            User_Role_relation user_role_relation = new User_Role_relation();

            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(rid);
            user_role_relation.setCreatedTime(new Date());
            user_role_relation.setUpdatedTime(new Date());
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }

    }

    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        //1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        //2.获取角色ID,保存到 list
        ArrayList<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }

        //3.根据角色id查询 父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);

        //4.封装父菜单下的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenuByPid = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenuByPid);
        }

        //5.获取资源权限
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        //6.封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu); //menuList: 菜单权限数据
        map.put("resourceList",resourceList);//resourceList: 资源权限数据
        ResponseResult result = new ResponseResult(true,200,"响应成功",map);
        return result;
    }
}
