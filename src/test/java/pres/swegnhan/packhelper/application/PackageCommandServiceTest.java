package pres.swegnhan.packhelper.application;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
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
import pres.swegnhan.packhelper.core.PackageCommandItem;
import pres.swegnhan.packhelper.core.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.CategoryDictionary;

import java.io.File;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
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
    private CategoryDictionary categoryDictionary;

    @Autowired
    private PackageCommandService packageCommandService;

    @Autowired
    private SupportSystemCommandService supportSystemCommandService;

    private Package pack4find, pack;

    private PackageCommandItem pci;

    private String packFileName;

    @Before
    public void setUp() throws Exception{
        pack4find = new Package();
        pack4find.setUid("a1d88df3-d038-40d4-b370-cdeb7fe7620f");
        pack4find.setName("apache2");
        pack4find.setVersion("2.4.29-1ubuntu4.5");
        pack4find.setCategory(2);
        pack4find.setDescription("Apache HTTP Server\n" +
                " The Apache HTTP Server Project's goal is to build a secure, efficient and\n" +
                " extensible HTTP server as standards-compliant open source software. The\n" +
                " result has long been the number one web server on the Internet.\n" +
                " .\n" +
                " Installing this package results in a full installation, including the\n" +
                " configuration files, init scripts and support scripts.");
        pack4find.setUrl("/home/swegnhan/IdeaProjects/packhub/apache2_2.4.29-1ubuntu4.5_amd64.deb");
        pack4find.setFiletype(".deb");
        pack4find.setSupsList(Arrays.asList(new SupportSystem(
                "04e4bfeb-a9b4-4be1-a65c-a2176fd186c3",
                "ubuntu",
                "14.04"
        ), new SupportSystem(
                "ee3f4052-3cba-41dd-8bf4-2cf917dbdb62",
                "ubuntu",
                "16.04"
        )));
        pack4find.setCreatedAt(new DateTime("2019-02-19T22:42:38"));
        pack4find.setUpdatedAt(new DateTime("2019-02-19T22:42:38"));
        pack4find.setDownloads(0);
        pci = new PackageCommandItem(
                "",
                "code",
                "1.29.1-1542309157",
                "Development",
                "Code editing. Redefined.\n" +
                        " Visual Studio Code is a new choice of tool that combines the simplicity of a code editor with what developers need for the core edit-build-debug cycle. See https://code.visualstudio.com/docs/setup/linux for installation instructions and FAQ.",
                "",
                ".deb",
                new SupportSystem[]{new SupportSystem(
                        "04e4bfeb-a9b4-4be1-a65c-a2176fd186c3",
                        "ubuntu",
                        "14.04"
                ), new SupportSystem(
                        "ee3f4052-3cba-41dd-8bf4-2cf917dbdb62",
                        "ubuntu",
                        "16.04"
                )},
                "code_1.29.1-1542309157_amd64.deb"
        );
    }

    @Test
    @Transactional
    @Rollback
    public void should_package_find_success() throws Exception{
        Package packMirror = packageCommandService.findByUid(pack4find.getUid()).get();
        assertThat(packMirror.getUid(), equalTo(pack4find.getUid()));
        assertThat(packMirror.getName(), equalTo(pack4find.getName()));
        assertThat(packMirror.getVersion(), equalTo(pack4find.getVersion()));
        assertThat(packMirror.getCategory(), equalTo(pack4find.getCategory()));
        assertThat(packMirror.getDescription(), equalTo(pack4find.getDescription()));
        assertThat(packMirror.getUrl(), equalTo(pack4find.getUrl()));
        assertThat(packMirror.getFiletype(), equalTo(pack4find.getFiletype()));
        assertThat(packMirror.getSupsList(), equalTo(pack4find.getSupsList()));
        assertThat(packMirror.getCreatedAt(), equalTo(pack4find.getCreatedAt()));
        assertThat(packMirror.getUpdatedAt(), equalTo(pack4find.getUpdatedAt()));
        assertThat(packMirror.getDownloads(), equalTo(pack4find.getDownloads()));
    }

    @Test
    @Transactional
    @Rollback
    public void should_package_create_success() throws Exception{
        FileUtils.copyFileToDirectory(new File("src/test/resources/" + pci.getPackFileName()), new File(TEMP_DIR_PATH));
        packageCommandService.create(pci);
        assertThat(new File(TEMP_DIR_PATH + '/' + pci.getPackFileName()).exists(), is(false));
        assertThat(new File(PACK_HUB_PATH + '/' + pci.getPackFileName()).exists(), is(true));
        Package packMirror = packageCommandService.findByNameVersion(pci.getName(), pci.getVersion()).get();
        assertThat(packMirror.getName(), equalTo(pci.getName()));
        assertThat(packMirror.getVersion(), equalTo(pci.getVersion()));
        assertThat(categoryDictionary.id2name(packMirror.getCategory()), equalTo(pci.getCategory()));
        assertThat(packMirror.getSupsList(), equalTo(Arrays.asList(pci.getSupsList())));
        assertThat(packMirror.getDescription(), equalTo(pci.getDescription()));
        assertThat(packMirror.getFiletype(), equalTo(pci.getFiletype()));
        FileUtils.deleteQuietly(new File(PACK_HUB_PATH + '/' + pci.getPackFileName()));
    }

}
