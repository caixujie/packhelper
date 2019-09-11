package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.UserQueryService;
import pres.swegnhan.packhelper.infrastructure.userrepository.UserQueryRepoditory;

import java.util.List;
@Service
public class MybatisUserQueryService implements UserQueryService {
    @Autowired
    private UserQueryRepoditory userQueryRepoditory;

    @Override
    public List<String> queryByLevel1(List<String> pushWhere) {
        return userQueryRepoditory.findIdByWhere(pushWhere);
    }

    @Override
    public String queryMacByip(String ip) {

        return userQueryRepoditory.findMacByIp(ip);
    }
}
