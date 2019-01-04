package pres.swegnhan.packhelper.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pres.swegnhan.packhelper.pojo.DBTest;
import pres.swegnhan.packhelper.service.DBTestService;

@RestController
@RequestMapping(value = "/api/dbtest")
public class DBTestRestController {

    @Autowired
    DBTestService dbTestService;

    @RequestMapping(value = "/addDBTest", method = RequestMethod.POST)
    public boolean addDBTest(DBTest dbTest) {
        System.out.println("开始新增...");
        return dbTestService.addTest(dbTest);
    }

    @RequestMapping(value = "/updateDBTest", method = RequestMethod.PUT)
    public boolean updateDBTest( DBTest dbTest) {
        System.out.println("开始更新...");
        return dbTestService.updateTest(dbTest);
    }

    @RequestMapping(value = "/deleteDBTest", method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value = "testId", required = true) int dbTestId) {
        System.out.println("开始删除...");
        return dbTestService.deleteTest(dbTestId);
    }

    @RequestMapping(value = "/testName", method = RequestMethod.GET)
    public DBTest findByDBTestName(@RequestParam(value = "testName", required = true) String dbTestName) {
        System.out.println("开始查询...");
        return dbTestService.findByName(dbTestName);
    }

    @RequestMapping(value = "/testId", method = RequestMethod.GET)
    public DBTest findByDBTestId(@RequestParam(value = "testId", required = true) int dbTestId) {
        System.out.println("开始查询...");
        return dbTestService.findById(dbTestId);
    }

}
