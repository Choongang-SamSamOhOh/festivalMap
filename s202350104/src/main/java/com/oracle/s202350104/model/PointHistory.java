package com.oracle.s202350104.model;

import java.util.Date;

import lombok.Data;

@Data
public class PointHistory {
	
	private int 	user_id;
	private int 	point_id;
	private Date 	created_at;
	
	//조회용
	private String	title;
	private String	content;
	private String	point;
}
