package pres.swegnhan.packhelper.application.commandservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pres.swegnhan.packhelper.application.commandservice.SupportSystemCommandService;
import pres.swegnhan.packhelper.core.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.commandrepository.SupportSystemRepository;

import java.util.Optional;

@Service
public class MyBatisSupportSystemCommandService implements SupportSystemCommandService {

    @Autowired
    private SupportSystemRepository supportSystemRepository;

    @Override
    public void create(SupportSystem sups) throws Exception {
        if(supportSystemRepository.findByNameVersion(sups.getName(), sups.getVersion()) != null)
            throw new Exception();
        supportSystemRepository.insert(sups);
    }

    @Override
    public Optional<SupportSystem> findByUid(String uid) {
        SupportSystem sups = supportSystemRepository.findByUid(uid);
        if(sups == null)
            return Optional.empty();
        return Optional.of(sups);
    }

    @Override
    public Optional<SupportSystem> findByNameVersion(String name, String verion) {
        SupportSystem sups = supportSystemRepository.findByNameVersion(name, verion);
        if(sups == null)
            return Optional.empty();
        return Optional.of(sups);
    }

    @Override
    public void remove(String uid) throws Exception {
        if(supportSystemRepository.findByUid(uid) == null)
            throw new Exception();
        supportSystemRepository.delete(uid);
    }

}
