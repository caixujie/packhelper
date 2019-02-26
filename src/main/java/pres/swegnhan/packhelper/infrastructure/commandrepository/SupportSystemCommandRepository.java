package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.SupportSystem;

@Mapper
@Repository
public interface SupportSystemCommandRepository {

    void insert(@Param("sups") SupportSystem sups);

    SupportSystem findByUid(@Param("uid") String uid);

    SupportSystem findByNameVersion(@Param("name") String name, @Param("version") String version);

    void delete(@Param("uid") String uid);

}
