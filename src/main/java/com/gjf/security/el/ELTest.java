package com.gjf.security.el;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @Author: GJF
 * @Date : 2018/06/04
 * Time   : 20:36
 */
public class ELTest {
    public ELTest(){

    }

    public String test(){
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name","value");
        return expressionParser.parseExpression("#name").getValue(context,String.class);
    }
}
