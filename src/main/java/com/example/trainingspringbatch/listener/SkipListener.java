/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.listener;

import com.example.trainingspringbatch.dao.PesertaErrorDao;
import com.example.trainingspringbatch.entity.Peserta;
import com.example.trainingspringbatch.entity.PesertaError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author anggi
 */

@Component
public class SkipListener {
    
    private Logger logger = LoggerFactory.getLogger(SkipListener.class);
    
    @Autowired
    private PesertaErrorDao errorDao;

    @OnSkipInRead
    public void onSkipInRead(Throwable t){
    }

    @OnSkipInProcess
    public void onSkipInProcess(Peserta item, Throwable t){
    }

    @OnSkipInWrite
    public void onSkipInWrite(Peserta item, Throwable t){
        logger.info("Data ini Error , disimpan di tempat berbeda !!");
        logger.info("Nama Peserta yang error "+item.getNama());
        
        // data error disimpan di table yang lain
        PesertaError error = new PesertaError();
        error.setNama(item.getNama());
        error.setAlamat(item.getAlamat());
        error.setTanggalLahir(item.getTanggalLahir());
        errorDao.save(error);
    }
}
