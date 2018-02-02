/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.processor;

import com.example.trainingspringbatch.entity.Peserta;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author anggi
 */
public class PesertaItemProcessor implements ItemProcessor<Peserta, Peserta>{

    @Override
    public Peserta process(Peserta i) throws Exception {
        if(i.getNama().equalsIgnoreCase("anggi")){
            i.setNama("Anggi Riyandi");
        }
        return i;
    }
    
}
