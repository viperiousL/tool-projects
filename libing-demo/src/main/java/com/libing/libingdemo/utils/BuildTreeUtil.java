package com.libing.libingdemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 构建树工具类（反射）
 */
@Slf4j
public class BuildTreeUtil {

    /**
     * 构建树
     *
     * @param nodes 所有数据
     * @param <T>
     * @return
     */
    public static <T> T buildTree(List<T> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return null;
        }
        //根节点
        T root = null;
        try {
            //获取节点class对象
            Class<?> clazz = nodes.get(0).getClass();
            //获取对象所有属性
            Field[] fields = clazz.getDeclaredFields();
            Field childrenField = null;
            Field idField = null;
            Field parentIdField = null;
            //1.获取 T List<T> 类型属性字段对象childrenField
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getDeclaredAnnotation(TreeId.class) != null) {
                    //获取对象属性id
                    idField = field;
                } else if (field.getDeclaredAnnotation(TreeParentId.class) != null) {
                    //获取对象属性 parentId
                    parentIdField = field;
//                } else if (field.getType().equals(List.class)) {
                } else if (field.getDeclaredAnnotation(TreeChildren.class) != null) {
                    Type genericType = field.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        // 得到泛型里的class类型对象
                        Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
                        if (clazz.equals(actualTypeArgument)) {
                            //确定 T类型对象属性必须含有 List<T> 类型属性
                            childrenField = field;
                        }
                    }
                }
                if (childrenField != null && idField != null && parentIdField != null) {
                    //满足树构建条件， 结束循环
                    break;
                }
            }
            //三者缺一不可
            if (childrenField == null || idField == null || parentIdField == null) {
                //不满足树构建条件
                return null;
            }
            root = doBuildTree(nodes, childrenField, idField, parentIdField);

        } catch (Exception e) {
            log.error("构建树异常：{}", e);
        }
        return root;
    }

    /**
     * 构建树
     *
     * @param nodes
     * @param childrenField
     * @param idField
     * @param parentIdField
     * @param <T>
     * @return
     */
    public static <T> T doBuildTree(List<T> nodes, Field childrenField, Field idField, Field parentIdField) {
        //根节点
        T root = null;
        try {
            Map<Object, T> map = new HashMap<>(nodes.size());
            //将节点与节点id对应起来
            for (T t : nodes) {
                map.put(idField.get(t), t);
            }
            for (T node : nodes) {
                //获取父节点
                T parentNode = map.get(parentIdField.get(node));
                //如果parentNode==null，确定当前node为一级节点
                if (Objects.isNull(parentNode)) {
                    root = node;
                } else {
                    //获取父节点对象的子节点对象属性
                    List<T> list = (List<T>) childrenField.get(parentNode);
                    if (Objects.isNull(list)) {
                        list = new ArrayList<>();
                        childrenField.set(parentNode, list);
                    }
                    //添加子节点
                    list.add(node);
                }
            }
        } catch (Exception e) {
            log.error("构建树异常：{}", e);
        }
        return root;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TreeId {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TreeParentId {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TreeChildren {
    }
}
