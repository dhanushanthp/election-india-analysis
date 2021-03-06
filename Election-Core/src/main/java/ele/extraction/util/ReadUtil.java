package ele.extraction.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import ele.extraction.india.conf.Config;

public class ReadUtil {
	public String getRawText(String filePath, int startPage)
			throws FileNotFoundException, IOException {
		PDFParser parser = new PDFParser(new FileInputStream(filePath));
		parser.parse();

		COSDocument cosDoc = parser.getDocument();
		PDDocument pdDoc = new PDDocument(cosDoc);

		PDFTextStripper pdfStripper = null;
		int number_of_pages = pdDoc.getNumberOfPages();

		pdfStripper = new PDFTextStripper();
		pdfStripper.setStartPage(startPage);
		pdfStripper.setEndPage(number_of_pages);
		String parsedText = pdfStripper.getText(pdDoc).toLowerCase();
		cosDoc.close();
		pdDoc.close();
		return parsedText;
	}

	public static List<String> getConstituencies() {
		BufferedReader br = null;
		List<String> constituency = new ArrayList<String>();
		try {
			String sCurrentLine;
			ClassLoader classLoader = ReadUtil.class.getClassLoader();
			br = new BufferedReader(new FileReader(new File(classLoader
					.getResource("constituency.list").getFile())));
			while ((sCurrentLine = br.readLine()) != null) {
				constituency.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return constituency;
	}
	
	public static List<String> getResultData(String year) {
		BufferedReader br = null;
		List<String> constituency = new ArrayList<String>();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(Config.getResultPath() + year+".csv"));
			while ((sCurrentLine = br.readLine()) != null) {
				constituency.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return constituency;
	}

	public static void main(String[] args) {
		System.out.println(getResultData("2014"));
	}
}
