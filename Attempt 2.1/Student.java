import java.util.ArrayList;
class Student{
  private String name;
  private String studentNumber;
  private ArrayList<String> dietaryRestrictions;
  private ArrayList<String> friendStudentNumbers;
  Student(String name, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers){
    this.name=name;
    this.studentNumber = studentNumber;
    this.dietaryRestrictions = dietaryRestrictions;
    this.friendStudentNumbers= friendStudentNumbers;
  }
  public String getName(){
    return name;
  }
  public String getStudentNumber(){
    return studentNumber;
  }
  public ArrayList<String> getDietaryRestrictions(){
    return dietaryRestrictions;
  }
  public ArrayList<String> friendStudentNumbers(){
    return friendStudentNumbers;
  }
}