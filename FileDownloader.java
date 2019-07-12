import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.plaf.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.channels.Channels;

class JFxButton extends JButton
{
  private int trans=0, fontsz=11;
  private String name;
  private Color top , bottom, border;

  public JFxButton(String name)
  {
    setContentAreaFilled(false);
    setFocusPainted(false);
    border = new Color(20, 20, 20);
    setForeground(new Color(252, 252, 252));
    trans  = 40;
    this.name = name;
    top = new Color(18, 18, 18);
    bottom = new Color(38, 38, 38);
    addMouseListener(new MouseAdapter()
    {
         @Override
         public void mouseEntered(MouseEvent e)
         {
            super.mouseEntered(e);
            border = new Color(226, 130, 4);
            top = new Color(255, 145, 0);
            bottom = new Color(255, 181, 82);
            trans = 120;
            fontsz=12;
            setForeground(new Color(0, 0, 0));
            repaint();
         }
         @Override
         public void mouseExited(MouseEvent e)
         {
            super.mouseExited(e);
             border = new Color(20, 20, 20);
             top = new Color(18, 18, 18);
             bottom = new Color(38, 38, 38);
            setForeground(new Color(252 ,252, 252));
            trans  = 40;
            fontsz=11;
            repaint();
         }
    });

  }
    @Override
  public void paintComponent(Graphics g)
  {
     super.paintComponent(g);
     Graphics2D g2 = (Graphics2D)g;
     setBorder(BorderFactory.createLineBorder(border, 1, true));
     g2.setPaint(new GradientPaint(getWidth(), getHeight()/2, top,
                                               getWidth(), getHeight(), bottom));
     g2.fillRect(0, 0, getWidth(), getHeight());
     
      g2.setPaint(new GradientPaint(getWidth()-1, (getHeight()/2)/2, new Color(254, 254, 254, trans),
                                               getWidth()-1, getHeight()/2, new Color(255, 255, 255, 0)));
      g2.fillRect(1, 1, getWidth()-1, getHeight()/2);
      
     g2.setFont(new Font("MS Sans Serif", Font.BOLD, fontsz));
     g2.setColor(getForeground());
     g2.drawString(name, 13, 16);
 }
}

class JFxField extends JTextField
{
  public JFxField()
  {
   setBorder(BorderFactory.createLineBorder(new Color(250, 250, 250), 1, true));
   setBackground(new Color(252, 252, 252));     
   setForeground(new Color(52, 52, 52)); 
  }
}
  
class JFxPanel extends JPanel
{
    private Point pt;
    private final JFrame frame;
    
  public JFxPanel(final JFrame frame)
  {
      this.frame = frame;
     setLayout(null);
     setBorder(BorderFactory.createBevelBorder(getWidth(), new Color(58, 58, 58), new Color(68, 68, 68)));
     addMouseListener(new MouseAdapter(){
        public void mousePressed(MouseEvent e)
             {
                 super.mousePressed(e);
                 if(e.getX()> 0 && e.getX() < getWidth() && e.getY() > 0 && e.getY() < 30)
                 {
                 pt = e.getPoint();
                 }
             }
          public void mouseReleased(MouseEvent e)
             {
                 super.mouseReleased(e);
                 pt = null;
             }
     });
     
     addMouseMotionListener(new MouseAdapter()
             {
                public void mouseDragged(MouseEvent e)
             {
                 super.mouseDragged(e);
                  if(e.getX()> 0 && e.getX() < getWidth() && e.getY() > 0 && e.getY() < 30)
             {
                 frame.setLocation(e.getLocationOnScreen().x-pt.x, 
                         e.getLocationOnScreen().y-pt.y);
             }
             }
             });
     
  }
  @Override
  public void paintComponent(Graphics g)
  {
     super.paintComponent(g);
     Graphics2D g2 = (Graphics2D)g;
     
     
      
     g2.setPaint(new GradientPaint(getWidth(), getHeight()/2, new Color(78, 78, 78),
                                                getWidth(), getHeight(), new Color(98, 98, 98)));
     g2.fillRect(0, 0, getWidth(), getHeight());
     
      g2.setPaint(new GradientPaint(getWidth(), 15, new Color(28, 28, 28),
                                                getWidth(), 30, new Color(38, 38, 38)));
      g2.fillRect(0, 0, getWidth(), 30);
      
         g2.setPaint(new GradientPaint(getWidth()-1, 7, new Color(252, 252, 252, 16),
                                                getWidth()-1, 15, new Color(255, 255, 255, 0)));
         g2.fillRect(1, 1, getWidth()-1, 15);
      g2.setColor(new Color(252, 252, 252));
      g2.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
      g2.drawString("FileDownloader", 28, 20); 
      g2.drawImage(Toolkit.getDefaultToolkit().getImage("logo.png"), 5, 8, 18, 18, this);
      g2.setColor(new Color(38, 38, 38));
      g2.fillRect(0, 30, 5, getHeight()-30);
      g2.fillRect(getWidth()-6, 30, 5, getHeight()-30);
      g2.fillRect(5, getHeight()-6, getWidth()-10, 5);
     g2.drawImage(Toolkit.getDefaultToolkit().getImage("logo.png"), 20, 70, 48, 48,
          this);
   }
}


class Search_Action implements ActionListener
{
  private DefaultTableModel model;
  private JTextField txt;
  public Search_Action(DefaultTableModel model, JTextField txt)
  {
   this.model = model;
   this.txt = txt;
  }
 public void doPerform()
 {
 try
    {
   URL url = new URL(txt.getText());
   InputStream is = url.openStream();
   String newpath = url.getFile().replace('\\','/');
    int start = newpath.lastIndexOf("/");
    model.addRow(new Object[]{newpath.substring(start+1, newpath.length()), ""+is.available()});
   }
    catch(IOException e)
   {
   }
   finally
    {
    }
   
 }

 @Override
 public void actionPerformed(ActionEvent e)
 {
   doPerform();
 }
}

class Browse_Action  implements ActionListener
{
  private JTextField brws;
  public Browse_Action(JTextField brws)
  {
  this.brws = brws;
  }

  public void doBrowse()
  {
     try
     {
          JFileChooser jfc = new JFileChooser();
          jfc.setDialogTitle("Browse Folder");
          jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          jfc.setAcceptAllFileFilterUsed(false);
       if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            brws.setText(jfc.getSelectedFile().getAbsolutePath());
        }
     }
     catch(Exception e)
     {
     }
  }

 @Override
 public void actionPerformed(ActionEvent e)
 {
     doBrowse(); 
 }
}

class Down_Action  implements ActionListener
{
  private  DefaultTableModel model;
  private JTextField txt;
  private JTextField dst;
  private JFrame par;
 
  public Down_Action(DefaultTableModel model, JTextField txt, JTextField dst, JFrame par)
  {
    this.model = model;
    this.txt = txt;
    this.dst = dst;
    this.par = par;
  }

   public void doPerform()
  {
 try
    {
     String newpath = txt.getText().replace('\\','/');
    int start = newpath.lastIndexOf("/");
    String fname = newpath.substring(start+1, newpath.length());
    URL url = new URL(txt.getText());
 ReadableByteChannel rbc = Channels.newChannel(url.openStream());
FileOutputStream fos = new FileOutputStream(dst.getText()+"/"+fname);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
  
  fos.close();
    }
    catch(IOException e)
   {
   }
   finally
    {
    }
   
  }

 @Override
 public void actionPerformed(ActionEvent e)
 {
   doPerform();
 }
}

class Clear_Action  implements ActionListener
{
  private DefaultTableModel model;
  private JTextField txt, dst;

  public Clear_Action(DefaultTableModel model, JTextField txt, JTextField dst)
  {
   this.model = model;
   this.txt  = txt;
   this.dst = dst;
  }

 @Override
 public void actionPerformed(ActionEvent e)
 {
   for(int i=0;i<=model.getRowCount()-1;i++)
   {
       model.removeRow(i);
       
   }
       txt.setText("");
       dst.setText("");
 }
}

class FxClose extends JButton
{
    private String text;
    private JFrame frm;
    public FxClose(String text, JFrame frame)
    {
      
        this.text =text;
        frm = frame;
     setContentAreaFilled(false);
     setFocusPainted(false);
     setBorderPainted(false);
     setFont(new Font("MS Sans Serif", Font.BOLD, 16));
     setForeground(new Color(252, 252, 252));
     addMouseListener(new MouseAdapter()
             {
               
                public void mouseEntered(MouseEvent e)
             {
                 super.mouseEntered(e);
                 setFont(new Font("MS Sans Serif", Font.BOLD, 18));
                 setForeground(new Color(255, 145, 0));
                 repaint();
             }
                public void mouseExited(MouseEvent e)
             {
                 super.mouseExited(e);
                 setFont(new Font("MS Sans Serif", Font.BOLD, 16));
                 setForeground(new Color(252, 252, 252));
                 repaint();
             }
             });
     addActionListener(new ActionListener()
       {
             public void actionPerformed(ActionEvent e)
             {
                frm.dispose();
             }
             });
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(text, 10, 13);
    }
}

public class FileDownloader
{
  public static void main(String args[]) throws Exception 
  {
     final JFrame frm = new JFrame("FileDownloader");
     final JFxPanel fxp = new JFxPanel(frm);
     JLabel mainlb = new JLabel("FileDownloader");
     JLabel lb1 = new JLabel("File URL:"),  lb2 = new JLabel("Dest:");
     final JFxField txt = new JFxField(),  dst = new JFxField();
     JFxButton srch = new JFxButton("Search"),  brws = new JFxButton("Browse"), down = new JFxButton("Download"), clear = new JFxButton("Clear");

     final DefaultTableModel model = new DefaultTableModel();
     JTable table = new JTable(model);
     JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     FxClose closebtn = new FxClose("×", frm);
   
    

    mainlb.setFont(new Font("MS Sans Seif", Font.PLAIN, 16));
    mainlb.setForeground(new Color(252, 252, 252));
    mainlb.setBounds(90, 85, 200, 20);
  
    lb1.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
    lb1.setForeground(new Color(252, 252, 252));
    lb1.setBounds(50, 150, 60, 20);

    lb2.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
    lb2.setForeground(new Color(252, 252, 252));
    lb2.setBounds(50, 196, 80, 20);

   
    txt.setBounds(110, 150, 420, 22);
    txt.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
    txt.setBackground(new Color(242, 242, 242));


    dst.setBounds(110, 196, 420, 22);
    dst.setFont(new Font("MS Sans Serif", Font.PLAIN, 12));
    dst.setBackground(new Color(242, 242, 242));
    dst.setEditable(false);

    table.setSelectionForeground(new Color(210, 210, 200));
    table.setForeground(new Color(50, 50, 50));
    jsp.setBounds(20, 255, 680, 150);

    srch.setBounds(540, 150, 80, 26);
    srch.addActionListener(new Search_Action(model, txt));
    brws.setBounds(540, 196, 80, 26);
   brws.addActionListener(new Browse_Action(dst));
    down.setBounds(440, 460, 90, 26);
    down.addActionListener(new Down_Action(model, txt, dst, frm));
    clear.setBounds(550, 460, 60, 26);
    clear.addActionListener(new Clear_Action(model, txt, dst));

    table.setFont(new Font("MS Sans Seif", Font.PLAIN, 10));
    model.addColumn("File_URL");
    model.addColumn("File_size");
 
   closebtn.setBounds(678, 5, 30, 20);
    
  
  fxp.add(mainlb);
  fxp.add(lb1);
  fxp.add(lb2);
  fxp.add(txt);
  fxp.add(dst);
  fxp.add(srch);
  fxp.add(brws);
  fxp.add(jsp);
  fxp.add(down);
  fxp.add(clear);
  fxp.add(closebtn);
  frm.add(fxp);

  frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frm.setUndecorated(true);
  frm.setBounds(255, 120, 720, 530);
  frm.setResizable(false);
  frm.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
 frm.setMenuBar(null);
  frm.setVisible(true);
 }
}

  
  
     
