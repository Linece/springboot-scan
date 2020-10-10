package com.zdc.scan.inter;


import com.zdc.scan.annotation.OdfBean;

/**
 * @program: ovr-common
 * @description: odf消息接口定义
 * @author: xjr
 * @create: 2020-08-05 17:01
 **/
public interface OdfMessage {

//   /**
//    *
//    * @description 数据填充成odf报文
//    * @param rscUnitCode: 单元编码
//    * @param documentType xml类型
//    * @param disciplineUnit 当前项目单元级别为Discipline的数据(运行期自动替换)
//    * @author xiejiarong
//    * @return BodyTyoe 只处理数据返回
//    * @date 2020年08月05日 17:02
//    * @throws BizException 业务异常
//    */
//    BodyType parse(String rscUnitCode, String documentType, BizEventUnit disciplineUnit) throws BizException;

    /**
     *
     * @description 获取自身的odfBean注解（由动态代理类执行）
     * @return  Odfbean 注解
     * @author xiejiarong
     * @date 2020年08月16日 18:44
     */
    default OdfBean getAnnotation(){
        return null;
    }



}
