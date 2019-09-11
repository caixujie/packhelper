package pres.swegnhan.packhelper.infrastructure.userrepository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserQueryRepoditory {
    public List<String> findIdByWhere (List<String> pushWhere);
    public String findMacByIp(String ip);
}
