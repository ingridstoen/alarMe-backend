package alarMe;

import java.sql.SQLException;

public class Alarme123 extends LoginProcess {
	
	public Alarme123(){
		super(username, user_password, driver);
	}
	 
	
    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
	   	
    	Alarme123 program = new Alarme123();
	   	program.setConnection();
	   	program.init();
	   	
	   	if(program.checkForNewUser() == true){
			System.out.println("Ny bruker er lagt til: ");
			System.out.println("Brukernavn = " + username);
			System.out.println("Passord = " + user_password);

	   		ItsLearning assI = new ItsLearning();
	   		assI.setAssignmentsI();
	   		System.out.println(assI.getCoursecodeI());
	   		System.out.println(assI.getAssignmentsI());
	   		System.out.println(assI.getDatesI());
	   		assI.addAssignmentsIToDatabase();
	   		Thread.sleep(3000);
	   		
	   		Blackboard black = new Blackboard();
	   		black.setAssignmentsB();
	   		black.checkForEqualAssignments();
	   		System.out.println(black.getAssignmentsB());
	   		System.out.println(black.getCoursecodeB());
	   		System.out.println(black.getDatesB());
	   		black.addAssignmentsBToDatabase();
	   		Thread.sleep(3000);
	   		   		   		
	   		Exams exams = new Exams();
	   		exams.setExams();
	   		System.out.println(exams.getCourseCode());
	   		System.out.println(exams.getCourseName());
	   		System.out.println(exams.getExamDate());
	   		exams.addExamsToDatabase();
	   		Thread.sleep(3000);
	   		
	   		driver.close();
	   		
	   	}	   	

    }
  
  

}
