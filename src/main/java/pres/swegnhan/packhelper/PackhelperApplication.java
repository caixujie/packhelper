package pres.swegnhan.packhelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("pres.swegnhan.packhelper.dao")
public class PackhelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackhelperApplication.class, args);
	}

}
