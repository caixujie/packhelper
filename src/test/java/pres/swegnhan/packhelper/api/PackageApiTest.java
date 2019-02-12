package pres.swegnhan.packhelper.api;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.io.FileUtils;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.Package;
import pres.swegnhan.packhelper.core.PackageCommandItem;
import pres.swegnhan.packhelper.core.SupportSystem;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@WebMvcTest({PackageApi.class})
@RunWith(SpringRunner.class)
public class PackageApiTest {

    @Value("${pers.swegnhan.packhelper.debhubpath}")
    private String PACK_HUB_PATH;

    @Value("${pres.swegnhan.packhelper.tempdirpath}")
    private String TEMP_DIR_PATH;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PackageCommandService packageCommandService;

    private PackageCommandItem pci;

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.mockMvc(mvc);
        pci = new PackageCommandItem();
        pci.setName("apache2");
        pci.setVersion("2.4.29-1ubuntu4.5");
        pci.setCategory("1");
        pci.setFiletype(".deb");
        pci.setSupsList(new SupportSystem[]{new SupportSystem("ubuntu|16.04")});
        pci.setPackFileName("apache2_2.4.29-1ubuntu4.5_amd64.deb");
    }

    @Test
    @Rollback
    public void should_create_package_success() throws Exception{
        FileUtils.copyFileToDirectory(new File("src/test/resources/" + pci.getPackFileName()), new File(TEMP_DIR_PATH));
        RestAssuredMockMvc.given().contentType("application/json").body(new HashMap<String, Object>(){
            {
                {
                    put("name", pci.getName());
                    put("version", pci.getVersion());
                    put("category", pci.getCategory());
                    put("filetype", pci.getFiletype());
                    put("supsList", pci.getSupsList());
                    put("packFileName", pci.getPackFileName());
                }
            }
        }).when().post("/package").then().statusCode(200);
        Package packMirror = packageCommandService.findByNameVersion(pci.getName(), pci.getVersion()).get();
        assertThat(pci.getName(), equalTo(packMirror.getName()));
        assertThat(pci.getVersion(), equalTo(packMirror.getVersion()));
        assertThat(Integer.valueOf(pci.getCategory()), equalTo(packMirror.getCategory()));
        assertThat(pci.getFiletype(), equalTo(packMirror.getFiletype()));
        assertThat(Arrays.asList(pci.getSupsList()), equalTo(packMirror.getSupsList()));
        assertThat(new File(PACK_HUB_PATH + '/' + pci.getPackFileName()).exists(), is(true));
        FileUtils.deleteQuietly(new File(PACK_HUB_PATH + '/' + pci.getPackFileName()));
    }

}
