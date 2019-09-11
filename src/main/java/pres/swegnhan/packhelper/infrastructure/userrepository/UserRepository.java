package pres.swegnhan.packhelper.infrastructure.userrepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.Comment;
import pres.swegnhan.packhelper.core.User;
@Mapper
@Repository
public interface UserRepository {
    void insert(@Param("user") User user);
}
