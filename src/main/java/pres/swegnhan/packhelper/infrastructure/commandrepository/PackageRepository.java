package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@Repository
public interface PackageRepository {

    void insert(@Param("pack") Package pack);

    Package findByUid(@Param("uid") String uid);

    Package findByNameVersion(@Param("name") String name, @Param("version") String version);

    void update(@Param("pack") Package pack);

    void delete(@Param("uid") String uid);

    boolean hasSupportSystem(@Param("sups") SupportSystem sups);

    void insertPackSupsRelation(@Param("packId") String packId, @Param("supsId") String supsId);

    void deletePackSupsRelation(@Param("packId") String packId, @Param("supsId") String supsId);

}
