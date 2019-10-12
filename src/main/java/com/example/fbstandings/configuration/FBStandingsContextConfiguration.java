package com.example.fbstandings.configuration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.http.HTTPBinding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.example.fbstandings.service.FBService;

import ch.qos.logback.classic.pattern.MessageConverter;

@Configuration
public class FBStandingsContextConfiguration {

	@Bean
	RestOperations rest() {
		RestTemplate rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		List<HttpMessageConverter<?>> converters = rest.getMessageConverters();
		MediaType supportedMedia = new MediaType("application", "json", Charset.defaultCharset());
		converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).forEach(c -> {
			((MappingJackson2HttpMessageConverter)c).setSupportedMediaTypes(new ArrayList<MediaType>(){{
				add(supportedMedia);
			}});
		});
		return rest;
	}
	
	@Bean
	FBService fbservice(RestOperations rest) {
		return new FBService(rest);
	} 
}
