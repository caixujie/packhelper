package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.Optional;

public interface SupportSystemCommandService {

    void create(SupportSystem sups) throws Exception;

    Optional<SupportSystem> findByUid(String uid);

    Optional<SupportSystem> findByNameVersion(String name, String verion);

    void remove(String uid) throws Exception;

}
