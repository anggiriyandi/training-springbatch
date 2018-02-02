/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.processor;

import com.example.trainingspringbatch.entity.Peserta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author anggi
 */
public class PesertaItemProcessor implements ItemProcessor<Peserta, Peserta>{
    
    private Logger  logger = LoggerFactory.getLogger(PesertaItemProcessor.class);

    @Override
    public Peserta process(Peserta i) throws Exception {
        logger.info("======= masuk ke processor =========");
        
        Peserta pesertaBaru = new Peserta();
        pesertaBaru.setNama(i.getNama());
        pesertaBaru.setAlamat(i.getAlamat());
        pesertaBaru.setTanggalLahir(i.getTanggalLahir());
        
        if(i.getNama().equalsIgnoreCase("anggi")){
            pesertaBaru.setNama("Anggi Riyandi");
        }
        return pesertaBaru;
    }
    
}
