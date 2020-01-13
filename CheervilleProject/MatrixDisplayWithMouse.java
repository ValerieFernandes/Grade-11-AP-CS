import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/* [MatrixDisplayWithMouse.java]
 * A small program showing how to use the MatrixDisplayWithMouse class
 *  NOTE - A lot of things to fix here!
 * @author Mangat
 */


class MatrixDisplayWithMouse extends JFrame {
  public static ArrayList<Point> clicked = new ArrayList<Point>();
  int maxX,maxY, GridToScreenRatio;
  int[][] matrix;
  int people;
  int plant;
  int zombie;
  String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
  
  MatrixDisplayWithMouse(String title, int[][] matrix) {
    super(title);
    this.matrix = matrix;
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (matrix.length+1);  //ratio to fit in screen as square map
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    
    this.add(new MatrixPanel());
    
    this.setVisible(true);
  }
  
   public void refresh() { 
    this.repaint();
  }
  
  //Inner Class 
  class MatrixPanel extends JPanel {
    
    MatrixPanel() { 
      
      addMouseListener(new MatrixPanelMouseListener());
    }
    
    public void paintComponent(Graphics g) {        
      super.repaint();
      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      g.drawOval(50, 50, 50, 50);

      
       for(int i = 0; i<matrix[0].length;i=i+1)  { 
        for(int j = 0; j<matrix.length;j=j+1)  { 
          
          if (matrix[i][j]==1)    // human
            g.setColor(Color.RED);
          else if (matrix[i][j]==2) // plant
            g.setColor(Color.BLUE);
          else if (matrix[i][j]==3)
            g.setColor(Color.GREEN); // zombie
          
          g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          g.setColor(Color.BLACK);
          g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          
          if(Town.upWind < 0){
            g.drawString(("Vertical Wind: " + Town.upWind*-1 + " South"), 1150, 130);
          }else{
            g.drawString(("Vertical Wind: " + Town.upWind + " North"), 1150, 130);
          }
          if(Town.sideWind < 0){
            g.drawString(("Horizontal Wind: " + Town.sideWind*-1 + " West"), 1150, 180);
          }else{
            g.drawString(("Horizontal Wind: " + Town.sideWind + " West"), 1150, 180);
          }
          
          g.drawString(("Month: " + month[Town.month%12]), 1000, 330);
          g.drawString(("Year: " + String.valueOf(Town.month/12)), 1000, 380);
          
          g.drawString(("People: " + String.valueOf(Town.people)), 1000, 30);
          g.drawString(("People: " + String.valueOf(Town.people)), 1000, 30);
          g.drawString(("Zombies: " + String.valueOf(Town.zombie)), 1150, 30);
          g.drawString(("Plant: " + String.valueOf(Town.plant)), 1300, 30);
          
          g.setColor(Color.RED);
          g.fillRect(970, 15, 20, 20);
           g.setColor(Color.GREEN);
          g.fillRect(1120, 15, 20, 20);
          g.setColor(Color.BLUE);
          g.fillRect(1270, 15, 20, 20);
          g.setColor(Color.BLACK);
        }
      }
      
      
    }
  }
  
  
  //Mouse Listener 
  class MatrixPanelMouseListener implements MouseListener{ 
    
     //Mouse Listner Stuff
   public void mousePressed(MouseEvent e) {
       System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
       System.out.println("x: " + e.getPoint().x + ",y: " + ((e.getPoint().y - 1)/ 33.3));
       clicked.add(new Point((int)(e.getPoint().y / 33.33), (int)(e.getPoint().x / 33.3)));
       
   }

    public void mouseReleased(MouseEvent e) {
      System.out.println("Mouse released; # of clicks: " + e.getClickCount());
    }

    public void mouseEntered(MouseEvent e) {
       System.out.println("Mouse entered");
    }

    public void mouseExited(MouseEvent e) {
       System.out.println("Mouse exited");
    }

    public void mouseClicked(MouseEvent e) {
       System.out.println("Mouse clicked (# of clicks: "+ e.getClickCount() + ")");
    }
  
  }
  
}