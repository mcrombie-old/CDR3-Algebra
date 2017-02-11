package CDR3Algebra;

import java.util.ArrayList;

public class SampleCsvFiles
{
  public ArrayList<String> list;
  
  public SampleCsvFiles()
  {
    this.list = new ArrayList();
  }
  
  public ArrayList<String> getList()
  {
    return this.list;
  }
  
  public void addToList(String string)
  {
    this.list.add(string);
  }
}
