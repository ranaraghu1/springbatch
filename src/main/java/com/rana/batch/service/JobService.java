package com.rana.batch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rana.batch.model.JobParamRequest;

@Service
public class JobService {
	@Autowired
	JobLauncher jobLauncher;
	@Qualifier("firstJob")
	@Autowired
	Job firstJob;
	@Qualifier("secondJob")
	@Autowired
	Job secondJob;
@Async	
public void startService(String jobName,List<JobParamRequest> jobParamReqList) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
	Map<String,JobParameter> params=new HashMap<>();
	params.put("current time", new JobParameter(System.currentTimeMillis()));
	jobParamReqList.stream().forEach(jobParamReq->{
		params.put(jobParamReq.getParamKey(), new JobParameter(jobParamReq.getParamValue()));
		
	});
	JobParameters jobParameters=new JobParameters(params);
	if(jobName.equals("first job")) {
		jobLauncher.run(firstJob, jobParameters);
	}
	else if(jobName.equals("second job")) {
		jobLauncher.run(secondJob, jobParameters);
	}

}
}
