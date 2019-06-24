package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

public interface PushFileService {
    public void addTask(List<PushUserPackages> pushUserPackage);
    public void addTaskTest();
}
