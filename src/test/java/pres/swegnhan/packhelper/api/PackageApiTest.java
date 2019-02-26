package pres.swegnhan.packhelper.api;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pres.swegnhan.packhelper.application.commandservice.PackageCommandService;
import pres.swegnhan.packhelper.core.Package;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@WebMvcTest({PackageApi.class})
public class PackageApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PackageCommandService packageCommandService;

    private Package pack;

    @Before
    public void setUp() throws Exception{
        RestAssuredMockMvc.mockMvc(mvc);
        pack = new Package(
                "apache2",
                "2.4.29-1ubuntu4.5",
                "1",
                "",
                ".deb",
                new String[]{}
        );
    }

//    @Test
//    @Rollback
//    @Transactional
//    public void should_create_package_success() throws Exception{
//        RestAssuredMockMvc.given().
//                param("name", pack.getName()).
//                param("version", pack.getVersion()).
//                param("category", pack.getCategory()).
//                param("filetype", pack.getFiletype()).
//                param("supsList", pack.getSupsList()).
//                when().post("/package").
//                then().statusCode(200).body("content", equalTo(nullValue()));
//    }

}
