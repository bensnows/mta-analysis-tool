package com.iisi.mta.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MTAModel {

	private String category;
	private String title;
	private String application;
	private String filePath;

	@Override
	public String toString() {
		return "MTAModel [category=" + category + ", title=" + title + ", application=" + application + ", filePath="
				+ filePath + "]";
	}
	
	public String showCategoryTitleApplicationFilepath() {
		return category + "," + title + "," + application + "," + filePath;
	}

}
