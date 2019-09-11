package pres.swegnhan.packhelper.infrastructure.pushfilerepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.PushUserPackages;

import java.util.List;

@Mapper
@Repository
public interface PushFileRepository {
    void insert(@Param("pushUserPackage") PushUserPackages pushUserPackage);
    void delete(@Param("pushUserPackage") PushUserPackages pushUserPackage);
    List<PushUserPackages> findByMac(String mac);
}
