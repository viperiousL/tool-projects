package com.libing.libingdemo.utils;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode {

    /**
     * id: String, Integer, Long ....
     * 此处注解为自定义注解，为构建树提供识别
     */
    @BuildTreeUtil.TreeId
    private Long id;

    private String name;

    /**
     * 功能菜单父节点(id)
     */
    @BuildTreeUtil.TreeParentId
    private Long pid;

    /**
     * 子节点
     */
    private List<TreeNode> children;

}
