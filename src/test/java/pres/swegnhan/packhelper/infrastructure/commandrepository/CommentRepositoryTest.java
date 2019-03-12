package pres.swegnhan.packhelper.infrastructure.commandrepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import pres.swegnhan.packhelper.core.Comment;
import pres.swegnhan.packhelper.core.Package;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@MybatisTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE )
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private  PackageCommandRepository packageCommandRepository;

    private Comment comment;

    private Package pack;



    @Before
    public void setUp() throws Exception{
        pack = new Package(
                "openjdk-8-jre-headless",
                "8u171-b11-0ubuntu0.18.04.1",
                "1",
                "usr/bin/packhelper/openjdk-8-jre-headless_8u171-b11-0ubuntu0.18.04.1_amd64.deb",
                ".deb",
                new String[]{"ubuntu|14.04", "ubuntu|16.04"});

        comment = new Comment();

        comment.setContent("hahahahahahah");

        comment.setCreateAt(new DateTime());

        comment.setIp("255.255.255.255");

        comment.setUid(UUID.randomUUID().toString());

        comment.setPackUid(pack.getUid());

        packageCommandRepository.insert(pack);

    }

    @Test
    public void should_comment_insert_select_success() throws Exception{


        commentRepository.insert(comment);
        Comment cmnt = commentRepository.findByUid(comment.getUid());
//        assertThat(comment.getCreate_at(), equalTo(cmnt.getCreate_at()));
        assertThat(cmnt, equalTo(comment));
    }

//    @Test
//    public void should_package_insert_select_success() throws Exception{
//        packageRepository.insert(pack);
//        assertThat(pack, equalTo(packageRepository.findByUid(pack.getUid())));
//    }
//
    @Test
    public void should_comment_update_success() throws Exception{
        commentRepository.insert(comment);

        comment.setContent("lalalalalala");


        comment.setCreateAt(new DateTime());

        comment.setIp("0.0.0.0");

        commentRepository.update(comment);

        System.out.println(comment);

        Comment cmnt = commentRepository.findByUid(comment.getUid());

        System.out.println(cmnt.toString());

       assertThat(comment, equalTo(commentRepository.findByUid(comment.getUid())));
    }

    @Test
    public void should_comment_insert_select_delete_update_success() throws Exception{
        commentRepository.insert(comment);
        commentRepository.delete(comment.getUid());
        assertThat(commentRepository.findByUid(comment.getUid()), is(nullValue()));
    }

//    @Test
//    public void should_packge_supportsystem_relation_insert_success() throws Exception{
//        packageRepository.insert(pack);
//        for(SupportSystem sups : pack.getSupsList()){
//            if(!packageRepository.findSupportSystem(sups))
//                packageRepository.insertSupportSystem(sups);
//            packageRepository.insertPackSupsRelation(pack.getUid(), sups.getUid());
//        }
//        assertThat(packageRepository.findByNameVersion(pack.getName(), pack.getVersion()), equalTo(pack));
//    }
}
