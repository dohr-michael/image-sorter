package org.dohrm.tools.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author MDO
 * @since 11/08/2015.
 */
@SpringBootApplication
@EnableIntegration
public class Application {

    /**
     * Main application.
     *
     * @param args params.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
