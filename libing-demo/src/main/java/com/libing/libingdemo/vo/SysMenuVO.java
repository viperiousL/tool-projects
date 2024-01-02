package com.libing.libingdemo.vo;

import com.libing.libingdemo.utils.BuildTreeUtil;
import lombok.Data;

import java.util.ArrayList;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author admin
 * @since 2023-12-25
 */
@Data
public class SysMenuVO {

    @BuildTreeUtil.TreeId
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父id
     */
    @BuildTreeUtil.TreeParentId
    private Long pid;

    @BuildTreeUtil.TreeChildren
    private ArrayList<SysMenuVO> cList;


    public SysMenuVO() {
    }

    public SysMenuVO(Long id, Long pid, String name, ArrayList<SysMenuVO> cList) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.cList = cList;
    }

    public SysMenuVO(Long id, Long pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

}
