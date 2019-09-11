package pres.swegnhan.packhelper.application.commandservice;

import org.springframework.web.multipart.MultipartFile;
import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

public interface PushFileService {
    public void addTask(String fileName, List<String> pushWhere, String desc, String taskName);
    public void addTaskTest();
    public void onlineAddTask(String user_mac);
}
