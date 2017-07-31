package com.suncj;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 基础bean配置
 * 
 * @author scj
 *
 */
@Configuration
@ImportResource(locations = { "classpath*:app-*.xml" })
public class ApplicationConfiguration {

}
