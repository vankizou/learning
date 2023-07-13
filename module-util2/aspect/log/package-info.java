/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/7/13 09:28
 **/
package com.zmn.biz.amislc.aspect.log;

/*

=== 功能 ===
本包为日志切面逻辑，主要处理逻辑：
1. 统一对入参做校验
2. 统一打印所有Admin/Dubbo接口的出入信息
3. 统一对接口[增删改]操作进行日志持久化（默认处理以[add/modify/delete]开头的方法，也可以添加[@LogPersistent]注解自定义指定）
4. 统一处理异常逻辑，规范接口异常响应数据，如：Admin返回AMISResponseDTO，Dubbo返回ResponseDTO

*/

/*

=== 接入 ===
1. 在Application模块接入AOP，样例：
-- 时机：项目初始化

@Order(1)
@Component
@Aspect
public class AdminLogAspectService extends BaseLogAspectService {

    public AdminLogAspectService() {
        super(LogEnvTypeEnum.ADMIN);
    }

    @Override
    @Around("within(com.zmn.biz.amislc.admin.controller..*)")
    public Object around(ProceedingJoinPoint point) {
        return super.doAround(point);
    }

    @Override
    protected void validateParam(Object arg) {
        if (arg instanceof BaseDIO) {
            ((BaseAddDIO) BaseDIO).validate();
        }
    }

    @Override
    protected Object buildResponseIfThrowException(Throwable ex) {
        if (ex instanceof BizAmislcException || ex instanceof IllegalArgumentException) {
            return AMISResponseDTO.fail(StatusConsts.ERROR_PARAMS, ex.getMessage());
        }
        return AMISResponseDTO.fail(ex.getMessage());
    }
}


2. 在需要持久化日志的【Controller类】加入注解进行注册，样例：
-- 时机：每次新加Controller

@RestController
@RequestMapping("/demo")
@LogPersistentRegister(LogObjTypeEnum.DEMO)
public class AppController {
    ...
}

3. 在不需要打印日志的接口方法上添加忽略打印注解（默认会自动打印），样例：
-- 时机：每次添加新接口

@RestController
@RequestMapping("/demo")
public class AppController {

    @PostMapping("/add")
    @LogPrintIgnore                     // 忽略入参、出参
    @LogPrintIgnoreInputParam           // 只忽略入参
    @LogPrintIgnoreOutputResponse       // 只忽略出参
    public ? add(@RequestBody DIO dio) {
        ...
    }

}

*/