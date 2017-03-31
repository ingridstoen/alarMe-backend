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
    
    //konstruktor
    public ItsLearning(){
    	super(username, user_password, driver);
    }

    //hent assignments
    public ArrayList<String> getAssignmentsI() {
        return assignmentsI;
    }
    
    public ArrayList<String> getDatesI(){
    	return datesI;
    }
    
    public ArrayList<String> getCoursecodeI(){
    	return coursecode;
    }
       
        
    public void setAssignmentsI(){
    	
    	driver.get("http://www.ilearn.sexy"); //loads the webpage
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	try {
			login();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	driver.switchTo().frame(driver.findElement(By.name("mainmenu")));
    	//Fetch the assignments and dates from itsLearning
    	List <WebElement> active_assignments = driver.findElements(By.cssSelector("a > .h-va-baseline"));
    	List<WebElement> dates1 = driver.findElements(By.cssSelector("li > .itsl-widget-extrainfo"));
    	
    	
    	for (WebElement a : active_assignments){
    		String assignmentString = a.getText();
    		this.assignmentsI.add(assignmentString);

    	}
    			
    	for (WebElement date : dates1) {
    		//int numberOfAss = active_assignments.size();
    		String dateString = date.getText();
    		dateString = dateString.replaceAll(",", "");
    		dateString = dateString.replaceAll("i ", "");
    		dateString = dateString.replaceAll("  ", " ");
    		dateString = dateString.replaceAll("VÅR 2017", "");
    		String [] dateStringNew = dateString.split(" ");
    		List<String> findCoursecode = Arrays.asList(dateStringNew);
    		dateAndCourse.add(dateString);
    		
    		for(String e1 : findCoursecode){
            	if(e1.length() == 7){
            		if(e1.matches("^[A-Z]{3}\\d{4}")){
               			this.coursecode.add(e1);
            		}
            	}
        	}
    
    	}
    	for (int i = active_assignments.size(); i < dateAndCourse.size(); i++){
    		if ((i % 2) == 0){
    			String course = dateAndCourse.get(i);
    			String [] course1 = course.split(" ", 2);
    			String courseC = course1 [0];
    			this.datesI.add(courseC);
    			
       		}
    	}
    	
    		/*else{
    			this.coursecode.add(dateAndCourse.get(i));
    			for(String e1 : dateAndCourse){
	        		if(e1.length() == 7){
	           			if(e1.matches("^[A-Z]{3}\\d{4}")){
	           				coursecode.add(e1);
	           			}
	           		}
	        	}
    		}*/
    		

    	}
    	
    
    
    //Code to add Itslearning assignments to Database
    public boolean addAssignmentsIToDatabase() {
        boolean success = true;	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    //setConnection();
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
