package com.zmn.biz.amislc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zmn.biz.amislc.common.basedxo.dio.BaseAddDIO;
import com.zmn.biz.amislc.common.basedxo.dio.BaseModifyDIO;
import com.zmn.biz.amislc.common.utils.CommonUtil;
import com.zmn.common.dto2.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 跟业务相关的通用工具类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/3/31 17:24
 **/
@Slf4j
public class ServiceUtil {

    private final static CopyOptions COPY_OPTIONS = CopyOptions.create().ignoreNullValue().ignoreError();

    /**
     * 分批请求工具函数
     *
     * @param maxBatchNum 每批次传参最大数量
     * @param sources     参数集
     * @param handler     处理函数
     * @param <A>         参数类型
     * @param <B>         返回数据类型
     * @return 返回数据
     * @author zoufanqi
     * @since 2023/3/31 17:43
     */
    public static <A, B> List<B> pageRequestHandle(int maxBatchNum, List<A> sources, Function<List<A>, List<B>> handler) {
        final List<B> results = new ArrayList<>(sources.size());
        final int maxSourceIdx = sources.size();
        int sIdx = 0;
        int eIdx;
        do {
            eIdx = Math.min(sIdx + maxBatchNum, maxSourceIdx);
            final List<B> dataList = handler.apply(sources.subList(sIdx, eIdx));
            if (CollectionUtil.isNotEmpty(dataList)) {
                results.addAll(dataList);
            }
        } while ((sIdx = eIdx) < maxSourceIdx);
        return results;
    }

    /**
     * 与新值比对，如果不一致则更新
     *
     * @param oldValGetter 旧值对象getter
     * @param oldValSetter 旧值对象setter
     * @param newVal       新值
     * @param <T>          值类型
     * @return 是否一致
     * @author zoufanqi
     * @since 2023/6/3 15:51
     */
    public static <T> boolean updateIfNotEqual(Supplier<T> oldValGetter, Consumer<T> oldValSetter, T newVal) {
        final T oldVal = oldValGetter.get();
        final boolean neq = !Objects.equals(oldVal, Objects.isNull(newVal) ? oldVal : newVal);
        if (neq) {
            oldValSetter.accept(newVal);
        }
        return neq;
    }

    public static <S, T> T copyProperties(S source, T target) {
        return copyProperties(source, target, null);
    }

    /**
     * 拷贝对象属性
     *
     * @param source            源对象
     * @param target            目标对象
     * @param afterCopyConsumer 拷贝后的操作（有些字段会不一致）
     * @param <T>               目标类型
     * @return 目标对象
     * @author zoufanqi
     * @since 2023/6/6 11:51
     */
    public static <S, T> T copyProperties(S source, T target, BiConsumer<S, T> afterCopyConsumer) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return target;
        }
        BeanUtil.copyProperties(source, target, COPY_OPTIONS);
        if (Objects.nonNull(afterCopyConsumer)) {
            afterCopyConsumer.accept(source, target);
        }

        try {
            // 填充entity通用属性（creater/creatTime/updater/updateTime）
            setCreateUpdateProperties(source, target);
        } catch (Exception e) {
            log.warn("为entity设置通用属性时抛出异常", e);
        }

        return target;
    }

    private static <S, T> void setCreateUpdateProperties(S source, T entity) throws IllegalAccessException, InvocationTargetException {
        // 如果source为DIO，自动填充操作人和时间
        final String operator;
        final boolean createTrueUpdateFalse;
        if (source instanceof BaseAddDIO) {
            operator = ((BaseAddDIO) source).getOperator();
            createTrueUpdateFalse = true;
        } else if (source instanceof BaseModifyDIO) {
            operator = ((BaseModifyDIO) source).getOperator();
            createTrueUpdateFalse = false;
        } else {
            operator = null;
            createTrueUpdateFalse = false;
        }
        if (StrUtil.isBlank(operator)) {
            return;
        }

        final Class<?> entityCls = entity.getClass();
        // 填充create
        if (createTrueUpdateFalse) {
            setCreateUpdateProperties(entity, entityCls, operator, "setCreater", "setCreateTime");
        }
        // 填充update
        setCreateUpdateProperties(entity, entityCls, operator, "setUpdater", "setUpdateTime");
    }

    private static <T> void setCreateUpdateProperties(T entity, Class<?> entityCls, String operator, String operatorMethodName, String timeMethodName)
            throws IllegalAccessException, InvocationTargetException {
        // 获取entity方法
        final Method cm = CommonUtil.getPublicMethod(entityCls, operatorMethodName, String.class);
        if (Objects.isNull(cm)) {
            return;
        }
        // 注入值
        cm.invoke(entity, operator);
        final Method ctm = CommonUtil.getPublicMethod(entityCls, timeMethodName, LocalDateTime.class);
        if (Objects.isNull(ctm)) {
            return;
        }
        ctm.invoke(entity, LocalDateTime.now());
    }


    /**
     * 批量拷贝对象属性
     *
     * @param sources   源对象
     * @param targetCls 目标对象cls
     * @param <T>       目标类型
     * @return 目标对象
     * @author zoufanqi
     * @since 2023/6/6 11:51
     */
    public static <S, T> List<T> copyPropertiesBatch(List<S> sources, Class<T> targetCls) {
        return copyBatchProperties(sources, targetCls, null);
    }

    /**
     * 批量拷贝对象属性
     *
     * @param sources   源对象
     * @param targetCls 目标对象cls
     * @param <T>       目标类型
     * @return 目标对象
     * @author zoufanqi
     * @since 2023/6/6 11:51
     */
    public static <S, T> List<T> copyPropertiesBatch(List<S> sources, Class<T> targetCls, BiConsumer<S, T> targetConsumer) {
        if (CollectionUtil.isEmpty(sources)) {
            return Collections.emptyList();
        }
        return sources.stream()
                .map(o -> {
                    final T t = BeanUtils.instantiateClass(targetCls);
                    ServiceUtil.copyProperties(o, t);
                    if (Objects.nonNull(targetConsumer)) {
                        targetConsumer.accept(o, t);
                    }
                    return t;
                })
                .collect(Collectors.toList());

    }

    /**
     * long to int
     *
     * @param num num
     * @return int
     * @author zoufanqi
     * @since 2023/6/10 16:31
     */
    public static Integer toInteger(Long num) {
        if (Objects.isNull(num)) {
            return null;
        }
        return Integer.parseInt(num.toString());
    }

    /**
     * long 转 int
     *
     * @param num           num
     * @param defaultIfNull 为空默认
     * @return int
     * @author zoufanqi
     * @since 2023/7/5 09:49
     */
    public static int toInteger(Long num, int defaultIfNull) {
        if (Objects.isNull(num)) {
            return defaultIfNull;
        }
        return Integer.parseInt(num.toString());
    }

    /**
     * 获取集合第一条数据
     *
     * @param list list
     * @param <T>  t
     * @return top1
     * @author zoufanqi
     * @since 2023/7/5 09:49
     */
    public static <T> T topOne(List<T> list) {
        return Objects.isNull(list) || list.isEmpty() ? null : list.get(0);
    }

    public static <T, R extends Serializable> PageResult<R> convertPage(Page<T> page, Class<R> cls) {
        return convertPage(page, cls, null);
    }

    /**
     * mybatis分页数据转换
     *
     * @param page mybatis分页数据
     * @param cls  数据类型
     * @param <T>  入
     * @param <R>  出
     * @return 自定义分页数据
     * @author zoufanqi
     * @since 2023/7/5 09:57
     */
    public static <T, R extends Serializable> PageResult<R> convertPage(Page<T> page, Class<R> cls, BiConsumer<T, R> afterCopyConsumer) {
        if (Objects.isNull(page) || Objects.isNull(cls)) {
            return new PageResult<>(Collections.emptyList(), 0, 0, 0);
        }
        final List<R> results = Optional.ofNullable(page.getRecords()).orElse(Collections.emptyList()).stream()
                .map(d -> ServiceUtil.copyProperties(d, ReflectUtil.newInstance(cls), afterCopyConsumer))
                .collect(Collectors.toList());
        return new PageResult<>(
                results,
                toInteger(page.getCurrent()),
                toInteger(page.getSize()),
                toInteger(page.getTotal())
        );
    }

    public static String cutString(String str, int maxLen) {
        return cutString(str, maxLen, null);
    }

    /**
     * 截串
     *
     * @param str    字符串
     * @param maxLen 最大长度
     * @param suffix 截串后追加后缀，如：xxx...
     * @return 截取后的字符串
     * @author zoufanqi
     * @since 2023/7/24 09:16
     */
    public static String cutString(String str, int maxLen, String suffix) {
        if (StrUtil.isBlank(str)) {
            return str;
        }
        if (maxLen <= 0) {
            return "";
        }
        if (StrUtil.isBlank(suffix)) {
            return str.length() > maxLen ? str.substring(0, maxLen) : str;
        } else {
            return str.length() > maxLen ? str.substring(0, maxLen - suffix.length()) + suffix : str;
        }
    }
}
