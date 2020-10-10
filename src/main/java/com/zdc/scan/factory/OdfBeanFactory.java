package com.zdc.scan.factory;
import com.zdc.scan.enums.DocumentType;
import com.zdc.scan.init.abstracts.AbstractGlobalApplicationInit;
import com.zdc.scan.inter.OdfMessage;
import com.zdc.scan.registry.OdfMessageFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.env.Environment;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ovr-common
 * @description: odfbean工厂
 * @author: xjr
 * @create: 2020-08-05 16:53
 **/
@Component
@Slf4j
public class OdfBeanFactory extends AbstractGlobalApplicationInit {

    private  static Map<String, OdfMessage> beansMap= new HashMap<>();

    public  static List<OdfMessage> beansList= new ArrayList<>();

    private static SpelExpressionParser parser=new SpelExpressionParser();

    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public void execute(ApplicationContext applicationContext, Environment environment) throws Throwable {
        //启动立马加载
        applicationContext.getBeansOfType(OdfMessage.class);
        beansMap= OdfMessageFactoryBean.beansMap;
    }



    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean async() {
        return false;
    }

    @Override
    public String taskName() {
        return "odf策略bean装载进工厂";
    }

    /**
     *
     * @description 根据类型获取odfbean
     * @param key: 键名
     * @return odfMessage
     * @author xiejiarong
     * @date 2020年08月13日 11:30
     */
    private static OdfMessage getOdfBean(DocumentType key) throws Exception{
        if (!beansMap.containsKey(key.value())){
            throw new Exception("不存在名字为"+key.value()+"的odf报文转换器");
        }
        return beansMap.get(key.value());
    }

    /**
     *
     * @description 外界执行消息组装和生成报文的唯一入口
     * @param key: 键名
     * @return
     * @author xiejiarong
     * @date 2020年08月13日 15:39
     */
    public static void parse(DocumentType key,String rscUnitCode)throws Exception{
        OdfMessage strategy=getOdfBean(key);
        //strategy.parse(rscUnitCode,key.value(),null);

    }

    /**
     *
     * @description 有条件的生成odf报文
     * @param entity: 封装了请求参数的实体
     * @return
     * @author xiejiarong
     * @date 2020年08月13日 16:55
     */
   // public static void parseByCondition(OdfEntity entity) throwsException{
//        EvaluationContext context=new StandardEvaluationContext();
//        context.setVariable("entity",entity);
//        List<Field> fields= FieldUtils.getAllFieldsList(OdfEntity.class);
//        fields.forEach(field->{
//            field.setAccessible(true);
//            try {
//                context.setVariable(field.getName(),field.get(entity));
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });
//        beansList.forEach(data->{
//            OdfBean odfBean= data.getAnnotation();
//            if (odfBean.conditionExpression().length>0){
//                StringBuffer springEl=new StringBuffer();
//                String[] conditions=odfBean.conditionExpression();
//                for (int i = 0; i < conditions.length; i++) {
//                    if (i==conditions.length-1){
//                        springEl.append(conditions[i]);
//                        continue;
//                    }
//                    springEl.append(conditions[i]+" "+odfBean.LogicalExpression().expression()+" ");
//                }
//                try {
//                    Expression expression=parser.parseExpression(springEl.toString());
//                    if (expression.getValue(context,Boolean.class)){
//                        data.parse(entity.getRscUnitCode(),odfBean.documentTypes()[0].value(),null);
//                    }
//                }catch (Exception ex){
//                    log.info("{}的注解odfBean的条件表达式解析失败！该表达式为：{}",odfBean.purpose(),springEl);
//                }
//            }
       // });




  //  }

//    public static void main(String[] args) {
//        SpelExpressionParser spelExpressionParser=new SpelExpressionParser();
//        Expression expression=spelExpressionParser.parseExpression("'123' eq #test.rscUnitCode");
//        EvaluationContext evaluationContext=new StandardEvaluationContext();
//        evaluationContext.setVariable("test", BizEventUnit.builder().rscUnitCode("1234").build());
//        System.out.println(expression.getValue(evaluationContext,Boolean.class).toString());
//
//    }
}
