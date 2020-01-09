import java.util.ArrayList;
class Table{
  private ArrayList<Student> students;
  private int size;
  Table(int size){
    this.size = size;
    this.students = new ArrayList<Student>();
  }
  public ArrayList<Student> getStudents(){
    return this.students;
  }
  public void setStudents(ArrayList<Student> students){
    this.students = students;
  }
  public int getSize(){
    return this.size;
  }
}