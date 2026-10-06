package com.saodi.config;


import com.saodi.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("--------跨域配置----------");
        /*
* addMapping：配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。 
* allowCredentials：是否开启Cookie
* allowedMethods：允许的请求方式，如：POST、GET、PUT、DELETE等。
* allowedOrigins：允许访问的url，可以固定单条或者多条内容
* allowedHeaders：允许的请求header，可以自定义设置任意请求头信息。 
* maxAge：配置预检请求的有效时间
*/
        registry.addMapping("/**")
//            .allowedOriginPatterns("http://localhost:8081")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .exposedHeaders(HttpHeaders.SET_COOKIE)
            .maxAge(3600);
    }

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //拦截地址
                .addPathPatterns("/**")
                //放行地址
                .excludePathPatterns("/user/login/**","/user/captcha","/cinema/**","/user/register/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/swagger-ui/*");
    }
}