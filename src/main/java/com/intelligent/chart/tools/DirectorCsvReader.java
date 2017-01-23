package com.intelligent.chart.tools;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jerry on 2017/1/17.
 */
public class DirectorCsvReader {

    public static void mainOther(String[] args) {
        try {

            CSVReader csvReader = new CSVReader(new FileReader("/Users/Jerry/Documents/MingRule/directorBoxOffice.csv"));
            String [] nextLine;

            int id = 1;

            while ((nextLine = csvReader.readNext()) != null) {

                String name = nextLine[1].trim();
                String sales = nextLine[7].trim();

                if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(sales)) {

                    String salesNumber = sales.substring(0, sales.toCharArray().length - 1);
                    String unitNUmber = sales.substring(sales.toCharArray().length - 1);
                    try {
                        Float salesInYi = null;
                        if (unitNUmber.equals("万")) {

                            salesInYi = new Float(salesNumber) / 10000;

                        } else {
                            salesInYi = new Float(salesNumber);
                        }


                        System.out.println("" + id + "," + name + "," + salesInYi + "," + "亿");
                        id ++;
                    } catch (NumberFormatException e) {

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
