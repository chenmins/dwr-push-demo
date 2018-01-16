package org.chenmin.push.demo;


import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@ImportResource(locations = "classpath:spring.xml")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		DwrSpringServlet servlet = new DwrSpringServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/dwr/*");
		registrationBean.addInitParameter("debug", "true");
		registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");
		registrationBean.addInitParameter("crossDomainSessionSecurity", "true");
		return registrationBean;
	}
}