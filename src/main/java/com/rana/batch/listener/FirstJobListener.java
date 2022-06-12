package com.rana.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
@Component
public class FirstJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Before Job Name: "+jobExecution.getJobInstance().getJobName());
		System.out.println("Job Param: "+jobExecution.getJobParameters());
		System.out.println("Job Exec context: "+jobExecution.getExecutionContext());
		jobExecution.getExecutionContext().put("Jec", "pass to next job");

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("After Job Name: "+jobExecution.getJobInstance().getJobName());
		System.out.println("Job Param: "+jobExecution.getJobParameters());
		System.out.println("Job Exec context: "+jobExecution.getExecutionContext());


	}

}
