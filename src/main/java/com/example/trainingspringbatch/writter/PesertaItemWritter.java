/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.writter;

import com.example.trainingspringbatch.entity.Peserta;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

/**
 *
 * @author anggi
 */
public class PesertaItemWritter implements ItemWriter<Peserta>{

    private Logger logger = LoggerFactory.getLogger(PesertaItemWritter.class);
    private DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    
    @Override
    public void write(List<? extends Peserta> list) throws Exception {
        logger.info("====== start writting ======");
        
        for (Peserta peserta : list) {
            logger.info("Nama : {}", peserta.getNama());
            logger.info("alamat : {}", peserta.getAlamat());
            logger.info("tanggal Lahir : {}", dateFormat.format(peserta.getTanggalLahir()));
        }
    }
    
}
