package pres.swegnhan.packhelper.application;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.application.commandservice.SupportSystemCommandService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class PackageCommandServiceTest {

    @Value("${pers.swegnhan.packhelper.debhubpath}")
    private String PACK_HUB_PATH;

    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;

    @Autowired
    private PackageCommandService packageCommandService;

    @Autowired
    private SupportSystemCommandService supportSystemCommandService;

    private Package pack;

    private String packFileName;

    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "apache2",
                "2.4.29-1ubuntu4.5",
                "1",
                "",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
        packFileName = "apache2_2.4.29-1ubuntu4.5_amd64.deb";
    }

    @Test
    @Transactional
    @Rollback
    public void should_package_create_update_delete_success() throws Exception{
        FileUtils.copyFileToDirectory(new File("src/test/resources/" + packFileName), new File(TEMP_DIR_PATH));
        for(SupportSystem sups : pack.getSupsList())
            supportSystemCommandService.create(sups);
        packageCommandService.create(pack, packFileName);
        assertThat(new File(TEMP_DIR_PATH + '/' + packFileName).exists(), is(false));
        assertThat(new File(PACK_HUB_PATH + '/' + packFileName).exists(), is(true));
        Package packMirror = packageCommandService.findByNameVersion(pack.getName(), pack.getVersion()).get();
        assertThat(pack, equalTo(packMirror));
        for(SupportSystem sups : pack.getSupsList())
            supportSystemCommandService.remove(sups.getUid());
        packageCommandService.remove(pack.getUid());
        assertThat(new File(PACK_HUB_PATH + '/' + packFileName).exists(), is(false));
        assertThat(Optional.empty(), equalTo(packageCommandService.findByUid(pack.getUid())));
    }

    @Test
    public void for_debug() throws Exception{
        System.out.println(packageCommandService);
    }

}
