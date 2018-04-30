package com.gjf.validator.certification.school;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: GJF
 * @Date : 2018/04/29
 * Time   : 19:02
 */
public class WHUT extends BaseSchool {

    private final static Pattern PHONE_PATTERN = Pattern.compile("0?(13|14|15|18|17)[0-9]{9}");
    private final static Pattern NAME_PATTERN = Pattern.compile("姓名：<i>([\\u4e00-\\u9fa5]{2,4}?)<");

    public WHUT(String url) {
        super(url);
    }

    @Override
    protected String getUserRealName(String response) {
        return parseFieldFromResponse(response,NAME_PATTERN);
    }

    public String getUserPhone(String response){
        return parseFieldFromResponse(response,PHONE_PATTERN);
    }

    @Override
    protected void putPostParams(String userId,String password) {
        Map<String,String> params= this.getPostParams();
        params.put("userName",userId);
        params.put("password", password);
        params.put("type", "xs");
        this.setPostParams(params);
    }

    private String parseFieldFromResponse(String response,Pattern pattern){
        Matcher matcher = null;
        if (response != null) {
            matcher = pattern.matcher(response);
        }
        if (matcher!=null && matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
