package com.libing.libingdemo.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TianmuVipEquityFilter {
//public class TianmuVipEquityFilter implements GlobalFilter, Ordered {
//    @Resource
//    private CacheServiceFactory cacheServiceFactory;
//    @Resource
//    private SystemProxy systemProxy;
//    @Resource
//    private TokenCache tokenCache;
//    // 保存HttpMessageReader
//    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        try {
//            //从缓存中获取所有需要鉴权的路径，如果缓存中没有则查询数据库并缓存
//            CacheService cacheService = cacheServiceFactory.getCacheService();
//            String equityConfigStr = cacheService.get(VipEquityCacheConstants.TIANMU_EQUITY_CONFIG);
//            Map<String, VipEquityConfigVO> equityConfigVOMap = new HashMap<>();
//            if (StringUtils.isBlank(equityConfigStr)) {
//                //如果从缓存中没有获取到,则查询配置
//                List<VipEquityConfigVO> vipEquityConfigVOS = systemProxy.queryVipEquityConfig(new VipEquityConfigQueryParam());
//                if (CollectionUtils.isEmpty(vipEquityConfigVOS)) {
//                    cacheService.set(VipEquityCacheConstants.TIANMU_EQUITY_CONFIG, JsonUtils.toJsonString(equityConfigVOMap));
//                    //如果查询结果为空,则表示没有配置,直接返回
//                    return chain.filter(exchange);
//                }
//                for (VipEquityConfigVO vipEquityConfigVO : vipEquityConfigVOS) {
//                    equityConfigVOMap.put(vipEquityConfigVO.getUrl(), vipEquityConfigVO);
//                }
//                cacheService.set(VipEquityCacheConstants.TIANMU_EQUITY_CONFIG, JsonUtils.toJsonString(equityConfigVOMap));
//            } else {
//                //从缓存中获取到路径缓存
//                equityConfigVOMap = JsonUtils.parseByType(equityConfigStr, new TypeToken<Map<String, VipEquityConfigVO>>() {
//                }.getType());
//            }
//            if (MapUtils.isEmpty(equityConfigVOMap)) {
//                //如果最终结果为空,则返回
//                return chain.filter(exchange);
//            }
//            ServerHttpRequest request = exchange.getRequest();
//            String url = exchange.getAttribute(RequestConstants.SERVER_WEB_EXCHANGE_PATH);
//            if (!equityConfigVOMap.containsKey(url)) {
//                //如果缓存不包含此次请求的url,则表示不需要校验,直接返回
//                return chain.filter(exchange);
//            }
//            VipEquityConfigVO vipEquityConfigVO = equityConfigVOMap.get(url);
//            String token = "";
//            List<String> tokenList = exchange.getRequest().getHeaders().get(RequestConstants.HEADER_NAME_TOKEN);
//            if (ListUtils.isEmpty(tokenList)) {
//                MultiValueMap<String, String> mmap = exchange.getRequest().getQueryParams();
//                if (mmap != null) {
//                    tokenList = mmap.get(RequestConstants.HEADER_NAME_TOKEN);
//                }
//            }
//            if (!ListUtils.isEmpty(tokenList)) {
//                token = tokenList.get(0);
//            }
//            String userVipLevel = "";
//            if (StringUtils.isEmpty(token)) {
//                //没有token,则说明为游客，进行权限校验
//                userVipLevel = UserAndTenantVipLevelEnum.VISITOR.getCode();
//                checkEquity(vipEquityConfigVO, UserAndTenantVipLevelEnum.VISITOR.getCode());
//            } else {
//                //需要鉴权，则获取当前用户
//                String clientType = exchange.getRequest().getHeaders().getFirst(GatewayConstants.HEADER_CLIENT_TYPE);
//                log.info("客户端类型={}", clientType);
//                if (com.tiandao.core.utils.StringUtils.isBlank(clientType)) {
//                    MultiValueMap<String, String> mmap = exchange.getRequest().getQueryParams();
//                    if (mmap != null) {
//                        List<String> clientTypes = mmap.get(RequestConstants.HEADER_CLIENT_TYPE);
//                        log.debug("clientType from queryParams clientTypes = {}", clientTypes);
//                        if (ListUtils.isNotEmpty(clientTypes)) {
//                            clientType = clientTypes.get(0);
//                        }
//                    }
//                }
//                String serviceType = exchange.getRequest().getHeaders().getFirst(GatewayConstants.HEADER_SERVICE_TYPE);
//                log.info("服务类型={}", serviceType);
//                if (com.tiandao.core.utils.StringUtils.isBlank(serviceType)) {
//                    MultiValueMap<String, String> mmap = exchange.getRequest().getQueryParams();
//                    if (mmap != null) {
//                        List<String> serviceTypes = mmap.get(GatewayConstants.HEADER_SERVICE_TYPE);
//                        log.debug("clientType from queryParams clientTypes = {}", serviceTypes);
//                        if (ListUtils.isNotEmpty(serviceTypes)) {
//                            serviceType = serviceTypes.get(0);
//                        }
//                    }
//                }
//                UserCacheVO user = tokenCache.getUser(token, clientType);
//                if (ObjectUtils.isNotEmpty(user)) {
//                    userVipLevel = cacheService.get(VipEquityCacheConstants.TIANMU_EQUITY_USER_VIP_LEVEL + user.getUserId());
//                }
//                SysUserRoleVO userInfo = null;
//                //如果为空,则查询当前用户vip等级
//                if (StringUtils.isBlank(userVipLevel)) {
//                    //查询当前用户vip等级
//                    try {
//                        userInfo = systemProxy.getUserInfo(token, clientType, serviceType);
//                    } catch (Exception e) {
//                        if (StringUtils.equals(e.getMessage(), "请登录后操作")) {
//                            throw new BaseServiceException(401, "请登录后操作");
//                        }
//                        log.error("查询当前用户vip等级失败:{}", e.getMessage());
//                    }
//                    if (ObjectUtils.isEmpty(userInfo)) {
//                        //若结果为空,则视为注册用户
//                        userVipLevel = UserAndTenantVipLevelEnum.VISITOR.getCode();
//                    } else {
//                        //若不为空,则取用户vip等级
//                        userVipLevel = userInfo.getUserVipLevel();
//                    }
//                }
//                //判断用户等级
//                if (StringUtils.isBlank(userVipLevel)) {
//                    //若用户vip等级最终结果为空,则视为注册用户
//                    userVipLevel = UserAndTenantVipLevelEnum.REGISTER.getCode();
//                } else {
//                    //否则缓存到redis中
//                    if (ObjectUtils.isNotEmpty(user)) {
//                        cacheService.set(VipEquityCacheConstants.TIANMU_EQUITY_USER_VIP_LEVEL + user.getUserId(), userVipLevel);
//                    } else if (ObjectUtils.isNotEmpty(userInfo)) {
//                        cacheService.set(VipEquityCacheConstants.TIANMU_EQUITY_USER_VIP_LEVEL + userInfo.getId(), userVipLevel);
//                    }
//                }
//                //进行权限校验
//                checkEquity(vipEquityConfigVO, userVipLevel);
//            }
//            //至此,接口层面校验已完成
//            //接下来进行参数校验
//            List<String> equityParams = getEquityParams(vipEquityConfigVO, userVipLevel);
//            if (CollectionUtils.isNotEmpty(equityParams)) {
//                Map<String, Object> params = new HashMap<>();
//                request.getQueryParams().forEach((key, items) -> {
//                    params.put(key, items.get(0));
//                });
//                if ("GET".equals(request.getMethodValue())) {
//                    return this.checkSign(params, chain, exchange, equityParams);
//                } else if ("POST".equals(request.getMethodValue())) {
//                    return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
//                        DataBufferUtils.retain(dataBuffer);
//                        final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
//                        final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
//                            @Override
//                            public Flux<DataBuffer> getBody() {
//                                return cachedFlux;
//                            }
//                        };
//                        final ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
//                        return cacheBody(mutatedExchange, chain, params, equityParams);
//                    });
//                }
//            }
//            return chain.filter(exchange);
//        } catch (BaseServiceException e) {
//            log.error("会员权益校验失败:{}" + e.getMessage());
//            if (e.getErrorCode() == 1007 || e.getErrorCode() == 1006) {
//                throw e;
//            } else {
//                return chain.filter(exchange);
//            }
//        }
//    }
//
//    /**
//     * 获取当前用户可以访问的参数
//     */
//    public List<String> getEquityParams(VipEquityConfigVO vipEquityConfigVO, String type) {
//        List<String> result = new ArrayList<>();
//        if (!ObjectUtils.isEmpty(vipEquityConfigVO)) {
//            String equityStr = "";
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.VISITOR.getCode(), type)) {
//                equityStr = vipEquityConfigVO.getVisitorParam();
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.REGISTER.getCode(), type)) {
//                equityStr = vipEquityConfigVO.getRegisterParam();
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.VIP.getCode(), type)) {
//                equityStr = vipEquityConfigVO.getVipParam();
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.SVIP.getCode(), type)) {
//                equityStr = vipEquityConfigVO.getSvipParam();
//            }
//            if (StringUtils.isNotBlank(equityStr)) {
//                result = Arrays.asList(equityStr.split(","));
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 权益校验
//     */
//    public void checkEquity(VipEquityConfigVO vipEquityConfigVO, String type) {
//        if (!ObjectUtils.isEmpty(vipEquityConfigVO)) {
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.VISITOR.getCode(), type)) {
//                if (StringUtils.equals(vipEquityConfigVO.getVisitor(), VipEquityAccessEnum.NO.getCode())) {
//                    throw new BaseServiceException(GatewayErrorMessage.E1006);
//                }
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.REGISTER.getCode(), type)) {
//                if (StringUtils.equals(vipEquityConfigVO.getRegister(), VipEquityAccessEnum.NO.getCode())) {
//                    throw new BaseServiceException(GatewayErrorMessage.E1007);
//                }
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.VIP.getCode(), type)) {
//                if (StringUtils.equals(vipEquityConfigVO.getVip(), VipEquityAccessEnum.NO.getCode())) {
//                    throw new BaseServiceException(GatewayErrorMessage.E1007);
//                }
//            }
//            if (StringUtils.equals(UserAndTenantVipLevelEnum.SVIP.getCode(), type)) {
//                if (StringUtils.equals(vipEquityConfigVO.getSvip(), VipEquityAccessEnum.NO.getCode())) {
//                    throw new BaseServiceException(GatewayErrorMessage.E1007);
//                }
//            }
//        }
//    }
//
//    /***
//     * 验证签名
//     * @author Lance lance_lan_2016@163.com
//     * @date 2020-01-07 09:57
//     * @param params
//     * @param chain
//     * @param exchange
//     * @return reactor.core.publisher.Mono<java.lang.Void>
//     *
//     * */
//    private Mono<Void> checkSign(Map<String, Object> params, GatewayFilterChain chain,
//                                 ServerWebExchange exchange, List<String> equityParams) {
//        log.info("校验参数集合：" + params);
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            if (!equityParams.contains(entry.getKey()) && ObjectUtils.isNotEmpty(entry.getValue())) {
//                throw new BaseServiceException(GatewayErrorMessage.E1007);
//            }
//        }
//        return chain.filter(exchange);
//    }
//
//    @SuppressWarnings("unchecked")
//    private Mono<Void> cacheBody(ServerWebExchange exchange, GatewayFilterChain chain, Map<String, Object> params, List<String> equityParams) {
//        final HttpHeaders headers = exchange.getRequest().getHeaders();
//        if (headers.getContentLength() == 0) {
//            return chain.filter(exchange);
//        }
//        final ResolvableType resolvableType;
////        if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(headers.getContentType())) {
////            resolvableType = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);
////        } else
//        if (MediaType.APPLICATION_JSON.isCompatibleWith(headers.getContentType())) {
//            resolvableType = ResolvableType.forClass(Map.class);
//        } else {
//            resolvableType = ResolvableType.forClass(String.class);
//        }
//
//        return MESSAGE_READERS.stream().filter(reader -> reader.canRead(resolvableType, headers.getContentType())).findFirst()
//                .orElseThrow(() -> new IllegalStateException("no suitable HttpMessageReader.")).readMono(resolvableType,
//                        exchange.getRequest(), Collections.emptyMap()).flatMap(resolvedBody -> {
////                    if (resolvedBody instanceof MultiValueMap) {
////                        @SuppressWarnings("rawtypes")
////                        MultiValueMap<String, Object> map = (MultiValueMap) resolvedBody;
////                        map.keySet().forEach(key -> {
//////                            SynchronossPartHttpMessageReader
////                            Object obj = map.get(key);
////                            List<Object> list = (List<Object>) obj;
////                            for (Object object : list) {
////                                if (object.getClass().toString().equals("class org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader$SynchronossFilePart")) {
////                                    // 过滤如果是SynchronossFilePart这个文件类型，就是传入的文件参数，做签名校验的时候，我这里没有验签文件体
////                                    continue;
////                                }
////                                // 通过反射的形式获取这个类型SynchronossPartHttpMessageReader下面的私有类SynchronossFormFieldPart的参数值
////                                Field[] fields = object.getClass().getDeclaredFields();
////                                try {
////                                    for (Field field : fields) {
////                                        field.setAccessible(true);
////                                        params.put(key, field.get(object) + "");
////                                    }
////                                } catch (IllegalAccessException e) {
////                                    e.printStackTrace();
////                                    log.error(e.getLocalizedMessage());
////                                }
////                            }
////                        });
////                    }
//                    if (resolvedBody instanceof Map) {
//                        Map<String, Object> map = (Map) resolvedBody;
//                        map.entrySet().forEach(entry -> {
//                            params.put(entry.getKey(), entry.getValue());
//                        });
//                    } else {
//                        // post请求中，在请求地址中的参数，如果做鉴权，也要考虑到
//                        if (null != resolvedBody) {
//                            String path = null;
//                            try {
//                                path = URLDecoder.decode(((Object) resolvedBody).toString(), "UTF-8");
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                                log.error(e.getLocalizedMessage());
//                            }
//                            if (null != path) {
//                                String items[] = path.split("&");
//                                for (String item : items) {
//                                    String subItems[] = item.split("=");
//                                    if (null != subItems && subItems.length == 2) {
//                                        params.put(subItems[0], subItems[1]);
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//                    // 验签或者其他操作
//                    return this.checkSign(params, chain, exchange, equityParams);
//                });
//    }
//
//    @Override
//    public int getOrder() {
//        return 7;
//    }
//
}