package com.iisi.mta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.iisi.mta.model.MTAModel;

/**
 * Hello world!
 *
 */
public class App {

	private final static String path = "./mtareport";
	private static File[] fileList;

	public static void main(String[] args) throws IOException {

		getFiles();
		analysisData();

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

	private static File[] getFiles() {
		File mtaSource = new File(path);
		File[] files = mtaSource.listFiles();
		if (files.length != 0) {
			fileList = files;
		} else {
			throw new RuntimeException("沒有MTA報告");
		}
		return files;
	}
}
