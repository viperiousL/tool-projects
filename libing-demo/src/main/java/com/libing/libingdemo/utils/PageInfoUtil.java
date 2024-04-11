package com.libing.libingdemo.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @discription 处理业务逻辑数据后 手动分页
 * @author Edward.Xiang
 * @date 2023/2/9 15:01
 */
public class PageInfoUtil {

    public static <T> PageInfo<T> listToPageInfo(List<T> list , int pageNum , int pageSize) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * pageNum;
            if (toIndex > total) {
                toIndex = total;
            }
            int totalPage = total % pageSize == 0 ? (total/pageSize) : (total/pageSize)+1;
            if (totalPage < pageNum){
                list = new ArrayList<>();
            }else {
                list = list.subList(pageSize * (pageNum - 1), toIndex);
            }
        }else {
            list = pageNum == 1 ? list : new ArrayList<>();
        }
        Page<T> page = new Page<>(pageNum, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        return new PageInfo<>(page);
    }

}
