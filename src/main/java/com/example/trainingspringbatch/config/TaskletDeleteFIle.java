/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.config;

import com.example.trainingspringbatch.tasklet.DeletePesertaCsvTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author anggi
 */
@Configuration
public class TaskletDeleteFIle {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private DeletePesertaCsvTasklet deletePesertaCsvTasklet;

    @Bean
    public Step deleteFileStep() {
        return stepBuilderFactory.get("deleteFileStep")
                .tasklet(deletePesertaCsvTasklet)
                .build();
    }
    
    @Bean
    public Job deleteFileJob() {
        return jobBuilderFactory.get("deleteFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(deleteFileStep())
                .end().build();
    }
}
