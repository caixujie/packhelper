package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.PushFileService;
import pres.swegnhan.packhelper.core.PushUserPackages;
import pres.swegnhan.packhelper.taskclass.PushFileTask;

import java.util.List;
import java.util.Optional;

@Service
public class MyBatisPushFileService implements PushFileService {
    @Override
    public void addTask(List<PushUserPackages> pushUserPackages) {
        for(PushUserPackages pushUserPackage : pushUserPackages) {
            try {
                PushFileTask.linkedBlockingQeque.put(pushUserPackage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addTaskTest() {
    PushUserPackages pushUserPackage = new PushUserPackages();
    pushUserPackage.setIP("192.168.1.43");
    pushUserPackage.setPackName("wps-office_10.8.0.6714.professional.preload.fx.withsn_arm64.deb");
        try {
            PushFileTask.linkedBlockingQeque.put(pushUserPackage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
