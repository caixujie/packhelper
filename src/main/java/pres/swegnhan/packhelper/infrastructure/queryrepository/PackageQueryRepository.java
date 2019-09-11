package pres.swegnhan.packhelper.infrastructure.queryrepository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.Page;

import java.util.List;
@Mapper
@Repository
public interface PackageQueryRepository {
    // 分页
    public int getCount(Page page);
    public List<Package> getPackage(Page page);

    public List<Package> getPackageall();

    public String findUidByName(String fileName);

    // 添加
//    public int addPackage(Package pack);
//
//    // 删除
//    public int deletePackage(String roleId);
//
//    // 更新
//    public int updatePackage(Package pack);
//
//    // 查询所有包的名字
//    public List<Package> findAllRoleName();

}
