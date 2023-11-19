package com.xw.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.xw.cloud.mapper")
@SpringBootApplication
public class LibvirtApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibvirtApplication.class, args);
	}
}
