package com.upp.nc.util;

import java.util.HashMap;
import java.util.List;

import com.upp.nc.dto.FormSubmissionDto;

public class MapListToDTO {
	
	public static HashMap<String, Object> run(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}

}
