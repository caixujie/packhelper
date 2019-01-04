package pres.swegnhan.packhelper.service;

import pres.swegnhan.packhelper.pojo.DBTest;

public interface DBTestService {

    boolean addTest(DBTest test);

    boolean updateTest(DBTest test);

    boolean deleteTest(int id);

    DBTest findById(int userid);

    DBTest findByName(String username);

}
