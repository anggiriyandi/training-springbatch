/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.config;

import com.example.trainingspringbatch.entity.Peserta;
import com.example.trainingspringbatch.listener.ItemProcessListener;
import com.example.trainingspringbatch.listener.ItemReaderListener;
import com.example.trainingspringbatch.listener.SkipCheckingListener;
import com.example.trainingspringbatch.listener.SkipListener;
import com.example.trainingspringbatch.mapper.PesertaMapper;
import com.example.trainingspringbatch.processor.PesertaItemProcessor;
import com.example.trainingspringbatch.tasklet.DeletePesertaCsvTasklet;
import com.example.trainingspringbatch.writter.PesertaItemWritter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
public class ReadCsvWithSequentialStep {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReaderListener itemReaderListener;

    @Autowired
    private ItemProcessListener itemProcessListener;

    @Autowired
    private SkipCheckingListener skipCheckingListener;

    @Autowired
    private SkipListener skipListener;

    @Autowired
    private PesertaItemWritter itemWritter;

    @Autowired
    private DeletePesertaCsvTasklet deletePesertaCsvTasklet;

    @Value("${file.location}")
    private String fileLocation;

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private Logger logger = LoggerFactory.getLogger(ReadCsvBatchConfiguration.class);

    @Bean
    public FlatFileItemReader<Peserta> itemReaderCsv() {
        FlatFileItemReader<Peserta> reader = new FlatFileItemReader<>();

        logger.info("File Location : " + fileLocation);
        reader.setResource(new ClassPathResource(fileLocation));

        DefaultLineMapper<Peserta> mapper = new DefaultLineMapper<Peserta>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"nama", "alamat", "tanggalLahir"});
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new PesertaMapper());

        reader.setLineMapper(mapper);
        return reader;
    }

    @Bean
    public Step readFileCsvStep1() {
        return stepBuilderFactory.get("readFileCsvStep1")
                .<Peserta, Peserta>chunk(1)
                .reader(itemReaderCsv())
                .processor(new PesertaItemProcessor())
                .writer(itemWritter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .retryLimit(0)
                .allowStartIfComplete(true)
                .listener(skipCheckingListener)
                .listener(itemReaderListener)
                .listener(itemProcessListener)
                .listener(skipListener)
                .build();
    }

    @Bean
    public Job sequentialImportDataPesertaFromCsv() {
        return jobBuilderFactory.get("sequentialImportDataPesertaFromCsv")
                .incrementer(new RunIdIncrementer())

                //sequential step
                .flow(readFileCsvStep1())
                    .next(deleteCsvFile1())
                .end().build();
    }

    @Bean
    public Step deleteCsvFile1() {
        return stepBuilderFactory.get("deleteCsvFile")
                .tasklet(deletePesertaCsvTasklet)
                .build();
    }
}
