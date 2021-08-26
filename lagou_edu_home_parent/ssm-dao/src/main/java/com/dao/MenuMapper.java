package com.dao;

import com.domain.Menu;

import java.util.List;

public interface MenuMapper {
    //查询全部的父子菜单信息
    public List<Menu> findSubMenuListByPid(int pid);

    //查询所有菜单信息
    public List<Menu> findAllMenu();

    //根据id查询菜单信息
    public Menu findMenuById(Integer id);
}
