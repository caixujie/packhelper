package pres.swegnhan.packhelper.infrastructure.mybaits.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.packpackage.Package;
import pres.swegnhan.packhelper.core.packpackage.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.mybatis.mapper.PackageMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageMapperTest {

    @Autowired
    private PackageMapper packageMapper;

    private Package pack;

    private SupportSystem supportSystem;

    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "openjdk-8-jre-headless",
                "8u171-b11-0ubuntu0.18.04.1",
                "1",
                "usr/bin/packhelper/openjdk-8-jre-headless_8u171-b11-0ubuntu0.18.04.1_amd64.deb",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
        supportSystem = new SupportSystem(
                "ubuntu",
                "14.04");
    }

    @Test
    public void should_supportsystem_insert_select_success() throws Exception{
        packageMapper.insertSupportSystem(supportSystem);
        assertThat(packageMapper.findSupportSystem(supportSystem), is(true));
    }

    @Test
    public void should_package_insert_selec_success() throws Exception{
        packageMapper.insert(pack);
        assertThat(pack, equalTo(packageMapper.findByUid(pack.getUid())));
    }

    @Test
    public void should_package_update_success() throws Exception{
        packageMapper.insert(pack);
        pack.setName("openjdk-11-jdk");
        pack.setVersion("10.0.2+13-1ubuntu0.18.04.2");
        pack.setCategory(2);
        pack.setUrl("usr/bin/packhelper/openjdk-11-jdk_10.0.2+13-1ubuntu0.18.04.2_amd64.rpm");
        pack.setFiletype(".rpm");
        packageMapper.update(pack);
        assertThat(pack, equalTo(packageMapper.findByUid(pack.getUid())));
    }

    @Test
    public void should_package_insert_select_delete_update_success() throws Exception{
        packageMapper.insert(pack);
        packageMapper.delete(pack.getUid());
        assertThat(packageMapper.findByUid(pack.getUid()), is(nullValue()));
    }

    @Test
    public void should_packge_supportsystem_relation_insert_success() throws Exception{
        packageMapper.insert(pack);
        for(SupportSystem sups : pack.getSupsList()){
            if(!packageMapper.findSupportSystem(sups))
                packageMapper.insertSupportSystem(sups);
            packageMapper.insertPackSupsRelation(pack.getUid(), sups.getUid());
        }
        assertThat(packageMapper.findByNameVersion(pack.getName(), pack.getVersion()), equalTo(pack));
    }

}
