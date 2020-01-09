import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import java.lang.*;

//Util
import java.util.ArrayList;



public class FloorPlan extends JFrame {
  
  private static JFrame window;
  private JPanel floorDisplayPanel;
  private ArrayList<Table> tables;
  private int[][] tableLocation; // The first bracket controls which table it is, the second bracket controls whether or not it's x or y
  private int radius;
  private int studentRadius;
  
  FloorPlan () {
    super("My Floor Plan");  
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    this.setResizable(false);
    
    //Set up the game panel (where we put our graphics)
    floorDisplayPanel = new FloorDisplayPanel();
    //this.add(new FloorDisplayPanel()); //MANGAT'S CODE
    this.add(floorDisplayPanel);// MY CODE
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
  }
  
  public void generateFloorPlan(ArrayList<Table> tables) throws Exception{
    this.tables = new ArrayList<Table>();
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
        dots[i*maxTablesHeight+j][2] = (int)Math.sqrt(Math.pow(xLocation-centerX,2)+Math.pow(yLocation-centerY,2));
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
    tables = new ArrayList<Table>();
    ArrayList<int[]> tableLocationArrayList = new ArrayList<int[]>();
    Scanner input;
    try{
      File file = new File("tables.txt");
      input = new Scanner(file);
      System.out.println("File Opened");
      while(input.hasNext()){
        String line = input.nextLine();
        String[] tableInfo = line.split(";");
        String[] coordinatesString = tableInfo[0].split(",");
        int[] coordinatesInt = new int[2];
        coordinatesInt[0] = Integer.parseInt(coordinatesString[0]);
        coordinatesInt[1] = Integer.parseInt(coordinatesString[1]);
        System.out.println("Coord: " + coordinatesInt[0] + " " + coordinatesInt[1]);
        tableLocationArrayList.add(coordinatesInt);
        ArrayList<Student> students = new ArrayList<Student>();
        for (int i = 1; i < tableInfo.length; i++){
          String[] studentInfo = tableInfo[i].split(",");
          String name = studentInfo[0];
          System.out.println(name);
          String studentNumber = studentInfo[1];
          System.out.println(studentNumber);
          ArrayList<String> dietaryRestrictions = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(studentInfo, 2, studentInfo.length)));
          System.out.println("Dietary Restrictions Success");
          students.add(new Student(name, studentNumber, dietaryRestrictions, new ArrayList<String>()));//NOTE: NOT SAVING STUDENT PREFERENCES FOR NOW, CAN CHANGE THIS
        }
        Table table = new Table(students.size());
        table.setStudents(students);
        tables.add(table);
        System.out.println(tables.size());
        System.out.println();
      }
      tableLocation = new int[tableLocationArrayList.size()][2];
      for (int i = 0; i < tableLocation.length; i++){
        tableLocation[i] = tableLocationArrayList.get(i);
      }
      System.out.println("Converted Successfully: " + tableLocation.length);
      input.close();
    }catch (Exception e){
      System.out.print("File Did Not Open");
    }
    
    this.setVisible(true);
    
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
  private class MyMouseListener implements MouseListener{
    
    public void mousePressed(MouseEvent e){
      //Set drag and drop thing to true
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
      //Display information
      
      JFrame frame = new JFrame();
      JPanel panel = new JPanel ();
      JScrollPane scrollPane = new JScrollPane(panel);
      
      //JFrame frameStudent = new JFrame ();
      //JPanel panelStudent = new JPanel ();
      //JScrollPane scrollPaneStudent = new JScrollPane(panelStudent);
      
      frame.setVisible(false);
      //frameStudent.setVisible(false);
      
      for (int i = 0; i < tables.size(); i++) {
        int x = tableLocation[i][0] - radius;
        int y = tableLocation[i][1] - radius;
        
        
        if(e.getX() > x && e.getX() < (x + radius + radius)){
          if(e.getY() > y && e.getY() < (y + radius + radius)){
            
            frame.setSize(200,250);
            frame.setLocation(x+radius,(int)(y+radius*1.5));
            
            frame.setResizable(true);
            frame.setVisible(true);
            
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            JLabel labelHeader = new JLabel("Table Number: " + (i+1));
            //labelHeader.setHorizontalAlignment(JLabel.CENTER);
            //labelHeader.setHorizontalAlignment(JLabel.RIGHT);
            panel.add(labelHeader);
            
            JLabel labelSubtitle = new JLabel("Table Information:");
            //frame.add(Box.createRigidArea(new Dimension(0,10)));
            panel.add(labelSubtitle);
            
            for (int j = 0; j < tables.get(i).getStudents().size(); j++) {
              
              JLabel labelEmpty = new JLabel(" ");
              panel.add(labelEmpty);
              
              JLabel labelStudent = new JLabel("Student: " + (j+1));
              panel.add(labelStudent);
              JLabel labelStudentName = new JLabel(tables.get(i).getStudents().get(j).getName());
              panel.add(labelStudentName);
              JLabel labelStudentNumber = new JLabel("Student Number: " + tables.get(i).getStudents().get(j).getStudentNumber());
              panel.add(labelStudentNumber);
              JLabel labelDietaryRestrictions = new JLabel("Dietary Restrictions: " + tables.get(i).getStudents().get(j).getDietaryRestrictions());
              panel.add(labelDietaryRestrictions);
              
            }
          }
        }
        
        //frame.add(panel);
        frame.getContentPane().add(scrollPane);
      }
        
        
        // -------------------------------------------------------------------------------------------------------------------------------------------------
      /*for (int a = 0; a < tables.size(); a++) {
        for (int k = 0; k < tables.get(a).getStudents().size(); k++) {
          int xStudent = (int)(Math.cos(k*2*Math.PI/tables.get(a).getStudents().size())*(radius+studentRadius)+tableLocation[a][0]-studentRadius);
          int yStudent = (int)(Math.sin(k*2*Math.PI/tables.get(a).getStudents().size())*(radius+studentRadius)+tableLocation[a][1]-studentRadius);
          
          
          if(e.getX() > xStudent && e.getX() < (xStudent + studentRadius*2)){
            if(e.getY() > yStudent && e.getY() < (yStudent + studentRadius*2)){
              
              frameStudent.setSize(100,200);
              frameStudent.setLocation(xStudent+studentRadius,(int)(yStudent+studentRadius*1.5));
              
              frameStudent.setResizable(true);
              frameStudent.setVisible(true);
              
              panelStudent.setLayout(new BoxLayout(panelStudent, BoxLayout.Y_AXIS));
              
              JLabel labelHeader = new JLabel("Student: " + (k+1));
              panelStudent.add(labelHeader);
              
              JLabel labelStudentName = new JLabel("Name: " + tables.get(a).getStudents().get(k).getName());
              //frame.add(Box.createRigidArea(new Dimension(0,10)));
              panelStudent.add(labelStudentName);
              JLabel labelStudentNumber = new JLabel("Student Number: " + tables.get(a).getStudents().get(k).getStudentNumber());
              panelStudent.add(labelStudentNumber);
              
            }
          }
          
          frameStudent.getContentPane().add(scrollPaneStudent);
        }
        
        // -------------------------------------------------------------------------------------------------------------------------------------------------
        
    }
    
    */
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
  }
    
    
  
}
