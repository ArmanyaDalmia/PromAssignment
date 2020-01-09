import java.util.ArrayList;
class Tester{
  public static void main(String [] args) throws Exception{
    int NUM_TABLES = 10;
    int TABLE_SIZE = 100;
    FloorPlan floorPlan = new FloorPlan();
    ArrayList<Table> tables = new ArrayList<Table>();
    ArrayList<Student>[] students = new ArrayList[NUM_TABLES];
    ArrayList<String>[] dietaryRestriction = new ArrayList[NUM_TABLES];
    ArrayList<String>[] friendStudentNumber = new ArrayList[NUM_TABLES];
    Table[] tablesArray = new Table[NUM_TABLES];
    for (int i = 0; i < NUM_TABLES; i++){
      students[i] = new ArrayList<Student>();
      dietaryRestriction[i] = new ArrayList<String>();
      friendStudentNumber[i] = new ArrayList<String>();
      for (int j = 0; j < TABLE_SIZE; j++){
        students[i].add(new Student("Kamron Zaidi", "072935653", dietaryRestriction[i], friendStudentNumber[i]));
      }
      tablesArray[i] = new Table(TABLE_SIZE);
      tablesArray[i].setStudents(students[i]);
      tables.add(tablesArray[i]);
    }
    /*
    ArrayList<Student> students1 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction1 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber1 = new ArrayList<String>();
    students1.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction1, friendStudentNumber1));
    students1.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction1, friendStudentNumber1));
    students1.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction1, friendStudentNumber1));
    students1.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction1, friendStudentNumber1));
    students1.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction1, friendStudentNumber1));
    Table table1 = new Table(4);
    table1.setStudents(students1);
    tables.add(table1);
    
    ArrayList<Student> students2 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction2 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber2 = new ArrayList<String>();
    students2.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction2, friendStudentNumber2));
    students2.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction2, friendStudentNumber2));
    students2.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction2, friendStudentNumber2));
    students2.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction2, friendStudentNumber2));
    students2.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction2, friendStudentNumber2));
    Table table2 = new Table(4);
    table2.setStudents(students2);
    tables.add(table2);
    
    ArrayList<Student> students3 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction3 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber3 = new ArrayList<String>();
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    Table table3 = new Table(4);
    table3.setStudents(students3);
    tables.add(table3);
    
        ArrayList<Student> students3 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction3 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber3 = new ArrayList<String>();
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    Table table3 = new Table(4);
    table3.setStudents(students3);
    tables.add(table3);
    
        ArrayList<Student> students3 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction3 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber3 = new ArrayList<String>();
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    Table table3 = new Table(4);
    table3.setStudents(students3);
    tables.add(table3);
    
        ArrayList<Student> students3 = new ArrayList<Student>();
    ArrayList<String> dietaryRestriction3 = new ArrayList<String>();
    ArrayList<String> friendStudentNumber3 = new ArrayList<String>();
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    students3.add(new Student("Kamron Zaidi", "072935653", dietaryRestriction3, friendStudentNumber3));
    Table table3 = new Table(4);
    table3.setStudents(students3);
    tables.add(table3);
    */
    floorPlan.generateFloorPlan(tables);
    floorPlan.displayFloorPlan();
    
  }
}