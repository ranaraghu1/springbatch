package com.rana.batch.controller;

import java.util.List;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rana.batch.model.JobParamRequest;
import com.rana.batch.service.JobService;

@RestController
@RequestMapping("api/job")
public class JobController {
	@Autowired
	JobService jobService;
	@Autowired
	JobOperator jobOperator;
	@GetMapping("/start/{jobName}")
	//we can pass list more than one job parameter as list from Json input
	public String startJob(@PathVariable String jobName,@RequestBody List<JobParamRequest> jobParamReqList) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		//Asynch call
		jobService.startService(jobName,jobParamReqList);
		return "Job Started ....";
	}
	
	@GetMapping("/start/{executionID}")
	//we can pass list more than one job parameter as list from Json input
	public String stopJob(@PathVariable long executionID) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, NoSuchJobExecutionException, JobExecutionNotRunningException {
		jobOperator.stop(executionID);
		return "Job Stopped....";
	}

}
