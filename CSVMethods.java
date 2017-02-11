package CDR3Algebra;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;

public class CSVMethods
{
  private final HashTableData hashTableData = new HashTableData();
  public static ArrayList<HashTableData> listoFHashTableDatasForEachSample = new ArrayList();
  public static int numberOfSamples = 0;
  
  public void generateSharedCDR3(Set CDR3sSet)
    throws FileNotFoundException, IOException
  {
    CsvWriter out = new CsvWriter("Generated_Shared_CDR3.csv");
    
    String[] titleArray = new String[listoFHashTableDatasForEachSample.size() + 1];
    titleArray[0] = "CDR3";
    for (int i = 0; i < listoFHashTableDatasForEachSample.size(); i++)
    {
      String pathName = (String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(i)).data.get("Name")).get(0);
      titleArray[(i + 1)] = ((String)pathName.subSequence(pathName.lastIndexOf(47) + 1, pathName.length()));
    }
    out.writeRecord(titleArray);
    
    String[] CDR3Array = (String[])CDR3sSet.toArray(new String[CDR3sSet.size()]);
    
    Iterator iter = CDR3sSet.iterator();
    while (iter.hasNext())
    {
      String CDR3 = (String)iter.next();
      
      String[] array = new String[listoFHashTableDatasForEachSample.size() + 1];
      array[0] = CDR3;
      for (int i = 0; i < listoFHashTableDatasForEachSample.size(); i++) {
        if (((HashTableData)listoFHashTableDatasForEachSample.get(i)).data.containsKey(CDR3 + "restOfRow"))
        {
          int totalCopyNum = Integer.parseInt((String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(i)).data.get(CDR3 + "restOfRow")).get(22));
          if (findTotalCDR3inSample(i, CDR3) > 1) {
            for (int j = 2; j < findTotalCDR3inSample(i, CDR3) + 1; j++)
            {
              int nextCopyNum = Integer.parseInt((String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(i)).data.get(CDR3 + "DuplicateCDR3s" + Integer.toString(j))).get(22));
              totalCopyNum += nextCopyNum;
            }
          }
          float result = 1.0E7F / ((HashTableData)listoFHashTableDatasForEachSample.get(i)).copyTotal * totalCopyNum;
          
          array[(i + 1)] = Integer.toString((int)result);
        }
      }
      out.writeRecord(array);
    }
    out.close();
  }
  
  public void sharedCDR3(String inFile, String outFile)
    throws FileNotFoundException, IOException
  {
    CsvReader csvCountRows = new CsvReader(inFile);
    CsvReader csv = new CsvReader(inFile);
    CsvWriter out = new CsvWriter(outFile);
    
    int rowCount = countRows(csvCountRows);
    
    String[] sampleNumbersRow = new String[numberOfSamples + 2];
    for (int i = 0; i < numberOfSamples; i++) {
      sampleNumbersRow[(i + 2)] = ("Sample " + Integer.toString(i + 1));
    }
    out.writeRecord(sampleNumbersRow);
    
    csv.readRecord();
    String[] titleRow = new String[4];
    titleRow[0] = "Number of samples sharing";
    titleRow[1] = "V_Genes";
    titleRow[2] = "Total_Combinations";
    titleRow[3] = "Copy numbers for each v gene";
    String[] CDR3AndFrequenciesTitle = csv.getValues();
    String[] CDR3Title = new String[1];
    CDR3Title[0] = "CDR3";
    CDR3AndFrequenciesTitle[0] = "Sample_Number";
    CDR3AndFrequenciesTitle = (String[])ArrayUtils.addAll(CDR3Title, CDR3AndFrequenciesTitle);
    titleRow = (String[])ArrayUtils.addAll(CDR3AndFrequenciesTitle, titleRow);
    out.writeRecord(titleRow);
    for (int i = 0; i < rowCount - 1; i++)
    {
      csv.readRecord();
      
      int SampleCount2 = 0;
      for (int j = 0; j < numberOfSamples * 2 - 1; j += 2)
      {
        if (((HashTableData)listoFHashTableDatasForEachSample.get(SampleCount2)).data.containsKey(csv.get(0)))
        {
          int numberOfShared = 0;
          for (int q = 1; q < csv.getValues().length; q++) {
            if (csv.get(q) != "") {
              numberOfShared++;
            }
          }
          String[] CDR3AndFrequencies = csv.getValues();
          String[] CDR3 = new String[1];
          CDR3[0] = CDR3AndFrequencies[0];
          
          CDR3AndFrequencies[0] = Integer.toString(SampleCount2 + 1);
          
          CDR3AndFrequencies = (String[])ArrayUtils.addAll(CDR3, CDR3AndFrequencies);
          
          String numberOfSharedString = Integer.toString(numberOfShared);
          String[] vGenes = findVinSample(SampleCount2, csv.get(0));
          String vGenesString = ArrayToString(vGenes);
          String[] array = new String[3];
          array[0] = numberOfSharedString;
          array[1] = vGenesString;
          array[2] = Integer.toString(findTotalCDR3inSample(SampleCount2, csv.get(0)));
          
          String[] copyNumbers = new String[((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(SampleCount2)).data.get(csv.get(0) + "copyNumber" + Integer.toString(SampleCount2 + 1))).size()];
          for (int q = 0; q < ((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(SampleCount2)).data.get(csv.get(0) + "copyNumber" + Integer.toString(SampleCount2 + 1))).size(); q++) {
            copyNumbers[q] = ((String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(SampleCount2)).data.get(csv.get(0) + "copyNumber" + Integer.toString(SampleCount2 + 1))).get(q));
          }
          String[] write = (String[])ArrayUtils.addAll(CDR3AndFrequencies, array);
          
          write = (String[])ArrayUtils.addAll(write, copyNumbers);
          out.writeRecord(write);
        }
        SampleCount2++;
      }
    }
    out.close();
  }
  
  public void sharedCDR3Notable(String sharedCDR3sFile, String outFile, int interestVar, int freqVar, int numberOfShared, boolean minOrExact)
    throws FileNotFoundException, IOException
  {
    CsvReader csvCountRows = new CsvReader(sharedCDR3sFile);
    CsvReader csv = new CsvReader(sharedCDR3sFile);
    CsvWriter out = new CsvWriter(outFile);
    
    int rowCount = countRows(csvCountRows);
    
    csv.readRecord();
    out.writeRecord(csv.getValues());
    csv.readRecord();
    out.writeRecord(csv.getValues());
    for (int i = 0; i < rowCount - 2; i++)
    {
      csv.readRecord();
      
      int numberOfSharedPosition = numberOfSamples + 2;
      int totalCombinationsPosition = numberOfSamples + 4;
      boolean passes = true;
      if ((csv.get(totalCombinationsPosition) != "") && 
        (Integer.parseInt(csv.get(totalCombinationsPosition)) < interestVar)) {
        passes = false;
      }
      if (csv.get(numberOfSharedPosition) != "") {
        if (minOrExact == true)
        {
          if (Integer.parseInt(csv.get(numberOfSharedPosition)) != numberOfShared) {
            passes = false;
          }
        }
        else if ((!minOrExact) && 
          (Integer.parseInt(csv.get(numberOfSharedPosition)) < numberOfShared)) {
          passes = false;
        }
      }
      if (Integer.parseInt(csv.get(Integer.parseInt(csv.get(1)) + 1)) < freqVar) {
        passes = false;
      }
      if (passes == true) {
        out.writeRecord(csv.getValues());
      }
    }
    out.close();
  }
  
  public void excludeCDR3(String outputFile, int[] excludeListIndices, int sharedNumberVar)
    throws FileNotFoundException, IOException
  {
    CsvWriter out = new CsvWriter(outputFile);
    
    CsvReader csvCountRows = new CsvReader("Generated_Shared_CDR3.csv");
    CsvReader csv1 = new CsvReader("Generated_Shared_CDR3.csv");
    CsvReader csv2 = new CsvReader("Generated_Shared_CDR3.csv");
    CsvReader csv3 = new CsvReader("Generated_Shared_CDR3.csv");
    
    int rowCount = countRows(csvCountRows);
    
    HashSet CDR3s = new HashSet();
    for (int i = 0; i < rowCount; i++)
    {
      csv1.readRecord();
      CDR3s.add(csv1.get(0));
    }
    for (int i = 0; i < excludeListIndices.length; i++)
    {
      HashSet set = (HashSet)HashTableData.samples.get(excludeListIndices[i] + 1);
      CDR3s.removeAll(set);
    }
    for (int i = 0; i < rowCount + 1; i++)
    {
      csv2.readRecord();
      if (CDR3s.contains(csv2.get(0))) {
        out.writeRecord(csv2.getValues());
      }
    }
    out.close();
  }
  
  public int findTotalCDR3inSample(int sampleNumber, String cdr3)
  {
    HashTableData sampleHashMap = (HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber);
    int returnNumber = ((LinkedList)sampleHashMap.data.get(cdr3)).size();
    
    return returnNumber;
  }
  
  public String[] findVinAllSamples(String cdr3)
  {
    HashSet set = new HashSet();
    for (int i = 0; i < ((LinkedList)this.hashTableData.data.get(cdr3)).size(); i++) {
      set.add((String)((LinkedList)this.hashTableData.data.get(cdr3)).get(i));
    }
    String[] returnArray = (String[])set.toArray(new String[set.size()]);
    
    return returnArray;
  }
  
  public String[] findVinSample(int sampleNumber, String cdr3)
  {
    HashTableData sampleHashMap = (HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber);
    
    HashSet set = new HashSet();
    for (int i = 0; i < ((LinkedList)sampleHashMap.data.get(cdr3)).size(); i++) {
      set.add((String)((LinkedList)sampleHashMap.data.get(cdr3)).get(i));
    }
    String[] returnArray = (String[])set.toArray(new String[set.size()]);
    
    return returnArray;
  }
  
  public String ArrayToString(String[] array)
  {
    String string = "";
    for (int i = 0; i < array.length; i++) {
      if (i < array.length - 1) {
        string = string + array[i] + " , ";
      } else {
        string = string + array[i];
      }
    }
    return string;
  }
  
  public void copyCsv(String inFile, String outFile)
    throws FileNotFoundException, IOException
  {
    CsvReader csvCountRows = new CsvReader(inFile);
    CsvReader csv = new CsvReader(inFile);
    
    CsvWriter out = new CsvWriter(outFile);
    
    int rowCount = countRows(csvCountRows);
    for (int i = 0; i < rowCount; i++)
    {
      csv.readRecord();
      out.writeRecord(csv.getValues());
    }
    out.close();
  }
  
  public void copySampleCsv(String inFile, String outFile)
    throws FileNotFoundException, IOException
  {
    numberOfSamples += 1;
    
    HashTableData sampleHashTable = new HashTableData();
    
    LinkedList name = new LinkedList();
    name.add(inFile);
    sampleHashTable.data.put("Name", name);
    
    CsvReader csvCountRows = new CsvReader(inFile);
    CsvReader csv = new CsvReader(inFile);
    CsvWriter out = new CsvWriter(outFile);
    
    int rowCount = countRows(csvCountRows);
    if (numberOfSamples == 1)
    {
      HashTableData.samples.add(new HashSet());
      HashTableData.samples.add(new HashSet());
    }
    else
    {
      HashTableData.samples.add(new HashSet());
    }
    csv.readRecord();
    out.write("Order_Sample" + Integer.toString(numberOfSamples));
    out.writeRecord(csv.getValues());
    for (int i = 0; i < rowCount - 1; i++)
    {
      csv.readRecord();
      
      int CDR3Num = i + 1;
      LinkedList orderNumber = new LinkedList();
      orderNumber.add(Integer.toString(CDR3Num));
      if (this.hashTableData.data.containsKey(csv.get(0)))
      {
        ((LinkedList)this.hashTableData.data.get(csv.get(0))).add(csv.get(1));
      }
      else
      {
        LinkedList list1 = new LinkedList();
        list1.add(csv.get(1));
        this.hashTableData.data.put(csv.get(0), list1);
      }
      if (sampleHashTable.data.containsKey(csv.get(0)))
      {
        ((LinkedList)sampleHashTable.data.get(csv.get(0))).add(csv.get(1));
        ((LinkedList)sampleHashTable.data.get(csv.get(0) + "count")).add("another CDR3!");
        
        LinkedList restOfRow = new LinkedList();
        for (int j = 1; j < 24; j++) {
          restOfRow.add(csv.get(j));
        }
        sampleHashTable.data.put(csv.get(0) + "DuplicateCDR3s" + Integer.toString(((LinkedList)sampleHashTable.data.get(new StringBuilder().append(csv.get(0)).append("count").toString())).size()), restOfRow);
        
        sampleHashTable.data.put(csv.get(0) + "orderNumber" + Integer.toString(((LinkedList)sampleHashTable.data.get(new StringBuilder().append(csv.get(0)).append("count").toString())).size()), orderNumber);
        
        ((LinkedList)sampleHashTable.data.get(csv.get(0) + "copyNumber" + Integer.toString(numberOfSamples))).add(csv.get(1));
        ((LinkedList)sampleHashTable.data.get(csv.get(0) + "copyNumber" + Integer.toString(numberOfSamples))).add(csv.get(23));
        
        sampleHashTable.copyTotal += Integer.parseInt(csv.get(23));
      }
      else
      {
        LinkedList list2 = new LinkedList();
        list2.add(csv.get(1));
        sampleHashTable.data.put(csv.get(0), list2);
        
        LinkedList countList = new LinkedList();
        countList.add("a CDR3!");
        sampleHashTable.data.put(csv.get(0) + "count", countList);
        
        LinkedList restOfRow = new LinkedList();
        for (int j = 1; j < 24; j++) {
          restOfRow.add(csv.get(j));
        }
        sampleHashTable.data.put(csv.get(0) + "restOfRow", restOfRow);
        
        sampleHashTable.data.put(csv.get(0) + "orderNumber", orderNumber);
        
        LinkedList copyNumbers = new LinkedList();
        copyNumbers.add(csv.get(1));
        copyNumbers.add(csv.get(23));
        sampleHashTable.data.put(csv.get(0) + "copyNumber" + Integer.toString(numberOfSamples), copyNumbers);
        
        sampleHashTable.copyTotal += Integer.parseInt(csv.get(23));
      }
      if (numberOfSamples == 1) {
        ((HashSet)HashTableData.samples.get(1)).add(csv.get(0));
      } else {
        ((HashSet)HashTableData.samples.get(numberOfSamples)).add(csv.get(0));
      }
      out.write(Integer.toString(CDR3Num));
      out.writeRecord(csv.getValues());
    }
    listoFHashTableDatasForEachSample.add(sampleHashTable);
    if (numberOfSamples > 1)
    {
      HashSet tempSet = new HashSet();
      HashSet returnSet = new HashSet();
      for (int i = 1; i < numberOfSamples; i++) {
        for (int j = i + 1; j < numberOfSamples + 1; j++)
        {
          tempSet.addAll((Collection)HashTableData.samples.get(i));
          tempSet.retainAll((Collection)HashTableData.samples.get(j));
          returnSet.addAll(tempSet);
          tempSet.clear();
        }
      }
      returnSet.remove("*");
      StarFilterPredicate<String> filter = new StarFilterPredicate();
      returnSet.removeIf(filter);
      
      generateSharedCDR3(returnSet);
    }
    out.close();
  }
  
  public void sharedFromSample(String sharedFile, int sampleNumber, String outFile)
    throws FileNotFoundException, IOException
  {
    CsvReader csvCountRows = new CsvReader(sharedFile);
    CsvReader sharedCsv = new CsvReader(sharedFile);
    
    CsvWriter out = new CsvWriter(outFile);
    
    int rowCount = countRows(csvCountRows);
    
    String[] titleRow = { "V", "VRefBegin", "VRefEnd", "VReadBegin", "VReadEnd", "D", "DRefBegin", "DRefEnd", "DReadBegin", "DReadEnd", "J", "JRefBegin", "JRefEnd", "JReadBegin", "JReadEnd", "C", "CRefBegin", "CRefEnd", "CReadBegin", "CReadEnd", "joinedSeq", "CDR3(nuc)", "Copy", "Sample order in Raw Data", "total Combinations" };
    
    sharedCsv.readRecord();
    out.write(sharedCsv.get(0));
    out.writeRecord(titleRow);
    for (int i = 0; i < rowCount - 1; i++)
    {
      sharedCsv.readRecord();
      if (((HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber)).data.containsKey(sharedCsv.get(0)))
      {
        out.write(sharedCsv.get(0));
        
        LinkedList listOfData = (LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber)).data.get(sharedCsv.get(0) + "restOfRow");
        listOfData.add((String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber)).data.get(sharedCsv.get(0) + "orderNumber")).get(0));
        listOfData.add(Integer.toString(findTotalCDR3inSample(sampleNumber, sharedCsv.get(0))));
        String[] arrayOfData = LinkedListToArray(listOfData);
        
        out.writeRecord(arrayOfData);
        if (findTotalCDR3inSample(sampleNumber, sharedCsv.get(0)) > 1) {
          for (int j = 2; j < findTotalCDR3inSample(sampleNumber, sharedCsv.get(0)) + 1; j++)
          {
            LinkedList listOfDataForOtherCDR3 = (LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber)).data.get(sharedCsv.get(0) + "DuplicateCDR3s" + Integer.toString(j));
            listOfDataForOtherCDR3.add((String)((LinkedList)((HashTableData)listoFHashTableDatasForEachSample.get(sampleNumber)).data.get(sharedCsv.get(0) + "orderNumber" + Integer.toString(j))).get(0));
            listOfDataForOtherCDR3.add(Integer.toString(findTotalCDR3inSample(sampleNumber, sharedCsv.get(0))));
            String[] listOfDataForOtherCDR3_array = LinkedListToArray(listOfDataForOtherCDR3);
            out.write(sharedCsv.get(0) + " (REPEAT)");
            out.writeRecord(listOfDataForOtherCDR3_array);
          }
        }
      }
    }
    out.close();
  }
  
  public void countNumberOfSharedSamples(String fileString)
    throws FileNotFoundException, IOException
  {
    CsvReader csvCountRows = new CsvReader(fileString);
    CsvReader csv = new CsvReader(fileString);
    
    int rowCount = countRows(csvCountRows);
    
    csv.readRecord();
    for (int i = 0; i < rowCount - 1; i++)
    {
      csv.readRecord();
      int numberOfShared = 0;
      for (int j = 1; j < numberOfSamples + 1; j++) {
        if (csv.get(j) != "") {
          numberOfShared++;
        }
      }
      HashTableData.numberOfShared.put(csv.get(0), Integer.valueOf(numberOfShared));
    }
  }
  
  private String[] LinkedListToArray(LinkedList list)
  {
    String[] array = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = ((String)list.get(i));
    }
    return array;
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
