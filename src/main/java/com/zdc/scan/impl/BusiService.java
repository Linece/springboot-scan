package com.zdc.scan.impl;

import com.zdc.scan.annotation.OdfBean;
import com.zdc.scan.enums.DocumentType;
import com.zdc.scan.inter.OdfSchedule;

@OdfBean(documentTypes = {DocumentType.DT_SCHEDULE_UPDATE,DocumentType.DT_SCHEDULE})
public class BusiService{

	public void aa(){
		System.out.println("#################");
	}
}
