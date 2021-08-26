package com.dao;

import com.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //用户分页&多条件查询
    public List<User> findAllUserByPage(UserVo userVo);

    //修改用户状态
    public void updateUserStatus(@Param("id") int id,@Param("status") String status);

    //用户登录
    public User login(User user);

    //根据用户id清空中间表
    public void deleteUserContextRole(Integer userId);

    //分配角色
    public void userContextRole(User_Role_relation user_role_relation);

    /*动态菜单*/

    //1.根据用户id查询关联的角色信息 一个用户可以有多个角色
    public List<Role> findUserRelationRoleById(Integer id);

    //2.根据角色id,查询角色所拥有的父级菜单
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);

    //3.根据父菜单id查询子菜单信息
    public List<Menu> findSubMenuByPid(Integer pid);

    //4.获取用户所用的资源权限信息
    public List<Resource> findResourceByRoleId(List<Integer> ids);
}
