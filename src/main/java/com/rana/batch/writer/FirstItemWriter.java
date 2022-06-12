package com.rana.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
@Component
public class FirstItemWriter implements ItemWriter<Long> {

	@Override
	//Size of list=our chunk size
	public void write(List<? extends Long> items) throws Exception {
		System.out.println("Inside Writer");
		items.stream().forEach(System.out::println);
		
	}

}
