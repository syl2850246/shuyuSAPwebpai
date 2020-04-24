package com.sypm.webapi.config;

import com.sypm.webapi.webService.CommonService;
import com.sypm.webapi.webService.CommonServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(),"/webServiceDemo/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

//    @Bean
//    public UserService userService() {
//        return new UserServiceImpl();
//    }

    @Bean
    public CommonService commonService() {
        return new CommonServiceImpl();
    }

//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
//        endpoint.publish("/api");
//        return endpoint;
//    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), commonService());
        endpoint.publish("/CommonService");
        return endpoint;
    }
}
