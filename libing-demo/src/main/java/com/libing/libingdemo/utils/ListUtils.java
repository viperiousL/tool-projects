package com.libing.libingdemo.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    /**
     * 对集合按照指定长度分段，每一个段为单独的集合，返回这个集合的列表
     *
     * <p>
     * 需要特别注意的是，此方法调用{@link List#subList(int, int)}切分List，
     * 此方法返回的是原List的视图，也就是说原List有变更，切分后的结果也会变更。
     * </p>
     *
     * @param <T>  集合元素类型
     * @param list 列表
     * @param size 每个段的长度
     * @return 分段列表
     * @since 5.4.5
     */
    public static <T> List<List<T>> split(List<T> list, int size) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        final int listSize = list.size();
        final List<List<T>> result = new ArrayList<>(listSize / size + 1);
        int offset = 0;
        for (int toIdx = size; toIdx <= listSize; offset = toIdx, toIdx += size) {
            result.add(list.subList(offset, toIdx));
        }
        if (offset < listSize) {
            result.add(list.subList(offset, listSize));
        }
        return result;
    }
}
