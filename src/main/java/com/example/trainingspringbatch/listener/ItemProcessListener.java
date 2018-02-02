/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.listener;

import com.example.trainingspringbatch.entity.Peserta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.stereotype.Component;

/**
 *
 * @author anggi
 */
@Component
public class ItemProcessListener {
    
    private Logger logger = LoggerFactory.getLogger(ItemProcessListener.class);
    
    @BeforeProcess
    public void beforeProcess(Peserta item){
        
    }
    
    @AfterProcess
    public void afterProcess(Peserta item, Peserta result){
        logger.info("Nama Sebelum DI process : "+item.getNama());
        logger.info("Nama setelah DI process : "+result.getNama());
    };
    
    @OnProcessError
    public void onProcessError(Peserta item, Exception e){
    
    };
}
