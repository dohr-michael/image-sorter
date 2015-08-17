package org.dohrm.tools.image.config;

import org.dohrm.tools.image.batch.DirectoryItemReader;
import org.dohrm.tools.image.batch.FileModelItemProcessor;
import org.dohrm.tools.image.batch.FileModelItemWriter;
import org.dohrm.tools.image.model.FileModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author MDO
 * @since 17/08/2015.
 */
@Configuration
public class BatchConfig {

    @Bean
    public ItemReader<File> itemReader(@Value("${input.folder}") String inputDirectory) {
        return new DirectoryItemReader(new File(inputDirectory));
    }

    @Bean
    public ItemWriter<FileModel> itemWriter(@Value("${output.folder}") String outputDirectory) {
        return new FileModelItemWriter(new File(outputDirectory));
    }

    @Bean
    public ItemProcessor<File, FileModel> itemProcessor() {
        return new FileModelItemProcessor();
    }


    @Bean
    public Job job(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("job")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<File> reader,
                      ItemWriter<FileModel> writer, ItemProcessor<File, FileModel> processor) {
        return stepBuilderFactory.get("step1")
                .<File, FileModel>chunk(20)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
