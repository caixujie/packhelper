package pres.swegnhan.packhelper.application.commandservice;

import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.Optional;

public interface PackageCommandService {

    void create(Package pack, String tempFilePath) throws Exception;

    void update(Package pack) throws Exception;

    void update(Package pack, String tempFilePath) throws Exception;

//    void saveWithAnalysis(Package pack, String tempFilePath) throws Exception;

    Optional<Package> findByUid(String uid);

    void remove(Package pack) throws Exception;

    void createSupportSystem(SupportSystem sups) throws Exception;

    void removeSupportSystem(SupportSystem sups) throws Exception;

}
