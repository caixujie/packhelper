package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.UndoFileService;
import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
@Service
public class MyBatisUndoFileService implements UndoFileService {
    @Override
    public void create(List<PushUserPackages> pushUserPackage) {

    }

    @Override
    public void delete(PushUserPackages pushUserPackage) {

    }
}
