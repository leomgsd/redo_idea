package com.service;

import com.domain.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> findSubMenuListByPid(int pid);

    public List<Menu> findAllMenu();

    public Menu findMenuById(Integer id);
}
