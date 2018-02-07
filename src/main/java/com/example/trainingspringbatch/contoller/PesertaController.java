/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.trainingspringbatch.contoller;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author anggi
 */
@RestController
public class PesertaController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importDataPesertaFromCsv")
    private Job importDataPesertaFromCsv;

    @Autowired
    @Qualifier("conditionalImportDataPesertaFromCsv")
    private Job conditionalImportDataPesertaFromCsv;

    @Autowired
    @Qualifier("importDataPesertaFromCsvParameter")
    private Job importDataPesertaFromCsvParameter;
    
    @Autowired
    @Qualifier("splitImportDataPesertaFromCsv")
    private Job splitImportDataPesertaFromCsv;
    
     
    @Autowired
    @Qualifier("exportPesertaJob")
    private Job exportPesertaJob;

    private Logger logger = LoggerFactory.getLogger(PesertaController.class);

    
//    contoh menjalankan job via api
//    ===================================
    @GetMapping("/runPesertaJob")
    public String pesertaBatchJob() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("JobId", "60") //mengirim parameter ke job
                    .toJobParameters();
            jobLauncher.run(importDataPesertaFromCsv, parameters);

        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ", ex.getMessage(), ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : " + ex.getMessage();
        }
        return "JOB DONE !!";
    }
    
//    menjalankan conditionl batch step
    @GetMapping("/conditionalBatchJob")
    public String conditionalBatchJob() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("JobId", "1") //mengirim parameter ke job
                    .toJobParameters();
            jobLauncher.run(conditionalImportDataPesertaFromCsv, parameters);

        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ", ex.getMessage(), ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : " + ex.getMessage();
        }
        return "JOB DONE !!";
    }
    
//    menjalankan split batch step
    @GetMapping("/splitBatchJob")
    public String splitBatchJob() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("JobId", "1") //mengirim parameter ke job
                    .toJobParameters();
            jobLauncher.run(splitImportDataPesertaFromCsv, parameters);

        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ", ex.getMessage(), ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : " + ex.getMessage();
        }
        return "JOB DONE !!";
    }

    //running raad file with parameter file name
    // baca file dari folder, kemudian jika extention nya csv baru di proses
    @GetMapping("/runPesertaJobParameter")
    public String pesertaBatchReaderFromParameter() {
        
//  lokasi folder disesuaikan dengan kebutuhan
        final File folder = new File("/home/anggi/tes");
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile()) {
                    String path = fileEntry.getAbsolutePath();
                    
//                    validasi file nya csv apa bukan
                    String extention = path.substring(path.lastIndexOf(".") + 1);
                    logger.info("file extention : " + extention);
                    if (extention.equalsIgnoreCase("csv")) {
                        JobParameters parameters = new JobParametersBuilder()
                                .addString("runId", "1") //mengirim parameter ke job
                                .addString("fileName", fileEntry.getAbsolutePath()) //mengirim parameter ke job
                                .toJobParameters();
                        jobLauncher.run(importDataPesertaFromCsvParameter, parameters);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ", ex.getMessage(), ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : " + ex.getMessage();
        }
        return "JOB DONE !!";
    }
    
   //menjalankan export peserta ke csv
   @GetMapping("/runExportPesertaJob")
    public String runExportPesertaBatchJob(){
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("JobId", "31")
                    .toJobParameters();
            jobLauncher.run(exportPesertaJob, parameters);
        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ",ex.getMessage(),ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : "+ex.getMessage();
        } 
        return "JOB DONE !!";
    }

    //menjalankan job menggunakan scheduler
    //contoh scheduled run job
//    @Scheduled(cron = "* */5 * * * *")
    public String runPesertaBatchJobScheduled() {
        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("JobId", "50") //mengirim parameter ke job
                    .toJobParameters();
            jobLauncher.run(importDataPesertaFromCsv, parameters);

        } catch (Exception ex) {
            logger.error("ERROR LAUNCH importDataPesertaFromCsvJob : ", ex.getMessage(), ex);
            return "ERROR LAUNCH importDataPesertaFromCsvJob : " + ex.getMessage();
        }
        return "JOB DONE !!";
    }
}