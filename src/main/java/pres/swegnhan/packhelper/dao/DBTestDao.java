package pres.swegnhan.packhelper.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import pres.swegnhan.packhelper.pojo.DBTest;

@Component
@Mapper
public interface DBTestDao {

    void addTest(DBTest test);

    void updateTest(DBTest test);

    void deleteTest(long id);

    DBTest findById(long id);

    DBTest findByName(String name);

}
