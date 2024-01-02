package com.libing.libingdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libing.libingdemo.entity.SysMenu;
import com.libing.libingdemo.vo.SysMenuVO;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author admin
 * @since 2023-12-25
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuVO> getMenuTree();

}
