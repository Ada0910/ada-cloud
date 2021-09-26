package com.ada.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description Nacos 启动类
 *
 * @return
 * @Author Ada
 * @Date 23:24 2021/9/26
 **/
@SpringBootApplication(scanBasePackages = "com.ada.nacos")
@ServletComponentScan
@EnableScheduling
public class NacosApplication {

	/**
	 * 是否单机模式启动
	 */
	private static String standalone = "true";

	/**
	 * 是否开启鉴权
	 */
	private static String enabled = "false";

	public static void main(String[] args) {
		System.setProperty("nacos.standalone", standalone);
		System.setProperty("nacos.core.auth.enabled", enabled);
		System.setProperty("server.tomcat.basedir", "logs");
		SpringApplication.run(NacosApplication.class, args);
	}

}
