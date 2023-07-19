package com.zmn.biz.amislc.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import java.util.Arrays;

/**
 * 版本工具类
 *
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/6 16:16
 **/
public class VersionUtil {
    private static final String VERSION_PREFIX = "v";

    /**
     * 分隔版本
     *
     * @param version  版本
     * @param maxParts 最大片段长度
     * @return 版本片段
     * @author zoufanqi
     * @since 2023/7/6 16:20
     */
    public static Integer[] splitAndCheckVersionParts(String version, int maxParts) {
        // 前缀校验
        Asserts.check(
                StringUtils.isNotBlank(version) || (version = version.trim().toLowerCase()).startsWith(VERSION_PREFIX.toLowerCase()),
                "应用版本不符合规范：%s，参考：v1%s",
                version, StringUtils.repeat(".0", maxParts - 1)
        );

        // 样例：v1.0.0，剔掉v
        final String[] parts = version.substring(1).split("\\.");
        Asserts.check(
                parts.length <= maxParts,
                "应用版本不符合规范：%s，参考：v1%s", version,
                StringUtils.repeat(".0", maxParts - 1)
        );

        // 字符串转数字
        final Integer[] results = new Integer[parts.length];
        try {
            for (int i = 0, len = parts.length; i < len; i++) {
                results[i] = Integer.parseInt(parts[i]);
            }
        } catch (Exception e) {
            Asserts.check(
                    true,
                    "应用版本不符合规范：%s，参考：v1%s",
                    version, StringUtils.repeat(".0", maxParts - 1)
            );
        }
        return results;
    }

    /**
     * 组合片段成版本
     *
     * @param parts 片段
     * @return 版本
     * @author zoufanqi
     * @since 2023/7/6 16:23
     */
    public static String combineVersion(Integer[] parts) {
        return VERSION_PREFIX + Arrays.stream(parts).reduce(
                new StringBuilder(parts.length * 4),
                (b, pv) -> b.append(".").append(pv),
                (a, b) -> null
        ).substring(1);
    }

    /**
     * 新版本是否更高
     *
     * @param baseVersion 当前稳定版本
     * @param newVersion        新版本
     * @param maxParts          最大片段长度
     * @return 是否合法
     * @author zoufanqi
     * @since 2023/7/6 16:24
     */
    public static boolean isNewVersionHigher(String baseVersion, String newVersion, int maxParts) {
        // 当前已发布稳定版
        final Integer[] currVersionParts = splitAndCheckVersionParts(baseVersion, maxParts);
        // 创建新迭代版本
        final Integer[] nextVersionParts = splitAndCheckVersionParts(newVersion, maxParts);

        // 比对稳定版与新版本（新版本需大于稳定版）
        final int maxCurrIdx = currVersionParts.length - 1;
        final int maxNextIdx = nextVersionParts.length - 1;
        final int maxIdx = Math.max(currVersionParts.length, nextVersionParts.length);
        final StringBuilder currBuilder = new StringBuilder(maxIdx * 4);
        final StringBuilder nextBuilder = new StringBuilder(maxIdx * 4);
        for (int i = 0; i < maxIdx; i++) {
            final int curr = maxCurrIdx >= i ? currVersionParts[i] : 0;
            final int next = maxNextIdx >= i ? nextVersionParts[i] : 0;
            if (curr > next) {
                return false;
            }
            currBuilder.append(curr);
            nextBuilder.append(next);
        }

        // 两版本是否一致
        return !currBuilder.toString().contentEquals(nextBuilder);
    }

}
