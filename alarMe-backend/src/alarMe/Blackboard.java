package alarMe;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Blackboard extends LoginProcess {
	
	protected ArrayList<String> assignmentsB = new ArrayList<String>();
	protected ArrayList<String> coursecodeB = new ArrayList<String>();
	protected ArrayList<String> datesB = new ArrayList<String>();
	protected ArrayList<String> assignments = new ArrayList<String>();
	protected ArrayList<String> assignments1 = new ArrayList<String>();
	protected ArrayList<String> assignments2 = new ArrayList<String>();
	protected ArrayList<String> assignments3 = new ArrayList<String>();
	
	
	public Blackboard(){
		super(username, user_password, driver);
	}
	
	//method that return the name of the assignments, f.ex "oving 4", as a list.
	public ArrayList<String> getAssignmentsB(){
		return assignmentsB;
	}
	
	//metod that return all the coursecodes as a list.
	public ArrayList<String> getCoursecodeB(){
		return coursecodeB;
	}
	
	//method that return the dates the assignments are expected to be handed in as a list.
	public ArrayList<String> getDatesB(){
		return datesB;
	}
	
	
	public void setAssignmentsB(){
		//loads the webpage
		driver.get("https://ntnu.blackboard.com/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_70_1");
	    driver.findElement(By.className("loginPrimary")).click();
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		}

	    //Clicks on "Varsler" at blackboard
	    driver.findElement(By.linkText("Varsler")).click();
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_4")).click();
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_3")).click();
	    driver.findElement(By.id("headerTextheader::1-dueView::1-dueView_2")).click();

	    
	    //fetches all the assignments. 
	    //Converts the webelements to strings and put them in the assignments list.
	    List<WebElement> allAssignments = driver.findElements(By.className("itemGroups"));
	    for (WebElement element : allAssignments) {
	    	String assig = element.getText();
	    	if(assig.length() >= 2){
	    		assignments.add(assig);
	    	}
	    }
		//Strips the assignment strings in the assignments list for unecessary info.       
	    for(String element1 : assignments){
	    	String assignment = element1.replace("\n", " ");
			assignment = assignment.replaceAll("[,-]", "");
			assignment = assignment.replace("(2017 VÅR)", "");
			assignment = assignment.replace("  ", " ");
			assignment = assignment.replace(" Leveringsfrist", "");
			String[] items = assignment.split(".17");
			List<String> assignments3 = Arrays.asList(items);
		   
		                  	
	       for(String element2 : assignments3){
	        	List<String> dates = Arrays.asList(element2.split("  "));
	        	assignments1.addAll(dates);
	        }
	    } 	        	
	        for(int i = 0; i < assignments1.size(); i ++){
	    		if (i % 2 != 0){
	    			datesB.add(assignments1.get(i));
	    		}else{
	    			assignments2.add(assignments1.get(i));
	    		}
	    	}
	        	
	        for(String element3 : assignments2){
	        	List<String> stringList = Arrays.asList(element3.split(" "));
	        	for(String e1 : stringList){
	        		if(e1.length() == 7){
	           			if(e1.matches("^[A-Z]{3}\\d{4}")){
	           				coursecodeB.add(e1);
	           				List<String> findass = Arrays.asList(element3.split(e1));
	           				String first = findass.get(0);
	           				first.replaceAll("  ", "");
	           				first.replaceAll(" ", "");
	           				assignmentsB.add(first);
	            				
	           			}
	           		}
	        	}
	}	    	
	        
}
	//The method checks if we have the assignments double up, because on blackboard the assignment
	//is sometimes put on both future and this week. 
	public void checkForEqualAssignments(){
		ArrayList<String> assignments_one = assignmentsB;
		ArrayList<String> coursecode_one = coursecodeB;
		ArrayList<String> dates_one = datesB;
		for (int i = 1; i < assignments_one.size(); i++) {
			String a1 = assignments_one.get(i);
	        String a2 = assignments_one.get(i-1);
	        String b1 = coursecode_one.get(i);
	        String b2 = coursecode_one.get(i-1);
	        String c1 = dates_one.get(i);
	        String c2 = dates_one.get(i-1);
	        if (a1.equals(a2) && b1.equals(b2) && c1.equals(c2)) {
	        	assignments_one.remove(a1);
	        	coursecode_one.remove(b1);
	        	dates_one.remove(c1);
	            }
	    }
		this.assignmentsB = assignments_one;
		this.coursecodeB = coursecode_one;
		this.datesB = dates_one;
		
	}
	
    //Code to add BlackBoard assignments to Database.
	//the method is a boolean only because we wanted to test the method, and needed a boolean.
    public boolean addAssignmentsBToDatabase()  {
    	boolean success = true;   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	   	//setConnection();
     	    for (int i = 0; i < assignmentsB.size(); i++){
     	    	PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Assignment(course_code, assignment_name, assignment_date, student_id) VALUES(?,?,?,?)");
     	    	p.setString(1, coursecodeB.get(i));
     	    	p.setString(2, assignmentsB.get(i));
     	    	p.setString(3, datesB.get(i));
     	    	p.setInt(4, student_id);
     	    	p.executeUpdate();
     	    }
     	        
      	    }catch(Exception e){
    			System.out.println( "error:" + e);
    			success = true;    	       
    	   }
		return success;
    	}
  }
	
