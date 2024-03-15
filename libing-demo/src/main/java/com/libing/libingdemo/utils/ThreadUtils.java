package com.libing.libingdemo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadUtils {

    ExecutorService executorService1 = new ScheduledThreadPoolExecutor(1);
    //executorService.execute(() -> {
//        //执行预警动作
////        alertPlanActionService.executePlanAction(paramF);
//    });
//executorService1.shutdown();
    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    Runnable runnable = () -> {
//        try {
//            //请求前先查询站内信是否已读
//            //查询出本次预警发出的所有站内信
//            NoticeBusinessF f = new NoticeBusinessF();
//            f.setBusinessId(paramF.getAlertCode());
//            f.setNoticeTime(paramF.getNoticeTime());
//            List<NoticeBusinessAlertV> messageVS = msgFeignClient.queryAlertMessage(f);
//            System.out.println("弹窗查询结果如下-------" + messageVS);
//            //过滤出已读站内信数据
//            List<NoticeBusinessAlertV> messageVSReaded = messageVS.stream().filter(a -> ObjectUtils.isNotEmpty(a.getChecked()) && StringUtils.equals(a.getChecked(), "1") && StringUtils.equals(a.getWbsMessage(), "1")).collect(Collectors.toList());
//            //若存在已读则按成功进行匹配
//            if (messageVSReaded.size() > 0) {
//                paramF.setParentResult(true);
//                paramF.setParentId(paramF.getAlertPlanActionD().getId());
//                executePlanAction(paramF);
//            } else {
//                paramF.setParentResult(false);
//                paramF.setParentId(paramF.getAlertPlanActionD().getId());
//                executePlanAction(paramF);
//            }
//        } catch (Exception e) {
//            paramF.setParentResult(false);
//            paramF.setParentId(paramF.getAlertPlanActionD().getId());
//            executePlanAction(paramF);
//            log.error("弹窗查询失败，错误原因：{}", e);
//            executorService.shutdown();
//            throw BizException.throw400("系统内部错误");
//        }
    };
//    if (ObjectUtils.isNotEmpty(paramF.getAlertActionConfigF().getCheckDuration()) && ObjectUtils.isNotEmpty(paramF.getAlertActionConfigF().getCheckTimeType())) {
//        executorService.schedule(runnable, paramF.getAlertActionConfigF().getCheckDuration().longValue(), TimeTypeEnum.getNameByCode(paramF.getAlertActionConfigF().getCheckTimeType()));
//    } else {
//        executorService.execute(runnable);
//    }
}
