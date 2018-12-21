package com.ssm.pojo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MutipleFileDomain {
	private List<String> description;
	private List<MultipartFile> myFiles;
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	public List<MultipartFile> getMyFiles() {
		return myFiles;
	}
	public void setMyFiles(List<MultipartFile> myFiles) {
		this.myFiles = myFiles;
	}
	
}
