package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PackageUploadDownloadRepository {
    void updateDownloads(@Param("url") String fileName);
}
