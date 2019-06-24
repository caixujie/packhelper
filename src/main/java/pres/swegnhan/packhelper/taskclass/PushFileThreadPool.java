package pres.swegnhan.packhelper.taskclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
@Component
public class PushFileThreadPool {

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
        }
//        exec.shutdown();
    }
}
