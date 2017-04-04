package alarMe;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ItsLearning extends LoginProcess{
	
	
	protected ArrayList<String> assignmentsI = new ArrayList<String>();
	protected ArrayList<String> datesI = new ArrayList<String>();
	protected ArrayList<String> coursecode = new ArrayList<String>();
	protected ArrayList<String> dateAndCourse = new ArrayList<String>();
    
    //constructor. 
    public ItsLearning(){
    	super(username, user_password, driver);
    }

    //method that return the name of the assignments, f.ex "oving 4", as a list.
    public ArrayList<String> getAssignmentsI() {
        return assignmentsI;
    }
    
    //method that return the dates the assignments are expected to be handed in as a list.
    public ArrayList<String> getDatesI(){
    	return datesI;
    }
    
    //metod that return all the coursecodes as a list.
    public ArrayList<String> getCoursecodeI(){
    	return coursecode;
    }
       
        
    public void setAssignmentsI(){
    	
    	driver.get("http://www.ilearn.sexy"); //loads the webpage.
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	//1. Try-catch block 1 and 2 must be "removed" when the server is runed, but are needed when the tests are runed.
    	/*try {
			login();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	//2
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
    	
    	driver.switchTo().frame(driver.findElement(By.name("mainmenu")));
    	//Fetch the assignments and dates from itsLearning
    	List <WebElement> active_assignments = driver.findElements(By.cssSelector("a > .h-va-baseline"));
    	List<WebElement> dates1 = driver.findElements(By.cssSelector("li > .itsl-widget-extrainfo"));
    	
    	//Convert the webelements into strings and adds them to the assignmentsI list.
    	for (WebElement a : active_assignments){
    		String assignmentString = a.getText();
    		this.assignmentsI.add(assignmentString);

    	}
    	//converts the date webelements to strings and adds them to an dateAndCourse list.		
    	for (WebElement date : dates1) {
    		String dateString = date.getText();
    		dateString = dateString.replaceAll(",", "");
    		dateString = dateString.replaceAll("i ", "");
    		dateString = dateString.replaceAll("  ", " ");
    		dateString = dateString.replaceAll("VÅR 2017", "");
    		dateString = dateString.replaceAll("in", "");
    		String [] dateStringNew = dateString.split(" ");
    		List<String> findCoursecode = Arrays.asList(dateStringNew);
    		dateAndCourse.add(dateString);
    		
    		//finds the coursecode in the dateAndCourse list.
    		for(String e1 : findCoursecode){
            	if(e1.length() == 7){
            		if(e1.matches("^[A-Z]{3}\\d{4}")){
               			this.coursecode.add(e1);
               			this.dateAndCourse.remove(e1);
               		}
            	}
        	}
    		
    
    	}
    	//finds the due date for the assignment.
		int start = active_assignments.size();
    	for (int i = start ; i < dateAndCourse.size(); i++){
    		if ((i % 2) != 0){
    			String course = dateAndCourse.get(i);
    			String [] course1 = course.split(" ", 2);
    			String courseC = course1 [0];
    			this.datesI.add(courseC);
    			
       		}
    	}
    	  		
    }
    	
    
    
    //Code to add Itslearning assignments to Database
    //the method is a boolean only because we wanted to test the method, and needed a boolean.
    public boolean addAssignmentsIToDatabase() {
        boolean success = true;	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    for (int i = 0; i < assignmentsI.size(); i++){
     	    	PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Assignment(course_code, assignment_name, assignment_date, student_id) VALUES(?,?,?,?)");
     	    	p.setString(3, datesI.get(i));
     	    	p.setString(2, assignmentsI.get(i));
     	    	p.setString(1, coursecode.get(i));
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
