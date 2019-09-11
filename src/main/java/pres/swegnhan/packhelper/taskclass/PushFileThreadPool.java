package pres.swegnhan.packhelper.taskclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
@Component
public class PushFileThreadPool {
//    @Autowired
//    org.springframework.beans.factory.BeanFactory beanFactory;

//    public static void main() {
//        ExecutorService exec = Executors.newFixedThreadPool(100);
//        for(int i=0;i<10=0;i++){
//            exec.execute(new PushFileTask());
//        }
//        exec.shutdown();
//    }
    public PushFileThreadPool(){
        ExecutorService exec = Executors.newFixedThreadPool(100);
        for(int i=0;i<100;i++){
            exec.execute(new PushFileTask());
            System.out.println("我是第"+i+"个线程");
        }
//        exec.shutdown();
    }
}
