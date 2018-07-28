package com.experimentos.batch.configuration;

import com.experimentos.batch.model.User;
import org.hibernate.annotations.Parameter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /*@Bean
    @StepScope
    public FlatFileItemReader<User> reader(@Value("#{jobParameters['filename']}") String filename) {
        return new FlatFileItemReaderBuilder<User>()
                .name("UserItemReader")
                .resource(new ClassPathResource(filename))
                .linesToSkip(1)
                .delimited()
                .names(new String[] { "id", "name", "dept", "salary" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                    setTargetType(User.class);
                }})
                .build();
    }*/

    @Bean
    public UserItemProcessor processor() { return new UserItemProcessor(); }

    @Bean
    public UserItemWriter writer() { return new UserItemWriter(); }

    @Bean
    @StepScope
    public ItemReaderUser reader() { return new ItemReaderUser(); }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(ItemReader<? extends User> reader,
                     ItemProcessor<User, User> processor,
                     ItemWriter<? super User> writer) {
        return stepBuilderFactory.get("step")
                .<User, User>chunk(300)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
