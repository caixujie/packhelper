package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.Comment;

@Mapper
@Repository
public interface CommentRepository {

    void insert(@Param("comment") Comment comment);

    void update(@Param("comment") Comment comment);

    void delete(@Param("uid") String uid);

    Comment findByUid(@Param("uid") String uid);



}
