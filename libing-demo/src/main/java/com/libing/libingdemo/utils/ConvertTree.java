package com.libing.libingdemo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class ConvertTree<T> {

    /**
     * 根据反射实现的树形结构
     *
     * @param list             需要排序的对象
     * @param idFileName       id对应的字段名称
     * @param parentIdFileName 父id对应的字段名称
     * @param orderFileName    排序的字段名称
     * @param asc              排序方式：asc或desc
     * @param parentValue      父级字段对应的值，支持多个，比如0，空，-1等等，格式要一致，比如0或"0"
     * @param <T>
     * @return
     */
    public static <T> List<T> convert(List<T> list, String idFileName, String parentIdFileName, String orderFileName, String asc, Object... parentValue) {
        List<T> result = list.stream().filter(parent -> {
            //获取parentId的值
            Object fieldValue = getFieldValue(parent, parentIdFileName);
            return Arrays.stream(parentValue).collect(Collectors.toList()).contains(fieldValue);
        }).map(child -> {
            for (Field field : child.getClass().getDeclaredFields()) {
                if (field.getGenericType().getTypeName().contains("List")) {
                    try {
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        Object fieldValue = getFieldValue(child, idFileName);
                        field.set(child, convert(list, idFileName, parentIdFileName, orderFileName, asc, fieldValue));
                        field.setAccessible(accessible);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return child;
        }).collect(Collectors.toList());

        //判断是否支持排序
        boolean canSort = false;
        boolean stringType = true;//判断排序类型，字符串或其他类型
        if (result != null && result.size() > 0) {
            try {
                Field declaredField = result.get(0).getClass().getDeclaredField(orderFileName);
                if (declaredField != null) {
                    canSort = true;
                    Class<?> type = declaredField.getType();
                    if (type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class)) {
                        stringType = false;
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (canSort) {
            //获取方法名
            String newStr = orderFileName.substring(0, 1).toUpperCase() + orderFileName.replaceFirst("\\w", "");
            String methodName = "get" + newStr;

            boolean finalStringType = stringType;
            Collections.sort(result, new Comparator<T>() {
                @Override
                public int compare(T obj1, T obj2) {
                    int retVal = 0;
                    try {
                        Method method1 = ((T) obj1).getClass().getMethod(methodName, null);
                        Method method2 = ((T) obj2).getClass().getMethod(methodName, null);
                        // 倒序
                        if ("desc".equalsIgnoreCase(asc)) {
                            // 是否按字符串比较
                            if (finalStringType) {
                                // 字符串按 中英文 排序方式
                                Comparator<Object> chinaSort = Collator.getInstance(Locale.CHINA);
                                retVal = ((Collator) chinaSort).compare(method2.invoke(((T) obj1), null).toString(), method1.invoke(((T) obj2), null).toString());
                            } else {
                                retVal = Double.valueOf(method2.invoke(((T) obj2), null).toString()).compareTo(Double.valueOf(method1.invoke(((T) obj1), null).toString()));
                            }
                        }
                        // 正序
                        if ("asc".equalsIgnoreCase(asc)) {
                            if (finalStringType) {
                                // 字符串按 中英文 排序方式
                                Comparator<Object> chinaSort = Collator.getInstance(Locale.CHINA);
                                retVal = ((Collator) chinaSort).compare(method1.invoke(((T) obj1), null).toString(), method2.invoke(((T) obj2), null).toString());
                            } else {
                                retVal = Double.valueOf(method1.invoke(((T) obj1), null).toString()).compareTo(Double.valueOf(method2.invoke(((T) obj2), null).toString()));
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return retVal;
                }
            });
        }
        return result;
    }

    /**
     * 获取根据属性名称获取实体类里的值
     *
     * @param entity
     * @param fieldName
     * @param <T>
     * @return
     */
    private static <T> Object getFieldValue(T entity, String fieldName) {
        Object result = null;
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            result = field.get(entity);
            field.setAccessible(accessible);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();

        }
        return result;
    }

//    public static <T> List<T> convert3(List<T> list, String idFileName, String parentIdFileName, String childrenFieldName, String orderFileName, String asc, Object... parentValue) {
//        List<T> result = new ArrayList<>();//所有的父节点
//        List<T> tempList = new ArrayList<>();//所有的子节点
//        // 用于保存当前 id 索引的实体类
//        Map<Object, T> idMaps = new HashMap<>();
//        for (T entity : list) {
//            Object iV = getFieldValue(entity, idFileName);
//            Object pV = getFieldValue(entity, parentIdFileName);
//            idMaps.put(iV, entity);
//            if (Arrays.stream(parentValue).collect(Collectors.toList()).contains(pV)) {
//                result.add(entity);
//            } else {
//                tempList.add(entity);
//            }
//        }
//        for (T entity : tempList) {
//            Object pV = getFieldValue(entity, parentIdFileName);
//            // 根据父id获取实体类
//            T parentEntity = idMaps.get(pV);
//            if (parentEntity != null) {
//                // 父组件判断是否存在children, 不存在新增, 存在则直接加入
//                setChildrenValue(childrenFieldName, entity, parentEntity);
//            }
//        }
//
//        //判断是否支持排序
//        boolean canSort = false;
//        boolean stringType = true;//判断排序类型，字符串或其他类型
//        if (result != null && result.size() > 0) {
//            try {
//                Field declaredField = result.get(0).getClass().getDeclaredField(orderFileName);
//                if (declaredField != null) {
//                    canSort = true;
//                    Class<?> type = declaredField.getType();
//                    if (type.equals(Integer.class) || type.equals(Float.class) || type.equals(Double.class) || type.equals(BigDecimal.class)) {
//                        stringType = false;
//                    }
//                }
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }
//        if (canSort) {
//            //获取方法名
//            String newStr = orderFileName.substring(0, 1).toUpperCase() + orderFileName.replaceFirst("\\w", "");
//            String methodName = "get" + newStr;
//
//            boolean finalStringType = stringType;
//            Collections.sort(result, new Comparator<T>() {
//                @Override
//                public int compare(T obj1, T obj2) {
//                    int retVal = 0;
//                    try {
//                        Method method1 = ((T) obj1).getClass().getMethod(methodName, null);
//                        Method method2 = ((T) obj2).getClass().getMethod(methodName, null);
//                        // 倒序
//                        if ("desc".equalsIgnoreCase(asc)) {
//                            // 是否按字符串比较
//                            if (finalStringType) {
//                                // 字符串按 中英文 排序方式
//                                Comparator<Object> chinaSort = Collator.getInstance(Locale.CHINA);
//                                retVal = ((Collator) chinaSort).compare(method2.invoke(((T) obj1), null).toString(), method1.invoke(((T) obj2), null).toString());
//                            } else {
//                                retVal = Double.valueOf(method2.invoke(((T) obj2), null).toString()).compareTo(Double.valueOf(method1.invoke(((T) obj1), null).toString()));
//                            }
//                        }
//                        // 正序
//                        if ("asc".equalsIgnoreCase(asc)) {
//                            if (finalStringType) {
//                                // 字符串按 中英文 排序方式
//                                Comparator<Object> chinaSort = Collator.getInstance(Locale.CHINA);
//                                retVal = ((Collator) chinaSort).compare(method1.invoke(((T) obj1), null).toString(), method2.invoke(((T) obj2), null).toString());
//                            } else {
//                                retVal = Double.valueOf(method1.invoke(((T) obj1), null).toString()).compareTo(Double.valueOf(method2.invoke(((T) obj2), null).toString()));
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return retVal;
//                }
//            });
//        }
//
//        return result;
//    }

//    private static <T> void setChildrenValue(String childrenFieldName, T entity, T parentEntity) {
//        Object children = getFieldValue(parentEntity, childrenFieldName);
//        List<T> childrenList;
//        if (children == null) {
//            childrenList = new ArrayList<>();
//            childrenList.add(entity);
//            setFieldValue(parentEntity, childrenFieldName, childrenList);
//        } else {
//            List<T> childrenReal = (List<T>) children;
//            childrenReal.add(entity);
//        }
//    }

//    private static <T> void setFieldValue(T entity, String fieldName, Object value) {
//        try {
//            Field field = entity.getClass().getDeclaredField(fieldName);
//            boolean accessible = field.isAccessible();
//            field.setAccessible(true);
//            field.set(entity, value);
//            field.setAccessible(accessible);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//    }

}
