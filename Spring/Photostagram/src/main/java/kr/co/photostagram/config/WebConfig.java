package kr.co.photostagram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // --------------------- 외부 경로 파일 참조 ---------------------
    // ResourceHandler를 통해 기능 구현
    // 1) WebMvcConfigurer 구현
    // 2) addResourceHandler 구현
    // 만약 spring-security 사용 시, 추가 설정 필요


    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler("/thumb/**")   // 클라이언트가 파일에 접근하기 위해 요청하는 url
            .addResourceLocations(resourceLoader.getResource("file:thumb/"));    // 실제 리소스가 존재하는 외부 경로
    }

}
