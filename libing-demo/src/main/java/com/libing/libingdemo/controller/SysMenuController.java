package com.libing.libingdemo.controller;

import com.libing.libingdemo.service.SysMenuService;
import com.libing.libingdemo.utils.BuildTreeUtil;
import com.libing.libingdemo.utils.ConvertTree;
import com.libing.libingdemo.vo.SysMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-12-25
 */
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/getMenuTree")
    @ResponseBody
    public List<SysMenuVO> getMenuTree() {
        return sysMenuService.getMenuTree();
    }

    public static void main(String[] args) {
        SysMenuVO m1 = new SysMenuVO(1L, null, "yiji");
        SysMenuVO m2 = new SysMenuVO(2L, 1L, "yiji");
        SysMenuVO m3 = new SysMenuVO(3L, 1L, "yiji");
        SysMenuVO m4 = new SysMenuVO(4L, 2L, "yiji");
        SysMenuVO m5 = new SysMenuVO(5L, 4L, "yiji");
        SysMenuVO m6 = new SysMenuVO(6L, 1L, "yiji");
        //第一种，结果为一个数组，可排序，可自定义顶级菜单父id值
        List<SysMenuVO> nodeMenus = Arrays.asList(m1, m2, m3, m4, m5, m6);
        List<SysMenuVO> nodeMenus1 = ConvertTree.convert(nodeMenus, "id", "pid", "id", "desc", "0", null);
        nodeMenus1.stream().forEach(SysMenuVO -> System.out.println(SysMenuVO.toString()));
        //第二种，结果为一个对象，顶级菜单的父id为null，只能修改方法，否则无法自定义设置
        SysMenuVO nodeMenus2 = BuildTreeUtil.buildTree(nodeMenus);
        System.out.println(nodeMenus2);

    }

}
