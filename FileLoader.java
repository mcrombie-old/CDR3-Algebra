package CDR3Algebra;

import java.awt.Cursor;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

class FileLoader
  extends SwingWorker<String, Void>
{
  private final JFrame frame;
  
  public FileLoader(JFrame frame)
  {
    frame.setCursor(Cursor.getPredefinedCursor(3));
    this.frame = frame;
  }
  
  public String doInBackground()
    throws IOException
  {
    String result = null;
    
    return result;
  }
  
  /* Error */
  public void done()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 6	CDR3Algebra/FileLoader:get	()Ljava/lang/Object;
    //   4: checkcast 7	java/lang/String
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 5	CDR3Algebra/FileLoader:frame	Ljavax/swing/JFrame;
    //   12: invokestatic 8	java/awt/Cursor:getDefaultCursor	()Ljava/awt/Cursor;
    //   15: invokevirtual 4	javax/swing/JFrame:setCursor	(Ljava/awt/Cursor;)V
    //   18: goto +30 -> 48
    //   21: astore_1
    //   22: aload_0
    //   23: getfield 5	CDR3Algebra/FileLoader:frame	Ljavax/swing/JFrame;
    //   26: invokestatic 8	java/awt/Cursor:getDefaultCursor	()Ljava/awt/Cursor;
    //   29: invokevirtual 4	javax/swing/JFrame:setCursor	(Ljava/awt/Cursor;)V
    //   32: goto +16 -> 48
    //   35: astore_2
    //   36: aload_0
    //   37: getfield 5	CDR3Algebra/FileLoader:frame	Ljavax/swing/JFrame;
    //   40: invokestatic 8	java/awt/Cursor:getDefaultCursor	()Ljava/awt/Cursor;
    //   43: invokevirtual 4	javax/swing/JFrame:setCursor	(Ljava/awt/Cursor;)V
    //   46: aload_2
    //   47: athrow
    //   48: return
    // Line number table:
    //   Java source line #36	-> byte code offset #0
    //   Java source line #41	-> byte code offset #8
    //   Java source line #42	-> byte code offset #18
    //   Java source line #38	-> byte code offset #21
    //   Java source line #41	-> byte code offset #22
    //   Java source line #42	-> byte code offset #32
    //   Java source line #41	-> byte code offset #35
    //   Java source line #43	-> byte code offset #48
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	49	0	this	FileLoader
    //   7	1	1	str	String
    //   21	1	1	localExecutionException	java.util.concurrent.ExecutionException
    //   21	1	1	localExecutionException1	java.util.concurrent.ExecutionException
    //   35	12	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	8	21	java/util/concurrent/ExecutionException
    //   0	8	21	java/lang/InterruptedException
    //   0	8	35	finally
  }
}
