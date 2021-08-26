package com.service;

import com.domain.ResponseResult;
import com.domain.Role;
import com.domain.User;
import com.domain.UserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    public PageInfo findAllUserByPage(UserVo userVo);

    public void updateUserStatus(int id, String status);

    public User login(User user) throws Exception;

    public List<Role> findUserRelationRoleById(Integer id);

    public void userContextRole(UserVo userVo);

    //根据用户权限 获取动态菜单
    public ResponseResult getUserPermissions(Integer userId);


}
