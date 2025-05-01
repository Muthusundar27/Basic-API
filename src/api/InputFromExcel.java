package api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.print.DocFlavor.STRING;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InputFromExcel {

	// 1. File input from excel
	public static void main(String[] args) throws IOException {

		String file = "C:\\Users\\91882\\OneDrive\\Desktop\\Sample.xlsx";
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook ws = new XSSFWorkbook(fis);
		XSSFSheet sh = ws.getSheetAt(0);

		int rows = sh.getLastRowNum();
		int col = sh.getRow(0).getLastCellNum();

		for (int r = 0; r <= rows; r++) {
			XSSFRow row = sh.getRow(r);
			if (row == null) {
				continue;
			}
			for (int c = 0; c <= col; c++) {
				XSSFCell cell = row.getCell(c);
				if (cell == null) {
					continue;
				}
//				switch (cell.getCellType()) {
//				case STRING:
//					System.out.println(cell.getStringCellValue());
//					break;
//				case NUMERIC:
//					System.out.println(cell.getNumericCellValue());
//					break;
//				case BOOLEAN:
//					System.out.println(cell.getBooleanCellValue());
//					break;
//				default:
//					System.out.println("Invalid Data");
//					break;
//
//				}

			}

		}
	}
}
