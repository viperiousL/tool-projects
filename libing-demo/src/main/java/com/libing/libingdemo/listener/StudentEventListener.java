package com.libing.libingdemo.listener;

import com.libing.libingdemo.event.StudentEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StudentEventListener {

    //    @EventListener(condition ="#student.student.id != null")
    @EventListener(StudentEvent.class)
    @Async  //异步监听
    public void handleEvent(StudentEvent student) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(student.getStudent());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void onSaveUserEvent(StudentEvent event) {
        Integer id = event.getStudent().getId();
        System.out.println(id);
    }

}
