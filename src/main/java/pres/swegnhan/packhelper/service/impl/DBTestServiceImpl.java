package pres.swegnhan.packhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pres.swegnhan.packhelper.pojo.DBTest;
import pres.swegnhan.packhelper.dao.DBTestDao;
import pres.swegnhan.packhelper.service.DBTestService;

@Service
public class DBTestServiceImpl implements DBTestService {

    @Autowired
    private DBTestDao dbTestDao;

    @Override
    public boolean addTest(DBTest test) {
        boolean flag = false;
        try{
            dbTestDao.addTest(test);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean updateTest(DBTest test) {
        boolean flag = false;
        try{
            dbTestDao.updateTest(test);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteTest(int id) {
        boolean flag = false;
        try{
            dbTestDao.deleteTest(id);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public DBTest findById(int userid) {
        return dbTestDao.findById(userid);
    }

    @Override
    public DBTest findByName(String username) {
        return dbTestDao.findByName(username);
    }

}
