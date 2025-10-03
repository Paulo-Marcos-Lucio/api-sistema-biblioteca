package com.devteam.biblioteca.web.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Problem {
	
	private LocalDateTime timestamp;
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		private String userMessage;
	}
	

}
