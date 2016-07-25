package com.example.mongo.mongodemo.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 
 * @author arijeet
 *
 */
@Configuration
@ComponentScan(basePackages = {"com.example"})
@Import({WebMVCConfig.class})
@EnableAspectJAutoProxy
@EnableMBeanExport
@EnableScheduling
public class ApplicationConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
	 /**
     * Content negotiating view resolver.
     *
     * @return the content negotiating view resolver
     */
    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = contentNegotiationManagerFactoryBean();
        MappingJackson2JsonView defaultView = new MappingJackson2JsonView();
        defaultView.setExtractValueFromSingleKeyModel(true);

        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());
        contentViewResolver.setDefaultViews(Arrays.<View> asList(defaultView));
        return contentViewResolver;
    }

    /**
     * Content negotiation manager factory bean.
     *
     * @return the content negotiation manager factory bean
     */
    private ContentNegotiationManagerFactoryBean contentNegotiationManagerFactoryBean() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        contentNegotiationManager.addMediaTypes(mediaTypes);
        return contentNegotiationManager;
    }

    /**
     * Request mapping handler mapping.
     *
     * @return the request mapping handler mapping
     */
    @Bean(name = "handlerMapping")
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        return requestMappingHandlerMapping;
    }
    
    @Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		LOGGER.debug("Adding config for internal resource view resolver");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}