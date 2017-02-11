package CDR3Algebra;

import java.awt.Insets;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class ProgressBarTask
  extends SwingWorker<Void, Void>
{
  private JTextArea taskOutput;
  
  public Void doInBackground()
  {
    Random random = new Random();
    int progress = 0;
    int update = 0;
    
    setProgress(0);
    while (progress < 100)
    {
      try
      {
        Thread.sleep(random.nextInt(1000));
      }
      catch (InterruptedException localInterruptedException) {}
      update += 10;
      
      setProgress(progress);
    }
    return null;
  }
  
  public void done()
  {
    Toolkit.getDefaultToolkit().beep();
  }
  
  public ProgressBarTask()
  {
    this.taskOutput = new JTextArea(5, 20);
    this.taskOutput.setMargin(new Insets(5, 5, 5, 5));
    this.taskOutput.setEditable(false);
  }
  
  public void propertyChange(PropertyChangeEvent evt)
  {
    if ("progress" == evt.getPropertyName()) {
      int i = ((Integer)evt.getNewValue()).intValue();
    }
  }
}
