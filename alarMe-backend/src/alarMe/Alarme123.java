package alarMe;

import java.sql.SQLException;

public class Alarme123 extends LoginProcess {
	
//This is the program that runs the classes. 
//This is the AlarMe "server". 
	
	public Alarme123(){
		super(username, user_password, driver);
	} 
	 
	
    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
	   	
    	Alarme123 program = new Alarme123();
	   	program.setConnection();
	   	program.init(); 
	   	
	   	if(program.checkForNewUser() == true){
			System.out.println("Ny bruker er lagt til");
			
	   		Exams exams = new Exams();
	   		exams.setExams();
	   		exams.addExamsToDatabase();
	   		Thread.sleep(3000);

	   		ItsLearning assI = new ItsLearning();
	   		assI.setAssignmentsI();
	   		assI.addAssignmentsIToDatabase();
	   		Thread.sleep(3000);
	   		
	   		Blackboard black = new Blackboard();
	   		black.setAssignmentsB();
	   		black.checkForEqualAssignments();
	   		black.addAssignmentsBToDatabase();
	   		Thread.sleep(3000);
	   		   		   		   		
	   		driver.close();
	   		
	   	}	   	

    }
  
  

}
