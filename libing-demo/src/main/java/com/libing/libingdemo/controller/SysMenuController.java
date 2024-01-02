package com.libing.libingdemo.controller;

import com.libing.libingdemo.service.SysMenuService;
import com.libing.libingdemo.vo.SysMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

//    public static void main(String[] args) {
//        SysMenuVO m1 = new SysMenuVO(1L, "0", "yiji");
//        SysMenuVO m2 = new SysMenuVO(2L, "1", "yiji");
//        SysMenuVO m3 = new SysMenuVO(3L, "1", "yiji");
//        SysMenuVO m4 = new SysMenuVO(4L, "", "yiji");
//        SysMenuVO m5 = new SysMenuVO(5L, "4", "yiji");
//        SysMenuVO m6 = new SysMenuVO(6L, "1", "yiji");
//        List<SysMenuVO> nodeMenus = Arrays.asList(m1, m2, m3, m4, m5, m6);
//        List<SysMenuVO> nodeMenus1 = ConvertTree.convert(nodeMenus, "id", "pid", "id", "desc", "0", "");
//        nodeMenus1.stream().forEach(SysMenuVO -> {
//            System.out.println(SysMenuVO.toString());
//        });
//
//
//
//    }

}
