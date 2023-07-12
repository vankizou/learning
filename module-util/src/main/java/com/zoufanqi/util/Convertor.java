package com.zoufanqi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/6/17 14:10
 **/
@Slf4j
public class Convertor {

    /**
     * list转树, 元素结构：
     * <p>
     * public class TreeEle {
     * private List<?> children;
     * ...
     * }
     *
     * @param dataList   元素集合
     * @param idSupplier id提供器
     * @param <T>        任意bean
     * @return 树
     * @author zoufanqi
     * @since 2023/7/12 10:13
     */
    public static <T> List<T> convertTree(List<T> dataList, Function<T, TreeConvertInfo> idSupplier) {
        if (Objects.isNull(dataList) || dataList.isEmpty() || Objects.isNull(idSupplier)) {
            return Collections.emptyList();
        }
        // 获取children方法
        final Class<?> cls = dataList.get(0).getClass();
        final Method childrenGetter = CommonUtil.getPublicMethod(cls, "getChildren");
        final Method childrenSetter = CommonUtil.getPublicMethod(cls, "setChildren", List.class);

        final Map<Integer, T> keyToTreeCacheMap = new HashMap<>(dataList.size() * 2);
        final List<T> roots = new ArrayList<>();
        try {
            for (T t : dataList) {
                final TreeConvertInfo treeConvertInfo = idSupplier.apply(t);
                final Integer parentId = treeConvertInfo.getParentId();
                final Integer selfId = treeConvertInfo.getSelfId();
                if (Objects.isNull(parentId) || Objects.isNull(selfId)) {
                    continue;
                }
                final T parent = keyToTreeCacheMap.get(parentId);
                if (Objects.isNull(parent)) {
                    roots.add(t);
                } else {
                    Collection<T> children = (Collection<T>) childrenGetter.invoke(parent);
                    if (Objects.isNull(children)) {
                        childrenSetter.invoke(parent, (children = new ArrayList<>(dataList.size() / 2)));
                    }
                    children.add(t);
                }
                keyToTreeCacheMap.put(selfId, t);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return roots;
    }

    @Getter
    @AllArgsConstructor
    public static class TreeConvertInfo {
        private final Integer parentId;

        private final Integer selfId;
    }

}
