package CDR3Algebra;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.io.FileUtils;

public class GUI2
  extends JFrame
{
  private static HashTableData hashTableData;
  private LinkedList<File> customFiles = new LinkedList();
  private JLabel NumberOfSamplesFilter;
  private ButtonGroup buttonGroup1;
  private JList<String> excludeList;
  private JSlider filter2;
  private JLabel frequencyFilter;
  private JTextField frequencyTextBox;
  private JComboBox<String> jComboBox1;
  private JComboBox<String> jComboBox2;
  private JComboBox<String> jComboBox3;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JLabel jLabel6;
  private JLabel jLabel7;
  private JRadioButton jRadioButton1;
  private JRadioButton jRadioButton2;
  private JScrollPane jScrollPane1;
  private JSeparator jSeparator1;
  private JLabel numberOfSharedSamplesNumberDisplay;
  private JComboBox<String> numbersOptionComboBox;
  private JButton sharedButton;
  
  public GUI2(LinkedList csvFiles)
  {
    initComponents();
    initToolTips();
    for (int i = 0; i < csvFiles.size(); i++) {
      this.jComboBox1.addItem((String)csvFiles.get(i));
    }
    String[] excludeListData = new String[csvFiles.size() / 2];
    for (int i = 0; i < csvFiles.size() / 2; i++) {
      excludeListData[i] = ((String)csvFiles.get(i));
    }
    this.excludeList.setListData(excludeListData);
    
    this.jComboBox3.addItem("Plain_SharedCDR3s.csv");
    this.jComboBox3.addItem("SharedCDR3s.csv");
  }
  
  private void initToolTips()
  {
    this.jLabel5.setToolTipText("<html>Thanks for taking an interest.<br> Most people don't even notice me :(</html>");
    this.jLabel2.setToolTipText("<html>Choose from a list of the orginal samples <br>and an option to show only those shared for each of those samples.</html>");
    this.jComboBox1.setToolTipText("<html>Choose from a list of the orginal samples <br>and an option to show only those shared for each of those samples.</html>");
    this.jLabel4.setToolTipText("<html>Shows the CDR3s shared by all samples.<br> Choose between a plain version with less data <br>and a more advanced one</html>.");
    this.jComboBox3.setToolTipText("<html>Shows the CDR3s shared by all samples.<br> Choose between a plain version with less data <br>and a more advanced one.</html>");
    this.jLabel7.setToolTipText("<html>Choose between the custom files you already generated.</html>");
    this.jComboBox2.setToolTipText("<html>Choose between the custom files you already generated.</html>");
    this.jSeparator1.setToolTipText("<html>I am the great divider:<br> separator of that which is seen and that which is made;<br> bridge between right and left, heaven and earth, light and dark;<br> consumer of oneness and universal love and happiness in the universe!<br> I am the line that draws all conflict: that which labels and divides a world that could be one.<br> Yet do not despair because I am but one of many forms<br> of forces beyond your simplistic recognition,<br> meaning you no harm nor help.<br> Grey is the relatavistic realm that is clouded by the purpose I serve here....<br>oh, I mean I seprate viewing from creating files :)</html>");
    this.jLabel6.setToolTipText("uh...I wasn't expected to be called on.");
    this.jLabel3.setToolTipText("<html>Sets the minimum number of total combinations of a CDR3 for it to be shown in the data.</html>");
    this.numbersOptionComboBox.setToolTipText("<html>Sets the minimum number of total combinations of a CDR3 for it to be shown in the data.</html>");
    this.jLabel1.setToolTipText("<html>Select which samples you want to be excluded from the generated file.<br> All CDR3s found in the selected samples will be left out.<br> Hold the command key to select more than one at a time.<br> Hold the command key to unselect.</html>");
    this.jScrollPane1.setToolTipText("<html>Select which samples you want to be excluded from the generated file.<br> All CDR3s found in the selected samples will be left out.<br> Hold the command key to select more than one at a time.<br> Hold the command key to unselect.</html>");
    this.excludeList.setToolTipText("<html>Select which samples you want to be excluded from the generated file.<br> All CDR3s found in the selected samples will be left out.<br> Hold the command key to select more than one at a time.<br> Hold the command key to unselect.</html>");
    this.frequencyFilter.setToolTipText("<html>Sets a minimum frequency of the CDR3 for it to be shown in the data.</html>");
    this.sharedButton.setToolTipText("<html>Click to generate a file showing the shared CDR3s given the conditions.</html>");
  }
  
  private void initComponents()
  {
    this.buttonGroup1 = new ButtonGroup();
    this.jComboBox1 = new JComboBox();
    this.sharedButton = new JButton();
    this.numbersOptionComboBox = new JComboBox();
    this.frequencyFilter = new JLabel();
    this.jScrollPane1 = new JScrollPane();
    this.excludeList = new JList();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jLabel3 = new JLabel();
    this.jComboBox2 = new JComboBox();
    this.jLabel4 = new JLabel();
    this.jSeparator1 = new JSeparator();
    this.jLabel5 = new JLabel();
    this.jLabel6 = new JLabel();
    this.jComboBox3 = new JComboBox();
    this.jLabel7 = new JLabel();
    this.filter2 = new JSlider();
    this.NumberOfSamplesFilter = new JLabel();
    this.jRadioButton1 = new JRadioButton();
    this.jRadioButton2 = new JRadioButton();
    this.numberOfSharedSamplesNumberDisplay = new JLabel();
    this.frequencyTextBox = new JTextField();
    
    setDefaultCloseOperation(3);
    
    this.jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "Nothing Selected" }));
    this.jComboBox1.setToolTipText("Choose from a list of the orginal samples and an option to show only those shared for each of those samples.");
    this.jComboBox1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.jComboBox1ActionPerformed(evt);
      }
    });
    this.sharedButton.setText("Generate Shared CDR3s");
    this.sharedButton.setToolTipText("Click to generate a file showing the shared CDR3s given the conditions.");
    this.sharedButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.sharedButtonActionPerformed(evt);
      }
    });
    this.numbersOptionComboBox.setModel(new DefaultComboBoxModel(new String[] { "1 (Default)", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
    this.numbersOptionComboBox.setToolTipText("Sets the minimum number of total combinations of a CDR3 for it to be shown in the data. ");
    this.numbersOptionComboBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.numbersOptionComboBoxActionPerformed(evt);
      }
    });
    this.frequencyFilter.setText("Frequency Filter");
    this.frequencyFilter.setToolTipText("Sets a minimum frequency of the CDR3 for it to be shown in the data. ");
    
    this.excludeList.setToolTipText("Select which samples you want to be excluded from hte generated file. All CDR3s found in the selected samples will be left out. Hold the command key to select more than one at a time. Hold the command key to unselect.");
    this.jScrollPane1.setViewportView(this.excludeList);
    
    this.jLabel1.setText("Exclude Samples");
    this.jLabel1.setToolTipText("Select which samples you want to be excluded from hte generated file. All CDR3s found in the selected samples will be left out. Hold the command key to select more than one at a time. Hold the command key to unselect.");
    
    this.jLabel2.setText("View Individual Samples");
    this.jLabel2.setToolTipText("Choose from a list of the orginal samples and an option to show only those shared for each of those samples.");
    
    this.jLabel3.setText("Minimum Total Combinations");
    this.jLabel3.setToolTipText("Sets the minimum number of total combinations of a CDR3 for it to be shown in the data. ");
    
    this.jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "Nothing Selected" }));
    this.jComboBox2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.jComboBox2ActionPerformed(evt);
      }
    });
    this.jLabel4.setText("View Shared by All");
    
    this.jSeparator1.setOrientation(1);
    
    this.jLabel5.setFont(new Font("Dialog", 1, 18));
    this.jLabel5.setText("File Viewing");
    
    this.jLabel6.setFont(new Font("Dialog", 1, 18));
    this.jLabel6.setText("File Creation");
    
    this.jComboBox3.setModel(new DefaultComboBoxModel(new String[] { "Nothing Selected" }));
    this.jComboBox3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.jComboBox3ActionPerformed(evt);
      }
    });
    this.jLabel7.setText("View Recently Made");
    
    this.filter2.setMinimum(2);
    this.filter2.setValue(0);
    this.filter2.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent evt)
      {
        GUI2.this.filter2StateChanged(evt);
      }
    });
    this.NumberOfSamplesFilter.setText("Number of shared samples");
    
    this.buttonGroup1.add(this.jRadioButton1);
    this.jRadioButton1.setText("Exact");
    this.jRadioButton1.setToolTipText("Sets the number of shared samples to be exactly the number of samples specified in the slider above.");
    
    this.buttonGroup1.add(this.jRadioButton2);
    this.jRadioButton2.setSelected(true);
    this.jRadioButton2.setText("Minimum");
    this.jRadioButton2.setToolTipText("Sets the number of shared samples to be atleast the number of samples specified in the slider above.");
    this.jRadioButton2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.jRadioButton2ActionPerformed(evt);
      }
    });
    this.numberOfSharedSamplesNumberDisplay.setText("2");
    
    this.frequencyTextBox.setText("0");
    this.frequencyTextBox.setToolTipText("Sets a minimum frequency of the CDR3 for it to be shown in the data. ");
    this.frequencyTextBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        GUI2.this.frequencyTextBoxActionPerformed(evt);
      }
    });
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
      .createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addGap(38, 38, 38)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel5, -1, -1, 32767)
      .addGap(147, 147, 147))
      .addComponent(this.jComboBox1, 0, -1, 32767)
      .addComponent(this.jComboBox3, 0, -1, 32767)
      .addComponent(this.jComboBox2, 0, -1, 32767)
      .addComponent(this.jLabel7, -1, -1, 32767)
      .addComponent(this.jLabel4, -1, -1, 32767)
      .addComponent(this.jLabel2, -1, -1, 32767))
      .addGap(110, 110, 110)
      .addComponent(this.jSeparator1)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addGap(66, 66, 66)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel1, -1, -1, 32767)
      .addGap(240, 240, 240))
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel3, -1, -1, 32767)
      .addGap(156, 156, 156))
      .addGroup(layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
      .addComponent(this.jScrollPane1, GroupLayout.Alignment.LEADING)
      .addComponent(this.numbersOptionComboBox, GroupLayout.Alignment.LEADING, 0, -1, 32767))
      .addGap(84, 84, 84))
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel6, -1, -1, 32767)
      .addGap(225, 225, 225))
      .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(this.filter2, -1, -1, 32767)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.NumberOfSamplesFilter, -1, -1, 32767)
      .addGap(71, 71, 71))
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jRadioButton2, -1, -1, 32767)
      .addGap(18, 18, 18)
      .addComponent(this.jRadioButton1, -1, -1, 32767)
      .addGap(80, 80, 80)))
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.numberOfSharedSamplesNumberDisplay, -1, -1, 32767)
      .addGap(84, 84, 84))
      .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
      .addComponent(this.frequencyTextBox, GroupLayout.Alignment.LEADING)
      .addComponent(this.frequencyFilter, -1, -1, 32767))
      .addGap(244, 244, 244))))
      .addGroup(layout.createSequentialGroup()
      .addGap(101, 101, 101)
      .addComponent(this.sharedButton, -1, -1, 32767)
      .addGap(127, 127, 127)))));
    
    layout.setVerticalGroup(layout
      .createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(this.jSeparator1, GroupLayout.Alignment.TRAILING)
      .addGroup(layout.createSequentialGroup()
      .addContainerGap()
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel5, -1, -1, 32767)
      .addGap(18, 18, 18)
      .addComponent(this.jLabel2, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.jComboBox1)
      .addGap(51, 51, 51)
      .addComponent(this.jLabel4, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
      .addComponent(this.jComboBox3)
      .addGap(51, 51, 51)
      .addComponent(this.jLabel7, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.jComboBox2)
      .addGap(191, 191, 191))
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.jLabel6, -1, -1, 32767)
      .addGap(18, 18, 18)
      .addComponent(this.jLabel3, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.numbersOptionComboBox)
      .addGap(25, 25, 25)
      .addComponent(this.jLabel1, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
      .addComponent(this.jScrollPane1)
      .addGap(18, 18, 18)
      .addComponent(this.frequencyFilter, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addComponent(this.frequencyTextBox, -2, -1, -2)
      .addGap(29, 29, 29)
      .addComponent(this.NumberOfSamplesFilter, -1, -1, 32767)
      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(this.filter2, -1, -1, 32767)
      .addGroup(layout.createSequentialGroup()
      .addComponent(this.numberOfSharedSamplesNumberDisplay, -1, -1, 32767)
      .addGap(1, 1, 1)))
      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
      .addComponent(this.jRadioButton2, -1, -1, 32767)
      .addComponent(this.jRadioButton1, -1, -1, 32767))
      .addGap(26, 26, 26)
      .addComponent(this.sharedButton, -1, -1, 32767)))
      .addContainerGap()));
    
    pack();
  }
  
  private void jComboBox1ActionPerformed(ActionEvent evt)
  {
    String fileString = (String)this.jComboBox1.getSelectedItem();
    fileString = fileString.substring(fileString.indexOf('-') + 1, fileString.length());
    File selectedFile = new File(fileString);
    if (selectedFile.toString() != "Nothing Selected") {
      try
      {
        Desktop.getDesktop().open(selectedFile);
      }
      catch (IOException ex)
      {
        Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  private void sharedButtonActionPerformed(ActionEvent evt)
  {
    CSVMethods csv = new CSVMethods();
    
    int interestVar = this.numbersOptionComboBox.getSelectedIndex();
    int freqVar = Integer.parseInt(this.frequencyTextBox.getText());
    int sharedNumberVar = Integer.parseInt(this.numberOfSharedSamplesNumberDisplay.getText());
    String inputFileName = "SharedCDR3s.csv";
    String minOrExactString = "";
    if (this.jRadioButton2.isSelected()) {
      minOrExactString = "Min_";
    }
    String outputFileName = "Shared_CDR3s_Min_Total_Combinations" + Integer.toString(interestVar + 1) + "_Freq" + Integer.toString(freqVar) + "_Shared_by_" + minOrExactString + Integer.toString(sharedNumberVar) + ".csv";
    
    int[] excludeListIndices = this.excludeList.getSelectedIndices();
    if (excludeListIndices.length > 0) {
      try
      {
        csv.excludeCDR3("Shared_CDR3_Excluded.csv", excludeListIndices, sharedNumberVar);
        csv.sharedCDR3("Shared_CDR3_Excluded.csv", "SharedCDR3s_Excluded_Final.csv");
        inputFileName = "SharedCDR3s_Excluded_Final.csv";
        
        outputFileName = "Shared_CDR3s_Min_Total_Combinations" + Integer.toString(interestVar + 1) + "_Freq" + Integer.toString(freqVar) + "_Shared_by_" + minOrExactString + Integer.toString(sharedNumberVar) + "_Excluding_";
        for (int i = 0; i < this.excludeList.getSelectedValuesList().size(); i++) {
          if (i == this.excludeList.getSelectedValuesList().size() - 1) {
            outputFileName = outputFileName + ((String)this.excludeList.getSelectedValuesList().get(i)).subSequence(0, ((String)this.excludeList.getSelectedValuesList().get(i)).indexOf('-')) + ".csv";
          } else {
            outputFileName = outputFileName + ((String)this.excludeList.getSelectedValuesList().get(i)).subSequence(0, ((String)this.excludeList.getSelectedValuesList().get(i)).indexOf('-')) + "_";
          }
        }
      }
      catch (IOException ex)
      {
        Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    try
    {
      if (interestVar == 0) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 1, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 1) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 2, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 2) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 3, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 3) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 4, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 4) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 5, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 5) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 6, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 6) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 7, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 7) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 8, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 8) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 9, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      } else if (interestVar == 9) {
        csv.sharedCDR3Notable(inputFileName, outputFileName, 10, freqVar, sharedNumberVar, this.jRadioButton1.isSelected());
      }
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
    }
    File notableFile = new File(outputFileName);
    File notableFileDest = new File(HashTableData.destFilePath + File.separator + "Custom" + File.separator + outputFileName);
    try
    {
      FileUtils.copyFile(notableFile, notableFileDest);
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
    }
    try
    {
      Desktop.getDesktop().open(notableFile);
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.jComboBox2.addItem(outputFileName);
    this.customFiles.add(notableFile);
  }
  
  private void jComboBox2ActionPerformed(ActionEvent evt)
  {
    int fileIndex = this.jComboBox2.getSelectedIndex();
    File file = (File)this.customFiles.get(fileIndex - 1);
    try
    {
      Desktop.getDesktop().open(file);
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private void jComboBox3ActionPerformed(ActionEvent evt)
  {
    int fileIndex = this.jComboBox3.getSelectedIndex();
    
    System.out.println(fileIndex);
    File file;
    File file;
    if (fileIndex == 1) {
      file = new File("Plain_SharedCDR3s.csv");
    } else {
      file = new File("SharedCDR3s.csv");
    }
    try
    {
      Desktop.getDesktop().open(file);
    }
    catch (IOException ex)
    {
      Logger.getLogger(GUI2.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private void numbersOptionComboBoxActionPerformed(ActionEvent evt) {}
  
  private void filter2StateChanged(ChangeEvent evt)
  {
    int value = this.filter2.getValue();
    this.numberOfSharedSamplesNumberDisplay.setText(Integer.toString(value));
    this.filter2.setMaximum(this.jComboBox1.getItemCount() / 2);
  }
  
  private void jRadioButton2ActionPerformed(ActionEvent evt) {}
  
  private void frequencyTextBoxActionPerformed(ActionEvent evt) {}
}
