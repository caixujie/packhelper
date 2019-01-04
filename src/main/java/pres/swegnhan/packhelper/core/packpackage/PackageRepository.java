package pres.swegnhan.packhelper.core.packpackage;

import java.util.Optional;

public interface PackageRepository {

    void save(Package pack, String tempFilePath) throws Exception;

    Optional<Package> findById(String id);

    void remove(Package pack);

}
