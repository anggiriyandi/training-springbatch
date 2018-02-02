/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.config;

import com.example.trainingspringbatch.entity.Peserta;
import com.example.trainingspringbatch.mapper.PesertaMapper;
import com.example.trainingspringbatch.processor.PesertaItemProcessor;
import com.example.trainingspringbatch.writter.PesertaItemWritter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author anggi
 */
@Configuration
public class ReadCsvBatchConfiguration {
    
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Value("${file.location}")
    private String fileLocation;

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    
    private  Logger logger = LoggerFactory.getLogger(ReadCsvBatchConfiguration.class);
    
    @Bean
    public FlatFileItemReader<Peserta> reader(){
        FlatFileItemReader<Peserta> reader = new FlatFileItemReader<>();
        
        logger.info("File Location : "+fileLocation);
        reader.setResource(new ClassPathResource(fileLocation));
        
        DefaultLineMapper<Peserta> mapper = new DefaultLineMapper<Peserta>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"nama", "alamat", "tanggalLahir"});
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new PesertaMapper());
        
        reader.setLineMapper(mapper);
        return reader;
    }
    
    @Bean
    public Step readCsvStep (){
        return stepBuilderFactory.get("readCsvStep")
                .<Peserta,Peserta> chunk(1)
                .reader(reader())
                .processor(new PesertaItemProcessor())
                .writer(new PesertaItemWritter())
                .build();
    }
    
    @Bean
    public Job importDataPesertaFromCsv(){
        return jobBuilderFactory.get("importDataPesertaFromCsv")
                .incrementer(new RunIdIncrementer())
                .flow(readCsvStep())
                .end().build();
    }
    
}
