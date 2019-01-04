package pres.swegnhan.packhelper.infrastructure.mybatis.mapper;

import pres.swegnhan.packhelper.core.packpackage.Package;
import pres.swegnhan.packhelper.core.packpackage.SupportSystem;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PackageMapper {

    void insert(@Param("pack") Package pack);

    Package findByUid(@Param("uid") String uid);

    Package findByNameVersion(@Param("name") String name, @Param("version") String version);

    boolean findSupportSystem(@Param("sups") SupportSystem sups);

    void insertSupportSystem(@Param("sups") SupportSystem sups);

    void insertPackSupsRelation(@Param("packId") String packId, @Param("supsId") String supsId);

    void update(@Param("pack") Package pack);

    void delete(@Param("uid") String uid);

}
