/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.dao;

import com.example.trainingspringbatch.entity.Peserta;
import com.example.trainingspringbatch.entity.PesertaError;
import java.io.Serializable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author anggi
 */
public interface PesertaErrorDao extends PagingAndSortingRepository<PesertaError, String>{
    
}
