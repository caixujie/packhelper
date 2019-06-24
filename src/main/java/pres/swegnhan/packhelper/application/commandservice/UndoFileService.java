package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

public interface UndoFileService {
   public void create(List<PushUserPackages> pushUserPackage);
   public void delete(PushUserPackages pushUserPackage);
}
