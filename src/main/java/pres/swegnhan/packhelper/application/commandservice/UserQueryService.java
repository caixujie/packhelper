package pres.swegnhan.packhelper.application.commandservice;

import java.util.List;

public interface UserQueryService {
    List<String> queryByLevel1(List<String> pushWhere);
    String queryMacByip(String ip);
}
