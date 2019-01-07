package pres.swegnhan.packhelper.core;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PackageTest {

    @Test
    public void should_get_right_package() throws Exception{
        Package packPackage = new Package(
                "openjdk-8-jre-headless",
                "8u171-b11-0ubuntu0.18.04.1",
                "2",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});
        assertThat(packPackage.getSupsList(), equalTo(Arrays.asList(
                new SupportSystem("ubuntu", "14.04"),
                new SupportSystem("ubuntu", "16.04"))));
    }

}
