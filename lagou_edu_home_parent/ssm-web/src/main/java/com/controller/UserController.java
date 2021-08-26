package com.controller;

import com.domain.ResponseResult;
import com.domain.Role;
import com.domain.User;
import com.domain.UserVo;
import com.github.pagehelper.PageInfo;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "分页多条件查询成功", pageInfo);
        return responseResult;
    }

    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(int id,String status){
        if("ENABLE".equalsIgnoreCase(status)){
            status = "DISABLE";
        }else{
            status = "ENABLE";
        }
        userService.updateUserStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true,200,"修改状态成功",status);
        return responseResult;
    }

    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        User user1 = userService.login(user);
        if(user1 != null){
            //保存用户id及access_token到session中
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id",user1.getId());

            Map<String,Object> map =new HashMap();
            map.put("access_token",access_token);
            map.put("user_id",user1.getId());
            ResponseResult responseResult = new ResponseResult(true, 200, "登录成功", map);
            return responseResult;
        }else {
            return new ResponseResult(true,400,"用户名密码错误",null);
        }
    }


    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id){
        List<Role> roleList = userService.findUserRelationRoleById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询成功", roleList);
        return responseResult;
    }



    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){

        userService.userContextRole(userVo);

        return new ResponseResult(true,200,"分配角色成功",null);
    }


    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){
        //获取请求头中的 token
        String token = request.getHeader("Authorization");

        //获取session中的access_token
        HttpSession session = request.getSession();
        String access_token = (String) session.getAttribute("access_token");

        if(token.equals(access_token)){
            Integer user_id =(Integer) session.getAttribute("user_id");
            ResponseResult responseResult = userService.getUserPermissions(user_id);
            return responseResult;
        }else{
            ResponseResult result = new ResponseResult(false,400,"获取失败","");
            return result;
        }
    }

}
