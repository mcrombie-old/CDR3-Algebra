package CDR3Algebra;

import com.csvreader.CsvReader;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;

public class GUI
  extends JFrame
{
  private File selectedFile;
  private static HashTableData hashTableData;
  private SampleCsvFiles sampleCsvFiles = new SampleCsvFiles();
  LinkedList csvFiles;
  private int progress = 0;
  private JButton chooseDirectory;
  private JLabel fileDisplay;
  private JButton generateButton;
  private JLabel info;
  private JLabel jLabel1;
  private JTextField projectName;
  
  public GUI()
  {
    initComponents();
    initToolTips();
  }
  
  private void initToolTips()
  {
    this.fileDisplay.setToolTipText("Where your directory is...");
    this.chooseDirectory.setToolTipText("<html>Click to select the directory that <br>contains all of your pep.csv files to be analyzed. <br> WARNING - make sure there is nothing else in the directory but these files. </html>");
    this.jLabel1.setToolTipText("<html>Enter the name of your project. <br>Do not reuse names as this may result <br>in data being overwritten.</html>");
    this.projectName.setToolTipText("<html>Enter the name of your project here.<br> Do not reuse names as this may result <br>in data being overwritten.</html>");
    this.generateButton.setToolTipText("<html>Generates the shared CDR3s between <br>the files given in the directory. </html>");
  }
  
  private void initComponents()
  {
    this.generateButton = new JButton();
    this.chooseDirectory = new JButton();
    this.fileDisplay = new JLabel();
    this.projectName = new JTextField();
    this.jLabel1 = new JLabel();
    this.info = new JLabel();
    
    setDefaultCloseOperation(3);
    
    this.generateButton.setText("Generate");
    this.generateButton.setToolTipText("Generates the shared CDR3s between the files given in hte directory. ");
    this.generateButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI.this.generateButtonActionPerformed(evt);
      }
    });
    this.chooseDirectory.setText("Choose Directory");
    this.chooseDirectory.setToolTipText("\n\"<html>\"Click to select the directory that contains all of your pep.csv files to be analyzed. \"<br>\" WARNING - make sure there is nothing else in the directory but these files. </html>\"\n\n");
    this.chooseDirectory.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI.this.chooseDirectoryActionPerformed(evt);
      }
    });
    this.fileDisplay.setHorizontalAlignment(0);
    this.fileDisplay.setText("No Directory Selected");
    this.fileDisplay.setToolTipText("Where your directory is...");
    this.fileDisplay.setHorizontalTextPosition(0);
    
    this.projectName.setText("Name me please");
    this.projectName.setToolTipText("Enter the name of your project here. Do not reuse names as this may result in data being overwritten. ");
    this.projectName.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI.this.projectNameActionPerformed(evt);
      }
    });
    this.jLabel1.setText("Project Name");
    this.jLabel1.setToolTipText("Enter the name of your project here. Do not reuse names as this may result in data being overwritten. ");
    
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
      .createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
      .addContainerGap(-1, 32767)
      .addComponent(this.info)
      .addGap(415, 415, 415))
      .addGroup(layout.createSequentialGroup()
      .addGap(329, 329, 329)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel1, -1, 86, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.projectName, -2, 149, -2))
      .addComponent(this.generateButton, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
      .addComponent(this.fileDisplay, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
      .addComponent(this.chooseDirectory, GroupLayout.Alignment.TRAILING, -1, -1, 32767))
      .addGap(316, 316, 316)));
    
    layout.setVerticalGroup(layout
      .createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addGap(103, 103, 103)
      .addComponent(this.fileDisplay, -1, 18, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
      .addComponent(this.chooseDirectory, -1, 28, 32767)
      .addGap(27, 27, 27)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
      .addComponent(this.projectName, -2, 22, -2)
      .addComponent(this.jLabel1, -1, -1, 32767))
      .addGap(18, 18, 18)
      .addComponent(this.generateButton, -1, 28, 32767)
      .addGap(54, 54, 54)
      .addComponent(this.info)
      .addGap(90, 90, 90)));
    
    pack();
  }
  
  private void generateButtonActionPerformed(ActionEvent evt)
  {
    if (this.fileDisplay.getText() == "No Directory Selected") {
      this.info.setText("Hmmmmm. Maybe pick a directory?");
    } else {
      this.info.setText("Generating...");
    }
    FileLoader loader = new FileLoader(this);
    loader.execute();
    try
    {
      this.csvFiles = generateCsvFiles(this.selectedFile);
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
    }
    GUI2 gui2 = new GUI2(this.csvFiles);
    gui2.setVisible(true);
    dispose();
  }
  
  private void chooseDirectoryActionPerformed(ActionEvent evt)
  {
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setFileSelectionMode(1);
    jFileChooser.setCurrentDirectory(new File("/User"));
    int result = jFileChooser.showOpenDialog(new JFrame());
    if (result == 0)
    {
      this.selectedFile = jFileChooser.getSelectedFile();
      
      this.fileDisplay.setText("Selected file: " + this.selectedFile.getAbsolutePath());
      this.fileDisplay.setFocusable(true);
      
      String[] excludeListValues = new String[this.selectedFile.listFiles().length];
      File[] directoryListing = this.selectedFile.listFiles();
      for (int i = 1; i < this.selectedFile.listFiles().length; i++) {
        excludeListValues[i] = directoryListing[i].toString();
      }
    }
  }
  
  private void projectNameActionPerformed(ActionEvent evt) {}
  
  public class PeskyFilter
    implements FileFilter
  {
    public PeskyFilter() {}
    
    public boolean accept(File pathname)
    {
      return !pathname.getName().equals(".DS_Store");
    }
  }
  
  public LinkedList generateCsvFiles(File dir)
    throws IOException
  {
    LinkedList names = new LinkedList();
    LinkedList files = new LinkedList();
    
    File[] directoryListing = dir.listFiles(new PeskyFilter());
    System.out.println(directoryListing.length);
    if (directoryListing != null) {
      for (File child : directoryListing) {
        System.out.println(child);
      }
    }
    String destFilePath = dir.getParentFile().toString();
    
    CSVMethods csv = new CSVMethods();
    for (int i = 0; i < directoryListing.length; i++)
    {
      String name = directoryListing[i].getName();
      names.add(name);
      csv.copySampleCsv(directoryListing[i].toString(), name);
      files.add("Sample " + Integer.toString(i + 1) + "-" + name);
      File sampleFile = new File(name);
      File sampleFileDest = new File(destFilePath + File.separator + this.projectName.getText() + File.separator + "Samples" + File.separator + "/Sample" + Integer.toString(i + 1) + "-" + name);
      FileUtils.copyFile(sampleFile, sampleFileDest);
      this.progress += 1;
    }
    String generatedSharedCsvFile = "Generated_Shared_CDR3.csv";
    
    csv.sharedCDR3(generatedSharedCsvFile, "SharedCDR3s.csv");
    
    File plainSharedFile = new File("Generated_Shared_CDR3.csv");
    FileUtils.copyFile(plainSharedFile, new File("Plain_SharedCDR3s.csv"));
    File plainSharedFileDest = new File(destFilePath + File.separator + this.projectName.getText() + File.separator + "Plain_SharedCDR3s.csv");
    FileUtils.copyFile(plainSharedFile, plainSharedFileDest);
    HashTableData.sharedByAllFiles.put("Plain_SharedCDR3s.csv", plainSharedFileDest);
    
    File sharedFile = new File("SharedCDR3s.csv");
    File sharedFileDest = new File(destFilePath + File.separator + this.projectName.getText() + File.separator + "SharedCDR3s.csv");
    FileUtils.copyFile(sharedFile, sharedFileDest);
    HashTableData.sharedByAllFiles.put("SharedCDR3s.csv", sharedFileDest);
    for (int i = 0; i < directoryListing.length; i++)
    {
      System.out.println((String)names.get(i));
      
      csv.sharedFromSample(generatedSharedCsvFile, i, "Shared_from_" + (String)names.get(i));
      files.add("Sample " + Integer.toString(i + 1) + "-" + "Shared_from_" + names.get(i));
      File sharedSampleFile = new File("Shared_from_" + (String)names.get(i));
      File sharedSampleFileDest = new File(destFilePath + File.separator + this.projectName.getText() + File.separator + "Shared_By_Individual_Sample" + File.separator + "Shared_from_" + "Sample" + Integer.toString(i + 1) + "-" + (String)names.get(i));
      FileUtils.copyFile(sharedSampleFile, sharedSampleFileDest);
    }
    HashTableData.destFilePath = destFilePath + File.separator + this.projectName.getText();
    
    return files;
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
  
  public class ProgressBar
  {
    public ProgressBar() {}
    
    public void run()
    {
      SwingWorker<Void, Integer> worker = new SwingWorker()
      {
        protected Void doInBackground()
          throws Exception
        {
          publish(new Integer[] { Integer.valueOf(10) });
          
          return null;
        }
        
        protected void process(List<Integer> chunks)
        {
          int value = ((Integer)chunks.get(chunks.size() - 1)).intValue();
        }
        
        protected void done() {}
      };
      worker.execute();
    }
  }
}
