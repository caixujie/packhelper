package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pres.swegnhan.packhelper.application.commandservice.PackageQueryService;
import pres.swegnhan.packhelper.application.commandservice.PushFileService;
import pres.swegnhan.packhelper.application.commandservice.UserQueryService;
import pres.swegnhan.packhelper.core.PushUserPackages;
import pres.swegnhan.packhelper.infrastructure.pushfilerepository.PushFileRepository;
import pres.swegnhan.packhelper.infrastructure.userrepository.UserQueryRepoditory;
import pres.swegnhan.packhelper.taskclass.PushFileTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyBatisPushFileService implements PushFileService {
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private PushFileRepository pushFileRepository;
    @Autowired
    private PackageQueryService packageQueryService;
    @Override
    public void addTask(String fileName, List<String> pushWhere, String desc, String taskName) {
        List<String> listIp = userQueryService.queryByLevel1(pushWhere);
        String packUid = packageQueryService.queryUidByName(fileName);
        for(String ip : listIp) {
//            List<String> iplist = null;
//            iplist.add(ip);
            PushUserPackages pushUserPackage = new PushUserPackages();
            pushUserPackage.setUserMac(userQueryService.queryMacByip(ip));
            pushUserPackage.setPackUid(packUid);
            pushUserPackage.setUserIp(ip);
            pushUserPackage.setPackName(fileName);
            pushUserPackage.setDescription(desc);
            pushUserPackage.setTaskName(taskName);
            System.out.println(pushUserPackage);
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
    pushUserPackage.setUserIp("192.168.1.43");

    pushUserPackage.setPackName("tcpdump_4.6.2-1kord1_arm64.deb");
        try {
            PushFileTask.linkedBlockingQeque.put(pushUserPackage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onlineAddTask(String user_mac) {
//        List<PushUserPackages> listpp = new ArrayList<PushUserPackages>();
        List<PushUserPackages> listpp = pushFileRepository.findByMac(user_mac);
        for (PushUserPackages pushUserPackage : listpp){
            try {
                PushFileTask.linkedBlockingQeque.put(pushUserPackage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
