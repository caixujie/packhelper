package pres.swegnhan.packhelper.infrastructure.commandrepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageCommandRepositoryTest {

    @Autowired
    private PackageCommandRepository packageRepository;

    private Package pack1, pack2;

    private SupportSystem sups1, sups2;

    @Before
    public void setUp() throws Exception{
        sups1 = new SupportSystem();
        sups1.setUid("04e4bfeb-a9b4-4be1-a65c-a2176fd186c3");
        sups1.setName("ubuntu");
        sups1.setVersion("14.04");
        sups2 = new SupportSystem();
        sups2.setUid("ee3f4052-3cba-41dd-8bf4-2cf917dbdb62");
        sups2.setName("ubuntu");
        sups2.setVersion("16.04");
        pack1 = new Package();
        pack1.setUid(UUID.randomUUID().toString());
        pack1.setName("code");
        pack1.setVersion("1.29.1-1542309157");
        pack1.setCategory(2);
        pack1.setDescription("Code editing. Redefined.\n" +
                " Visual Studio Code is a new choice of tool that combines the simplicity of a code editor with what developers need for the core edit-build-debug cycle. See https://code.visualstudio.com/docs/setup/linux for installation instructions and FAQ.");
        pack1.setFiletype(".deb");
        pack1.setSupsList(Arrays.asList(sups1, sups2));
        pack2 = new Package();
        pack2.setUid("a1d88df3-d038-40d4-b370-cdeb7fe7620f");
        pack2.setName("apache2");
        pack2.setVersion("2.4.29-1ubuntu4.5");
        pack2.setCategory(2);
        pack2.setDescription("Apache HTTP Server\n" +
                " The Apache HTTP Server Project's goal is to build a secure, efficient and\n" +
                " extensible HTTP server as standards-compliant open source software. The\n" +
                " result has long been the number one web server on the Internet.\n" +
                " .\n" +
                " Installing this package results in a full installation, including the\n" +
                " configuration files, init scripts and support scripts.");
        pack2.setFiletype(".deb");
        pack2.setSupsList(Arrays.asList(sups1, sups2));
    }

    @Test
    public void should_package_select_success() throws Exception{
        Package packMirror = packageRepository.findByUid("a1d88df3-d038-40d4-b370-cdeb7fe7620f");
        assertThat(packMirror.getName(), equalTo(pack2.getName()));
        assertThat(packMirror.getVersion(), equalTo(pack2.getVersion()));
        assertThat(packMirror.getCategory(), equalTo(pack2.getCategory()));
        assertThat(packMirror.getDescription(), equalTo(pack2.getDescription()));
        assertThat(packMirror.getFiletype(), equalTo(pack2.getFiletype()));
        assertThat(packMirror.getSupsList(), equalTo(pack2.getSupsList()));
    }

    @Test
    public void should_package_insert_success() throws Exception{
        packageRepository.insert(pack1);
        Package packMirror = packageRepository.findByNameVersion(pack1.getName(), pack1.getVersion());
        assertThat(packMirror.getName(), equalTo(pack1.getName()));
        assertThat(packMirror.getVersion(), equalTo(pack1.getVersion()));
        assertThat(packMirror.getCategory(), equalTo(pack1.getCategory()));
        assertThat(packMirror.getDescription(), equalTo(pack1.getDescription()));
        assertThat(packMirror.getFiletype(), equalTo(pack1.getFiletype()));
    }

    @Test
    public void should_package_update_success() throws Exception{
        packageRepository.insert(pack1);
        Package temp = packageRepository.findByUid(pack1.getUid());
        pack1.setName("openjdk-11-jdk");
        pack1.setVersion("10.0.2+13-1centos0.18.04.2");
        pack1.setCategory(2);
        pack1.setUrl("usr/bin/packhelper/openjdk-11-jdk_10.0.2+13-1centos0.18.04.2_amd64.rpm");
        pack1.setFiletype(".rpm");
        Thread.sleep(1000);
        packageRepository.update(pack1);
        Package packMirror = packageRepository.findByUid(pack1.getUid());
        assertThat(packMirror.getName(), equalTo(pack1.getName()));
        assertThat(packMirror.getVersion(), equalTo(pack1.getVersion()));
        assertThat(packMirror.getCategory(), equalTo(pack1.getCategory()));
        assertThat(packMirror.getUrl(), equalTo(pack1.getUrl()));
        assertThat(packMirror.getFiletype(), equalTo(pack1.getFiletype()));
        assertThat(packMirror.getCreatedAt().isBefore(packMirror.getUpdatedAt()), is(true));
    }

    @Test
    public void should_package_delete_success() throws Exception{
        packageRepository.insert(pack1);
        packageRepository.delete(pack1.getUid());
        assertThat(packageRepository.findByUid(pack1.getUid()), is(nullValue()));
    }

    @Test
    public void should_packge_supportsystem_relation_insert_delete_success() throws Exception{
        packageRepository.insert(pack1);
        for(SupportSystem sups : pack1.getSupsList())
            packageRepository.insertPackSupsRelation(pack1.getUid(), sups.getUid());
        Package packMirror = packageRepository.findByUid(pack1.getUid());
        assertThat(packMirror.getSupsList(), equalTo(pack1.getSupsList()));
        for(SupportSystem sups : pack1.getSupsList())
            packageRepository.deletePackSupsRelation(pack1.getUid(), sups.getUid());
        packMirror = packageRepository.findByUid(pack1.getUid());
        assertThat(packMirror.getSupsList(), is(new ArrayList()));
    }

}
