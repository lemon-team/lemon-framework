package com.ilemontech.framework.manage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 启动类
 * @author zhaicl
 * @date 2017年8月3日 下午2:00:35
 */
@ServletComponentScan
@SpringBootApplication  
@ImportResource({ "classpath:applicationContext.xml" })
public class ManageApplication extends WebMvcConfigurerAdapter{
	
	private static Logger logger=LoggerFactory.getLogger(ManageApplication.class);
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ManageApplication.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	@Bean
	public ServletContextInitializer initializer() {
	    return new ServletContextInitializer() {

	        @Override
	        public void onStartup(ServletContext servletContext) throws ServletException {
	            servletContext.setAttribute("contextpath", "");	        
	        }
	    };
	}
	/**
	 * 自定义异常
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/jyj/403");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/jyj/404");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/jyj/500");
	            container.addErrorPages(error403Page, error404Page, error500Page);
	        }
	    };
	}
	/**
	 * 配置拦截器
	 */
    public void addInterceptors(InterceptorRegistry registry) {
    	
	}
	
	public static void main(String[] args) {		
		SpringApplication.run(ManageApplication.class, args);
		logger.info("启动完成");
	}	
}
