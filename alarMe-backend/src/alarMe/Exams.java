package alarMe;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class Exams extends LoginProcess {

    private ArrayList<String> coursename = new ArrayList<String>();
    private ArrayList<String> coursecode = new ArrayList<String>();
    private ArrayList<String> examdate = new ArrayList<String>();
 
    
    public Exams(){
    	super(username, user_password, driver);
    } 

    
    public ArrayList<String> getExamDate() {
        return examdate;
    }
    
    public ArrayList<String> getCourseName(){
    	return coursename;
    }
    
    public ArrayList<String> getCourseCode(){
    	return coursecode;
    }
    
    public void setExams(){
    	//STUDWEB
        driver.get("https://idp.feide.no/simplesaml/module.php/feide/login.php?asLen=169&AuthState=_"
                + "d3cf8da4fdb8785ba65151ba2683aca1150fe3bfc2%3Ahttps%3A%2F%2Fidp.feide.no%2Fsimplesaml%"
                + "2Fsaml2%2Fidp%2FSSOService.php%3Fspentityid%3Dhttps%253A%252F%252Ffsweb.no"
                + "%252Fstudentweb%26cookieTime%3D1487768961");
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

        //Choose NTNU as the institution
      /*  try {
			chooseNTNU();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

        //innlogging med brukerinput
        try {
			login();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}*/

        //need to choose NTNU as the institution one more time
        try {
			chooseNTNUAgain();
		} catch (InterruptedException e) {
		 
			e.printStackTrace();
		}

        //adds the exam dates to this.exams as <subject code subject name, date>
        List<WebElement> datesAndSubjects = driver.findElements(By.className("infoLinje"));
        for (WebElement element : datesAndSubjects){
            int index = datesAndSubjects.indexOf(element);
        	if (index == 0 || index % 3 == 0) {
                String date = element.getText();
                String courseCode = datesAndSubjects.get(index + 1).getText();
                String courseName = datesAndSubjects.get(index + 2).getText();
                //this.exams.put(courseCode, date);
                this.examdate.add(date);
                this.coursecode.add(courseCode);
                this.coursename.add(courseName);
            }
          
        }

    }
    
    
    //Code to add courses and exam dates to Database
    public boolean addExamsToDatabase() {
    	boolean success = true;	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    //setConnection();
     	    for(int i = 0; i < coursename.size(); i++){
     	    	PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Exam(coursecode, exam_date, coursename, student_id) VALUES(?,?,?,?)");
     	    	p.setString(1, coursecode.get(i));
     	    	p.setString(2, examdate.get(i));
     	    	p.setString(3, coursename.get(i));
     	    	p.setInt(4, student_id);
     	    	p.executeUpdate();
     	    }
    	       		
    	}catch(Exception e){
    			System.out.println( "error:" + e);
    			success = false;
    	   }
		return success;
    	}
    		
    
}
