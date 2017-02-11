package CDR3Algebra;

import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;

public class main
{
  private static final ArrayList<String> sampleFiles = new ArrayList();
  
  public static void main(String[] args)
    throws FileNotFoundException, IOException
  {
    GUI gui = new GUI();
    gui.setTitle("The Michael Crombie CDR3 Algebra Experience");
    gui.setVisible(true);
  }
  
  public static void generateCsvFiles()
    throws IOException
  {
    File dir = new File("/Users/michaelcrombie/Desktop/BAH");
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (int i = 1; i < directoryListing.length; i++) {
        System.out.println(directoryListing[i]);
      }
    }
    CSVMethods csv = new CSVMethods();
    for (int i = 1; i < directoryListing.length; i++) {
      csv.copySampleCsv(directoryListing[i].toString(), "/Users/michaelcrombie/Desktop/Sample" + Integer.toString(i) + ".csv");
    }
    String generatedSharedCsvFile = "/Users/michaelcrombie/Desktop/Generated_Shared_CDR3.csv";
    csv.sharedCDR3(generatedSharedCsvFile, "/Users/michaelcrombie/Desktop/SharedCDR3s.csv");
    for (int i = 0; i < directoryListing.length - 1; i++) {
      csv.sharedFromSample(generatedSharedCsvFile, i, "/Users/michaelcrombie/Desktop/SharedFromSample" + Integer.toString(i + 1) + ".csv");
    }
  }
  
  public static void test()
    throws FileNotFoundException, IOException
  {
    CsvReader f1 = new CsvReader("/Users/michaelcrombie/Desktop/Generated_Shared_CDR3.csv");
    CsvReader f1Counter = new CsvReader("/Users/michaelcrombie/Desktop/Generated_Shared_CDR3.csv");
    CsvReader f2 = new CsvReader("/Users/michaelcrombie/Desktop/shared_cdr3_Anqelp.csv");
    CsvReader f2Counter = new CsvReader("/Users/michaelcrombie/Desktop/shared_cdr3_Anqelp.csv");
    
    int f1Rows = countRows(f1Counter);
    int f2Rows = countRows(f2Counter);
    
    HashSet tempSet = new HashSet();
    for (int i = 0; i < f2Rows - 1; i++)
    {
      f2.readRecord();
      tempSet.add(f2.get(0));
    }
    for (int j = 0; j < f1Rows - 1; j++)
    {
      f1.readRecord();
      if (!tempSet.contains(f1.get(0))) {
        System.out.println(f1.get(0));
      }
    }
  }
  
  public static int countRows(CsvReader CsvFile)
    throws FileNotFoundException, IOException
  {
    int count = 0;
    while (CsvFile.readRecord()) {
      count++;
    }
    return count;
  }
}
