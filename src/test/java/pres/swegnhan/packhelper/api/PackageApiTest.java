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

import java.io.File;
import java.util.Arrays;

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

    private TransPackageData tpd;

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.mockMvc(mvc);
        tpd = new TransPackageData();
        tpd.setName("apache2");
        tpd.setVersion("2.4.29-1ubuntu4.5");
        tpd.setCategory("1");
        tpd.setFiletype(".deb");
        tpd.setFilename("apache2_2.4.29-1ubuntu4.5_amd64.deb");
    }

    @Test
    @Rollback
    public void should_create_package_success() throws Exception{
        FileUtils.copyFileToDirectory(new File("src/test/resources/" + tpd.getFilename()), new File(TEMP_DIR_PATH));
        RestAssuredMockMvc.given().
                param("name", tpd.getName()).
                param("version", tpd.getVersion()).
                param("category", tpd.getCategory()).
                param("filetype", tpd.getFiletype()).
                param("supsList", Arrays.asList(tpd.getSupsList())).
                param("filename", tpd.getFilename()).
                when().post("/package").
                then().statusCode(200).body("content", equalTo(""));
        FileUtils.deleteQuietly(new File(PACK_HUB_PATH + '/' + tpd.getFilename()));
    }

}
