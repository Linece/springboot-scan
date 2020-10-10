package com.zdc.scan.annotation;


import com.zdc.scan.enums.DocumentType;

import java.lang.annotation.*;

/**
 * @program: ovr-common
 * @description: odf数据组装bean注解
 * @author: xjr
 * @create: 2020-08-05 16:47
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OdfBean {

    String value() default "";

    /*
    说明bean支持的文档类型
     */
    DocumentType[] documentTypes();

    /*
    说明bean在填充xml报文时使用的方式 LOCAL 本地模板  TMS 远程调用模板平台模板
     */
    //OdfParseWay dependsOn() default OdfParseWay.UNKNOWN;

    /*
    说明模板文件的位置,当且仅当dependsOn属性为LOCAL的时候才会使用，且为空或者路径不对会报错
     */
    String templatePath() default "";
    /*
    模板id，当且仅当选择TMS生成xml时，远程调用所需要的参数
     */
    String templateId() default "";

    /*
    * 当前策略bean的工作内容（方便日志打印）
    */
    String purpose() default "odf报文数据组装";

    /*
    ** 当前parse方法的条件判断标识 使用spring el表达式,不填写则标识此方法不参与条件判断
     */
    String[] conditionExpression() default {};



}
