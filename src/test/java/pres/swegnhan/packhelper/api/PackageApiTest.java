package pres.swegnhan.packhelper.api;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.PackageCommandItem;
import pres.swegnhan.packhelper.core.SupportSystem;
import pres.swegnhan.packhelper.infrastructure.commandrepository.PackageCommandRepository;

import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @MockBean
    private PackageCommandRepository packageRepository;

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
    public void should_create_package_success() throws Exception{
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
    }

    @Test
    public void should_remove_package_success() throws Exception{
        RestAssuredMockMvc.given().param("packUid", UUID.randomUUID().toString()).
        when().delete("/package").then().statusCode(200);
    }

}
