package com.libing.libingdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libing.libingdemo.entity.SysMenu;
import com.libing.libingdemo.mapper.SysMenuMapper;
import com.libing.libingdemo.service.SysMenuService;
import com.libing.libingdemo.utils.ConvertTree;
import com.libing.libingdemo.vo.SysMenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-12-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenuVO> getMenuTree() {
        List<SysMenu> sysMenus = this.getBaseMapper().selectList(new QueryWrapper<>());
        List<SysMenuVO> sysMenuVOList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            sysMenuVOList.add(sysMenuVO);
        }
        List<SysMenuVO> convert = ConvertTree.convert(sysMenuVOList, "id", "pid", "id", "asc", 0L);

        return convert;
    }
}
