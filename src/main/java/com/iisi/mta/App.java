package com.iisi.mta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.iisi.mta.model.MTAModel;

/**
 * analysisOutput
 *
 */
public class App {

	private final static String path = "./mtareport";
	private static File[] fileList;
	private static String[] outputPathList;

	private final static String projectName = "MOrderEJB";
	private final static String catrgory_mandatory = "mandatory";
	private final static String catrgory_cloudMandatory = "cloud-mandatory";
	
	private final static String OutputPath_Mandatory = "./analysisOutput/" + projectName + "_" + catrgory_mandatory + "_Output.csv";
	private final static String OutputPath_cloudMandatory = "./analysisOutput/" + projectName + "_" + catrgory_cloudMandatory + "_Output.csv";

	public static void main(String[] args) throws IOException {

		getFiles();
		
//		analysisData();
//		outputToFileWhenMandatoryIssue();
//		extractCategorytoCsv(catrgory_cloudMandatory, OutputPath_cloudMandatory);
		outputToFileWhenMandatoryIssue();
		
	}

	private static void analysisData() throws IOException {

		File report = fileList[0];

		FileReader fr = new FileReader(report);
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		while ((str = br.readLine()) != null) {
			if (str.split(",").length != 10) {
				continue;
			}
			MTAModel model = convertStrToMtaModel(str);
			System.out.println(model);
		}
		br.close();
		fr.close();
	}

	private static MTAModel convertStrToMtaModel(String str) {

		String[] data = str.split(",");

		MTAModel model = new MTAModel();

		model.setCategory(removeSymbols(data[1]));
		model.setTitle(removeSymbols(data[2]));
		model.setApplication(removeSymbols(data[5]));
		model.setFilePath(removeSymbols(data[7]));

		return model;
	}

	private static String removeSymbols(String str) {

		return str.replaceAll("\"", "");
	}

//	private file[] void getFiles() {
	private static void getFiles() {
		File mtaSource = new File(path);
		File[] files = mtaSource.listFiles();
		if (files.length != 0) {
			fileList = files;
		} else {
			throw new RuntimeException("沒有MTA報告");
		}
//		return files;
	}
	
	private static void outputToFileWhenMandatoryIssue() throws IOException {
		File report = fileList[0];
		FileReader fr = new FileReader(report);
		BufferedReader br = new BufferedReader(fr);
		
//		File outputFile = fileList[0];
		FileWriter fw = new FileWriter(path + OutputPath_Mandatory);
		BufferedWriter bw = new BufferedWriter(fw); //null to change
		bw.write("Test this line. GO.");
		
		String str = br.readLine();
		while ((str = br.readLine()) != null) {
			if (str.split(",").length != 10) {
				continue;
			}
			MTAModel model = convertStrToMtaModel(str);
			if(model.getCategory().equals(catrgory_mandatory)) {
//				bw.write(model.toString());
			System.out.println(model);
			}
		}
		br.close();
		bw.close();
	}
	
	private static void extractCategorytoCsv(String category, String aimCategoryPath) throws IOException {

		File report = fileList[0];
		List<String> aList = new ArrayList<>();

		FileReader fr = new FileReader(report);
		BufferedReader br = new BufferedReader(fr);
		
		FileWriter fw = new FileWriter(aimCategoryPath);
		BufferedWriter bw = new BufferedWriter(fw); //null to change
		
		String str = br.readLine();
		while ((str = br.readLine()) != null) {
			if (str.split(",").length != 10) {
				continue;
			}
			MTAModel model = convertStrToMtaModel(str);
			if(model.getCategory().equals(category)) {
				aList.add(model.showCategoryTitleApplicationFilepath());
//				System.out.println(model.toString());
			}
		}
		
		for(String strInList: aList) {
//			System.out.println(strInList);
			bw.write(strInList + "\n");
		}
		bw.flush();
		
		bw.close();
		fw.close();
		br.close();
		fr.close();
		System.out.printf("Extract category[%s] DONE.\n", category);
	}
		
}
