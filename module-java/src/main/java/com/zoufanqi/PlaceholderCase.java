package com.zoufanqi;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: ZOUFANQI
 * @create: 2022-08-29 12:53
 **/
public class PlaceholderCase {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.+?)}}");

    public static void main(String[] args) {
        /* Test Case 1 */
        String sourceText = "My name is {{ name }} and I am forever {{ age }}.";
        Map<String, Object> paramMap = new HashMap<String, Object>(4) {{
            this.put("name", "Bill");
            this.put("age", 21);
        }};
        System.out.println(replaceText(sourceText, paramMap));

        System.out.println("==================================================");

        /* Test Case 2 */
        sourceText = "Say hello to {{ name }}. He is {{ age }}.";
        paramMap = new HashMap<String, Object>(8) {{
            this.put("name", "Bill");
            this.put("age", 21);
            this.put("male", true);
        }};
        System.out.println(replaceText(sourceText, paramMap));

        System.out.println("==================================================");

        /* Test Case 3 */
        sourceText = "Tommy is a good friend of {{ name }}. He lives in {{ city }}.";
        paramMap = new HashMap<String, Object>(4) {{
            this.put("name", "Bill");
        }};
        System.out.println(replaceText(sourceText, paramMap));
    }

    /**
     * 替换占位符文本
     *
     * @param sourceText 原文本内容
     * @param paramMap   参数
     * @return 替换后的文本内容
     */
    public static String replaceText(String sourceText, Map<String, Object> paramMap) {
        if (sourceText == null || "".equals(sourceText.trim()) || paramMap == null || paramMap.isEmpty()) {
            return sourceText == null ? "" : sourceText;
        }
        final StringBuffer replacedTextBuilder = new StringBuffer(sourceText.length() * 2);
        final Matcher matcher = PLACEHOLDER_PATTERN.matcher(sourceText);
        while(matcher.find()) {
            // 获取参数名
            final String paramKey = matcher.group(1).trim();
            // 根据参数获取参数值
            final Object replaceVal = paramMap.get(paramKey);
            // 参数值为空则不替换
            matcher.appendReplacement(replacedTextBuilder, replaceVal == null ? matcher.group(0) : String.valueOf(replaceVal));
        }
        matcher.appendTail(replacedTextBuilder);
        return replacedTextBuilder.toString();
    }

}
