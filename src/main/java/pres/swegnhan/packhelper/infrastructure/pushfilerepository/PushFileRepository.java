package pres.swegnhan.packhelper.infrastructure.pushfilerepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.PushUserPackages;

@Mapper
@Repository
public interface PushFileRepository {
    void insert(@Param("pushUserPackage") PushUserPackages pushUserPackage);
    void delete(@Param("pushUserPackage") PushUserPackages pushUserPackage);
}
