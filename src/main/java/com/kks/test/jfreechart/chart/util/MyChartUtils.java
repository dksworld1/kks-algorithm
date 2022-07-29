package com.kks.test.jfreechart.chart.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

public class MyChartUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public static void drawJpegImage(JFreeChart chart, String filePath, int width, int height) throws IOException {
		File jpeg = new File(filePath);

		String fileExtension = FilenameUtils.getExtension(jpeg.getName());
		if(!"jpeg".equals(fileExtension)) {	    	  
			throw new IllegalArgumentException(String.format("Not support such file extension: {%s}", fileExtension));
		}

		try {
			Files.createDirectories(jpeg.toPath().getParent());	
		} catch (FileAlreadyExistsException e) { }
      
		ChartUtils.saveChartAsJPEG(jpeg ,chart, width ,height);
	}
	
	public static void drawJpegImage(JFreeChart chart, String filePath) throws IOException {
		int width = 640;    /* Width of the image */
		int height = 480;   /* Height of the image */ 
		MyChartUtils.drawJpegImage(chart, filePath, width, height);
	}

}
