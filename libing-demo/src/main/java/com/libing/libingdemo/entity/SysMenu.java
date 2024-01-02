package com.libing.libingdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.libing.libingdemo.sys.TreePid;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author admin
 * @since 2023-12-25
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父id
     */
    @TreePid
    private Long pid;
}
