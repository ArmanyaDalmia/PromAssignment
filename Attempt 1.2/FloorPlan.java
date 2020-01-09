import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
//Util
import java.util.ArrayList;



public class FloorPlan extends JFrame {
  
  private static JFrame window;
  private JPanel floorDisplayPanel;
  private ArrayList<Table> tables;
  
  private ArrayList<JButton> buttons;
  
  private int[][] tableLocation; // The first bracket controls which table it is, the second bracket controls whether or not it's x or y
  private int radius;
  private ActionListener eventListener;
  
  FloorPlan () {
    super("My FloorPlan");  
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    this.setResizable(false);
    
    //Set up the game panel (where we put our graphics)
    floorDisplayPanel = new FloorDisplayPanel();
    this.add(new FloorDisplayPanel());
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
    
    tables = new ArrayList<Table>();
    
    buttons = new ArrayList<Button>();
  }
  
  public void generateFloorPlan(ArrayList<Table> tables) throws Exception{
    this.tables = tables;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double maxWidth = screenSize.getWidth();
    double maxHeight = screenSize.getHeight();
    double centerX = maxWidth/2;
    double centerY = maxHeight/2;
    int maxTablesWidth = (int)Math.ceil(Math.sqrt(maxWidth*tables.size()/maxHeight));
    int maxTablesHeight = (int)Math.ceil(Math.sqrt(maxHeight*tables.size()/maxWidth));
    radius = (int)Math.min(maxWidth/maxTablesWidth/4,maxHeight/maxTablesHeight/4);
    int[][] dots = new int[maxTablesWidth*maxTablesHeight][4];//[which dot the properties belong to][x,y,distance from center,id]
    for (int i = 0; i < maxTablesWidth; i++){
      int xLocation = (int)(maxWidth/maxTablesWidth/2+maxWidth/maxTablesWidth*i);
      for (int j = 0; j < maxTablesHeight; j++){
        int yLocation = (int)(maxHeight/maxTablesHeight/2+maxHeight/maxTablesHeight*j);
        dots[i*maxTablesHeight+j][0] = xLocation;
        dots[i*maxTablesHeight+j][1] = yLocation;
        dots[i*maxTablesHeight+j][2] = (int)Math.sqrt(Math.pow(xLocation-centerX/2,2)+Math.pow(yLocation-centerY/2,2));
        dots[i*maxTablesHeight+j][3] = i*maxTablesHeight+j;
      }
    }
    Arrays.sort(dots, new java.util.Comparator<int[]>(){
      public int compare(int[] a, int[] b){
        return Integer.compare(a[2], b[2]);
      }
    });
    tableLocation = new int[tables.size()][2];//[the table][x,y,id]
    for (int i = 0; i < tableLocation.length; i++){
      if (i < dots.length){
        tableLocation[i][0] = dots[i][0];
        tableLocation[i][1] = dots[i][1];
      } else {
        System.out.println("Too few potential table locations!");
        tableLocation[i][0] = (int)(Math.random()*maxWidth);
        tableLocation[i][1] = (int)(Math.random()*maxHeight);
      }
    }
    PrintWriter output;
    try{
      File file = new File("tables.txt");
      output = new PrintWriter(file);
      for (int i = 0; i < tableLocation.length; i++){
        output.print(tableLocation[i][0] + "," + tableLocation[i][1]);
        //System.out.print(tableLocation[i][0] + "," + tableLocation[i][1]);//Debug
        //System.out.println(" " + i);//
        Table table = tables.get(i);
        //System.out.println(table.getStudents().size());
        for (int j = 0; j < table.getStudents().size(); j++){
          Student student = table.getStudents().get(j);
          output.print(";" + student.getName() + "," + student.getStudentNumber());
          //System.out.println(";" + student.getName() + "," + student.getStudentNumber());//
          ArrayList<String> dietaryRestrictions = student.getDietaryRestrictions();
          for (int k = 0; k < dietaryRestrictions.size(); k++){
            output.print("," + dietaryRestrictions.get(k));
            //System.out.print("," + dietaryRestrictions.get(k));//
          }
        }
        output.println("");
        //System.out.println("");
      }
      output.close();
    } catch (Exception e){
      System.out.print("File Did Not Save");
    }
  }
  
  public void displayFloorPlan() {
    ArrayList<Integer[]> tableLocationArrayList = new ArrayList<Integer[]>();
    Scanner input;
    try{
      File file = new File("tables.txt");
      input = new Scanner(file);
      while(input.hasNext()){
        String line = input.nextLine();
        String[] tableInfo = line.split(";");
        String[] coordinatesString = tableInfo[0].split(",");
        Integer[] coordinatesInt = new Integer[2];
        coordinatesInt[0] = Integer.parseInt(coordinatesString[0]);
        coordinatesInt[1] = Integer.parseInt(coordinatesString[1]);
        tableLocationArrayList.add(coordinatesInt);
        ArrayList<Student> students = new ArrayList<Student>();
        for (int i = 1; i < tableInfo.length; i++){
          String[] studentInfo = tableInfo[i].split(",");
          String name = studentInfo[0];
          String studentNumber = studentInfo[1];
          ArrayList<String> dietaryRestrictions = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(studentInfo, 2, studentInfo.length)));
          students.add(new Student(name, studentNumber, dietaryRestrictions, new ArrayList<String>()));//NOTE: NOT SAVING STUDENT PREFERENCES FOR NOW, CAN CHANGE THIS
        }
        Table table = new Table(students.size());
        table.setStudents(students);
        tables.add(table);
      }
      tableLocation = tableLocationArrayList.toArray(new int[tableLocationArrayList.size()][2]);//Sketchy
    }catch (Exception e){
      System.out.print("File Did Not Open");
    }
  }
  
  private class FloorDisplayPanel extends JPanel {
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g); //required
      setDoubleBuffered(true);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double maxWidth = screenSize.getWidth();
      double maxHeight = screenSize.getHeight();
      double centerX = maxWidth/2;
      double centerY = maxHeight/2;
      int maxTablesWidth = (int)Math.ceil(Math.sqrt(maxWidth*tables.size()/maxHeight));
      int maxTablesHeight = (int)Math.ceil(Math.sqrt(maxHeight*tables.size()/maxWidth));
      radius = (int)Math.min(maxWidth/maxTablesWidth/4,maxHeight/maxTablesHeight/4);
      for (int i = 0; i < tables.size(); i++){
//        Button.addActionListenre(new ActionListener(
//           display(i);                                                 
//      )){
        
        //}
        g.setColor(Color.RED);
        g.fillOval(tableLocation[i][0]-radius,tableLocation[i][1]-radius,radius*2,radius*2);
        int numStudents = tables.get(i).getStudents().size();
        g.setColor(Color.BLUE);
        for (int j = 0; j < numStudents; j++){
          int studentRadius = (int)(Math.pow(0.000463,1.0/numStudents)*radius*(1.0+Math.sqrt(2.0/(1.0-Math.cos(2*Math.PI/numStudents))))/(2.0/(1.0-Math.cos(2*Math.PI/numStudents))-1.0));//FIX THIS
          //System.out.println("Radius: " + radius + "    Student Radius: " + studentRadius);
          g.fillOval((int)(Math.cos(j*2*Math.PI/numStudents)*(radius+studentRadius)+tableLocation[i][0]-studentRadius),(int)(Math.sin(j*2*Math.PI/numStudents)*(radius+studentRadius)+tableLocation[i][1]-studentRadius),studentRadius*2,studentRadius*2);
        }
      }
      //repaint(); //Add later
    }
    
  }
  
  private class EventListener implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      
    }
    
  }
  
}
