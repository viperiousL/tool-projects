package com.libing.libingdemo;

import com.libing.libingdemo.entity.SysMenu;

import java.lang.reflect.Field;

public class TreeMenu {
    public static void main(String[] args) {
        try {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(1000L);
            Class clazz = Class.forName("com.libing.libingdemo.entity.SysMenu");
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            Object o = id.get(sysMenu);
            System.out.println(o);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
