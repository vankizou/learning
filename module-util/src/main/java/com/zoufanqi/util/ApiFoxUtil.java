package com.zoufanqi.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/5 10:53
 **/
@Slf4j
public class ApiFoxUtil {
    /**
     * 递归对象
     */
    private final static Set<String> OBJ_RECIRCLE_CACHE_SET = new HashSet<>(8);

    private final static List<String> IGNORE_FIELDS = Arrays.asList("serialVersionUID", "errors", "SUCCESS");

    private final static List<Class<?>> NO_ANNO_WHITE_CLS_LIST = null;//Arrays.asList(null, AMISResponseDTO.class, PageResult.class, AMISListData.class);

//    public static String toApiFoxAmisResponsePage(Class<?> beanCls) {
//        final ApiFoxSchema amisSchema = toApiFoxObjSchema(AMISResponseDTO.class);
//
//        final ApiFoxSchema pageSchema = toApiFoxObjSchema(PageResult.class);
//        pageSchema.getProperties().put("items", toApiFoxListSchema(beanCls));
//
//        amisSchema.getProperties().put("data", pageSchema);
//
//        return JSON.toJSONString(amisSchema);
//    }
//
//    public static String toApiFoxAmisResponseObj(Class<?> beanCls) {
//        final ApiFoxSchema amisSchema = toApiFoxObjSchema(AMISResponseDTO.class);
//        amisSchema.getProperties().put("data", toApiFoxObjSchema(beanCls));
//        amisSchema.getProperties().get("status").getMock().put("mock", "0");
//        amisSchema.getProperties().get("msg").getMock().put("mock", "success");
//        return JSON.toJSONString(amisSchema);
//    }
//
//    public static String toApiFoxAmisResponseList(Class<?> beanCls) {
//        final ApiFoxSchema amisSchema = toApiFoxObjSchema(AMISResponseDTO.class);
//        amisSchema.getProperties().put("data", toApiFoxListSchema(beanCls));
//        amisSchema.getProperties().get("status").getMock().put("mock", "0");
//        amisSchema.getProperties().get("msg").getMock().put("mock", "success");
//        return JSON.toJSONString(amisSchema);
//    }

    public static String toApiFoxObj(Class<?> beanCls) {
        final ApiFoxSchema schema = toApiFoxObjSchema(beanCls);
        schema.setTitle("对象");
        return JSON.toJSONString(schema);
    }

    public static String toApiFoxList(Class<?> beanCls) {
        final ApiFoxSchema schema = toApiFoxListSchema(beanCls);
        schema.setTitle("集合");
        return JSON.toJSONString(schema);
    }

    private static ApiFoxSchema toApiFoxListSchema(Class<?> beanCls) {
        final ApiFoxSchema schema = new ApiFoxSchema();
        schema.setType("array");
        schema.setItems(toApiFoxObjSchema(beanCls));
        return schema;
    }

    private static ApiFoxSchema toApiFoxObjSchema(Class<?> beanCls) {
        OBJ_RECIRCLE_CACHE_SET.clear();
        OBJ_RECIRCLE_CACHE_SET.add(beanCls.getName());
        return buildObjSchema(new GenericInfo(null, beanCls), 0);
    }

    private static ApiFoxSchema buildObjSchema(GenericInfo parentInfo, int level) {
        level++;

        final ApiFoxSchema schema = new ApiFoxSchema();
        schema.setType("object");
        schema.setProperties(new LinkedHashMap<>(16));

        // 父schema的一些信息，如：标题等
        if (Objects.nonNull(parentInfo.getField())) {
            final ApiModelProperty anno = parentInfo.getField().getAnnotation(ApiModelProperty.class);
            if (Objects.nonNull(anno)) {
                schema.setTitle(anno.value());
                schema.setDescription(anno.notes());
            }
        }

        // 处理cls中的属性
        final List<Field> fields = getAllFields(parentInfo.getFieldCls());
        for (Field f : fields) {
            // 黑名单字段
            if (IGNORE_FIELDS.contains(f.getName())) {
                continue;
            }

            // 只处理有 @ApiModelProperty 注解的字段
            final ApiModelProperty anno = f.getAnnotation(ApiModelProperty.class);
            if (Objects.isNull(anno)) {
                if (!NO_ANNO_WHITE_CLS_LIST.contains(parentInfo.getFieldCls())) {
                    continue;
                }
            } else if (anno.hidden()) {
                continue;
            }
            // 必填字段
            else if (anno.required()) {
                schema.getRequired().add(f.getName());
            }

            // 枚举定义的类型
            schema.getProperties().put(
                    f.getName(),
                    routeAndBuildSchema(
                            parentInfo,
                            GenericInfo.buildGenericInfo(f),
                            level
                    )
            );
        }

        return schema;
    }

    private static ApiFoxSchema routeAndBuildSchema(GenericInfo parentInfo, GenericInfo currInfo, int level) {
        if (Objects.isNull(parentInfo) || Objects.isNull(currInfo)) {
            return null;
        }

        // 处理字段
        for (ApiFoxTypeEnum type : ApiFoxTypeEnum.values()) {
            // 枚举匹配
            if (!type.matches.apply(currInfo.fieldCls)) {
                continue;
            }
            final ApiFoxSchema s = type.handler.handle(parentInfo, currInfo, level);
            if (Objects.nonNull(s)) {
                return s;
            } else {
                return null;
            }
        }

        // 递归对象检测
        if (level > 1 && Objects.nonNull(parentInfo.getFieldCls()) && OBJ_RECIRCLE_CACHE_SET.contains(parentInfo.getFieldCls().getName())) {
            final ApiFoxSchema schema = new ApiFoxSchema();
            schema.setTitle("子对象");
            schema.setDescription("递归对象");
            return schema;
        }

        // 字段如果是一个对象
        final ApiFoxSchema schema = buildObjSchema(currInfo, level);

        return schema;
    }


    private static ApiFoxSchema buildBasicSchema(GenericInfo currInfo, String apiFoxType, String desc, Object mock) {
        if (Objects.isNull(currInfo.field)) {
            return null;
        }

        final ApiFoxSchema s = new ApiFoxSchema();
        s.setType(apiFoxType);
        s.setMock(new HashMap<String, Object>(4) {{
            this.put("mock", Optional.ofNullable(mock).orElse(1));
        }});

        final ApiModelProperty anno = currInfo.field.getAnnotation(ApiModelProperty.class);
        final boolean flag = !NO_ANNO_WHITE_CLS_LIST.contains(currInfo.fieldCls) && (Objects.nonNull(anno) && anno.hidden());
        if (flag) {
            return null;
        }
        s.setTitle(Objects.isNull(anno) ? "" : anno.value());
        s.setDescription(Objects.isNull(desc) ? (Objects.isNull(anno) ? "" : anno.notes()) : desc);
        return s;
    }

    private static List<Field> getAllFields(Class<?> cls) {
        List<Field> fields = new ArrayList<>(32);
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        return fields;
    }

    @Getter
    enum ApiFoxTypeEnum {
        /**
         * str
         */
        STRING(
                c -> "String".equals(c.getSimpleName()),
                (parentInfo, currInfo, level) ->
                        buildBasicSchema(
                                currInfo,
                                "string",
                                null,
                                ("mock-" + (Objects.isNull(currInfo.field) ? "str" : currInfo.field.getName()))
                        )
        ),
        /**
         * date
         */
        DATE(
                c -> c.getSimpleName().contains("Date"),
                (parentInfo, currInfo, level) ->
                        buildBasicSchema(
                                currInfo,
                                "string",
                                null,
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        )
        ),
        /**
         * enum
         */
        ENUM(
                Enum.class::isAssignableFrom,
                (parentInfo, currInfo, level) ->
                        buildBasicSchema(
                                currInfo,
                                "string",
                                Arrays.asList(currInfo.fieldCls.getEnumConstants()).toString(),
                                currInfo.fieldCls.getEnumConstants()[new Random().nextInt(currInfo.field.getAnnotations().length)]
                        )
        ),
        /**
         * int
         */
        INTEGER(
                c -> {
                    final String cn = c.getSimpleName();
                    return "Integer".equals(cn) || "int".equals(cn) || "long".equals(cn) || "Long".equals(cn);
                },
                (parentInfo, currInfo, level) ->
                        buildBasicSchema(
                                currInfo,
                                "integer",
                                null,
                                new Random().nextInt(10)
                        )
        ),
        /**
         * bool
         */
        BOOLEAN(
                c -> "Boolean".equals(c.getSimpleName()),
                (parentInfo, currInfo, level) ->
                        buildBasicSchema(
                                currInfo,
                                "boolean",
                                null,
                                1 == (new Random().nextInt(10) % 2)
                        )
        ),
        /**
         * arr
         */
        ARRAY1(
                c -> c.getSimpleName().endsWith("[]"),
                (parentInfo, currInfo, level) -> {
                    final ApiModelProperty anno = currInfo.field.getAnnotation(ApiModelProperty.class);
                    final ApiFoxSchema schema = new ApiFoxSchema();
                    schema.setType("array");
                    schema.setTitle(anno.value());
                    schema.setDescription(anno.notes());
                    schema.setItems(routeAndBuildSchema(parentInfo, new GenericInfo(currInfo.field, currInfo.fieldCls.getComponentType()), level));
                    return schema;
                }
        ),
        /**
         * arr
         */
        ARRAY2(
                c -> {
                    final String cn = c.getSimpleName();
                    return "List".equals(cn) || "ArrayList".equals(cn) || "LinkedList".equals(cn) ||
                            "Set".equals(cn) || "HashSet".equals(cn) || "TreeSet".equals(cn) || "Collection".equals(cn);
                },
                (parentInfo, currInfo, level) -> {
                    try {
                        final ParameterizedType genericType = (ParameterizedType) currInfo.getField().getGenericType();
                        final Class<?> argType = (Class<?>) genericType.getActualTypeArguments()[0];
                        final ApiModelProperty anno = currInfo.getField().getAnnotation(ApiModelProperty.class);
                        final ApiFoxSchema schema = new ApiFoxSchema();
                        schema.setType("array");
                        schema.setTitle(anno.value());
                        schema.setDescription(anno.notes());
                        schema.setItems(routeAndBuildSchema(parentInfo, new GenericInfo(currInfo.field, argType), level));
                        return schema;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );

        private final Function<Class<?>, Boolean> matches;

        private final ApiFoxHandler handler;

        ApiFoxTypeEnum(Function<Class<?>, Boolean> matches, ApiFoxHandler handler) {
            this.matches = matches;
            this.handler = handler;
        }
    }

    @FunctionalInterface
    private interface ApiFoxHandler {
        /**
         * handler
         *
         * @param parentInfo pi
         * @param currInfo   ci
         * @param level      层级
         * @return schema
         * @author zoufanqi
         * @since 2023/7/5 17:00
         */
        ApiFoxSchema handle(GenericInfo parentInfo, GenericInfo currInfo, int level);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ApiFoxSchema {
        private String type;

        private String title;

        private String description;

        private Map<String, Object> mock;

        private Map<String, ApiFoxSchema> properties = new LinkedHashMap<>(8);

        private ApiFoxSchema items;

        private List<String> required = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class GenericInfo {
        private final Field field;

        private final Class<?> fieldCls;

        public static GenericInfo buildGenericInfo(Field field) {
            // 字段真实类型（如果是泛型则传递对象）
            Class<?> fieldGenericCls;
            try {
                // 非泛型（强转Class失败）
                fieldGenericCls = (Class<?>) field.getGenericType();
            } catch (Exception e) {
                fieldGenericCls = field.getType();
            }
            return new GenericInfo(field, fieldGenericCls);
        }
    }
}
