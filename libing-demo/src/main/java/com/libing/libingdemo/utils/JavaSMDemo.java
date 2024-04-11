package com.libing.libingdemo.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zls
 * @ClassName JavaDemo
 * @datetime 2022年 12月 09日 13:34
 */
public class JavaSMDemo {

    //租户ID，从租户配置信息.txt文档里获取
    private static final String TENANT_ID = "2010148543660xxxx";

    //获取公钥信息，从公钥.txt文档中获取
    private static final String PUBLIC_KEY = "049e3ab75371fd456a5029b69a33aba22643ef7xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxe5c1f8f6ef840ada57e888ade82b1b960612";

    //随机生成16位的秘钥，接入方自定义
    private static final String RANDOM_KEY = "0123456789abcdef";

    //请求地址
    private static final String URL = "https://uat-openapi.tdft.cn/zhelixin/dataservice";


    public static void main(String[] args) throws Exception {

        //接口路径
        String path = "/xxx/xxx";
        //请求报文body内容
        Map<String, Object> data = new HashMap<>();
        data.put("enterpriseName", "xxxx有限公司");
        post(URL + path, JSONUtil.toJsonStr(data));
    }


    @SneakyThrows
    public static void post(String path, String jsonData) {

        Map<String, String> headerMap = new HashMap<>();
        //将租户ID信息设置到请求头
        headerMap.put("tenant-id",TENANT_ID);
        headerMap.put("Content-Type", "application/json");
        //对16位AES秘钥进行SM2加密
        String key = URLEncoder.encode(SmUtil.sm2(null, PUBLIC_KEY).encryptHex(RANDOM_KEY, KeyType.PublicKey), CharsetUtil.UTF_8);

        //对请求报文body内容进行SM4加密
        String encryptData = SmUtil.sm4(RANDOM_KEY.getBytes()).encryptHex(jsonData, StandardCharsets.UTF_8);
        System.out.println("content加密后：contentStr=" + encryptData);
        String decryptContentStr = SmUtil.sm4(RANDOM_KEY.getBytes()).decryptStr(encryptData, CharsetUtil.CHARSET_UTF_8);
        System.out.println("content解密后：decryptContentStr=" + decryptContentStr);

        //构造加密后的请求报文
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("content", encryptData);
        System.out.println(String.format("============>请求地址：%s", path));
        System.out.println(String.format("============>请求参数：%s", jsonData));
        String jsonString = JSONUtil.toJsonStr(paramMap);
        System.out.println(String.format("============>请求加密参数：%s", jsonString));
        //执行请求报文发送
        HttpResponse execute = HttpUtil.createPost(path)
                .body(jsonString)
                .timeout(50000)
                .headerMap(headerMap, false)
                .execute();
        System.out.println(String.format("============>返回结果：%s", execute.body()));


    }

}
