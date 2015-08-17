package org.dohrm.tools.image;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author MDO
 * @since 11/08/2015.
 */
@SpringBootApplication
@EnableBatchProcessing
public class Application {

    /**
     * Main application.
     *
     * @param args params.
     */
    public static void main(String[] args) {
        System.exit(
                SpringApplication.exit(
                        SpringApplication.run(Application.class, args)
                )
        );
    }

}
