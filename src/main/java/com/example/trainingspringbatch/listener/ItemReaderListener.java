/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.stereotype.Component;

/**
 *
 * @author anggi
 */

@Component
public class ItemReaderListener {
    
    private Logger logger = LoggerFactory.getLogger(ItemReaderListener.class);
    
    
    @BeforeRead
    public void beforeRead(){
        logger.info("interceptor sebelum baca file");
    }
    
    @AfterRead
    public void afterRead(){
        logger.info("interceptor setelah baca file");
    }
    
    @OnReadError
    public void onReadError(Exception e){
        logger.info("interceptor ketika ada yang error : "+e.getMessage());
    }
    
}
