package CDR3Algebra;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class HashTableData
{
  LinkedList<HashMap> hashTables;
  HashMap<String, LinkedList> data;
  static LinkedList<File> customFiles;
  static HashMap<String, File> sharedByAllFiles;
  static HashMap<String, Integer> numberOfShared = new HashMap();
  static LinkedList<HashSet<String>> samples = new LinkedList();
  int copyTotal;
  static String destFilePath;
  
  public HashTableData()
  {
    this.data = new HashMap();
    this.hashTables = new LinkedList();
    this.copyTotal = 0;
    customFiles = new LinkedList();
    sharedByAllFiles = new HashMap();
  }
}
