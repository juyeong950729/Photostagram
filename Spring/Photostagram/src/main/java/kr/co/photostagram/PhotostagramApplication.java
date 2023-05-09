package kr.co.photostagram;

import groovy.util.logging.Slf4j;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@MapperScan("kr.co.photostagram.dao")
@SpringBootApplication
public class PhotostagramApplication {

	public static void main(String[] args) {
		try{

			log.info("Starting Photostagram!!!!");

			SpringApplication.run(PhotostagramApplication.class, args);

		}catch(Exception e){
			log.error("e : " + e.getMessage());
		}

	}

}
