package com.rana.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.rana.batch.listener.FirstJobListener;
import com.rana.batch.listener.FirstStepListener;
import com.rana.batch.processor.FirstItemProcessor;
import com.rana.batch.reader.FirstItemReader;
import com.rana.batch.service.SecondTasklet;
import com.rana.batch.writer.FirstItemWriter;
@Component
public class SampleJob {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private SecondTasklet secondTasklet;
	@Autowired
	private FirstJobListener fisFirstJiobListener;
	@Autowired
	private FirstStepListener firstStepListener;
	@Autowired
	private FirstItemReader itemReader;
	@Autowired
	private FirstItemWriter iteWriter;
	@Autowired
	FirstItemProcessor itemProcessor;
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("first job")
		.start(firstStep())
		.next(secondStep())
		.listener(fisFirstJiobListener)
		.build();
	}
 private Step firstStep() {
	 return stepBuilderFactory.get("first step")
	 .tasklet(firstTask())
	 .listener(firstStepListener)
	 .build();
 }
 
 private Step secondStep() {
	 return stepBuilderFactory.get("second step")
	 .tasklet(secondTasklet).build();
 }
 
 private Tasklet firstTask() {
	 return new Tasklet() {

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			System.out.println("This is first tasklet step");
			return RepeatStatus.FINISHED;
		}
		 
	 };
	 
	 
	 
 }
 
	/*
	 * private Tasklet secondTask() { return new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception {
	 * System.out.println("This is second tasklet step"); return
	 * RepeatStatus.FINISHED; }
	 * 
	 * };
	 * 
	 * }
	 */
 
 //Chunk-oriented steps
    @Bean
	public Job secondJob() {
		return jobBuilderFactory.get("second job")
		.incrementer(new RunIdIncrementer())
		.start(firstChunkStep())
		.build();
	}
    
    private Step firstChunkStep() {
   	 return stepBuilderFactory.get("first  Chunk step")
   			 .<Integer,Long>chunk(3)
   			 .reader(itemReader)
   			 .processor(itemProcessor)
   			 .writer(iteWriter)
   			 .build();
    }
    
}
