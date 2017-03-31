package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import alarMe.Blackboard;
import alarMe.ConnectToDatabase;
import alarMe.Exams;
import alarMe.ItsLearning;
import alarMe.LoginProcess;

public class AllTests {

	protected static WebDriver driver = new ChromeDriver();
	
	@Test
	public void BlackboardTest() {
		Blackboard b = new Blackboard();
		b.checkForEqualAssignments(); 
		b.setAssignmentsB();
		//String a = b.getAssignmentsB().get(0);
		//String c = b.getAssignmentsB().get(1);
		String d = b.getCoursecodeB().get(0);
		//String e = b.getCoursecodeB().get(1);
		String f = b.getDatesB().get(0);
		//String g = b.getDatesB().get(1);
		//assertEquals("Øving 4 / Exercise 4", c);
		//assertEquals("Test 2 middels", a);
		//assertEquals("TDT4145", e);
		assertEquals("TFY4125", d);
		//assertEquals("30.03", g);
		assertEquals("05.05", f);
		assertTrue(b.addAssignmentsBToDatabase());

	}
	
	@Test
	public void ItsLearningTest() {
		ItsLearning i = new ItsLearning();
		i.setAssignmentsI();
		String a = i.getCoursecodeI().get(0);
		/*String b = i.getCoursecodeI().get(1);
		String c = i.getCoursecodeI().get(2);
		String d = i.getCoursecodeI().get(3);
		String e = i.getCoursecodeI().get(4);
		String f = i.getCoursecodeI().get(5);*/
		//String g = i.getAssignmentsI().get(0);
		//String h = i.getDatesI().get(0);
		assertEquals("TDT4140", a);
		/*assertEquals("TTM4100", b);
		assertEquals("TTM4100", c);
		assertEquals("TDT4140", d);
		assertEquals("TTM4100", e);
		assertEquals("TDT4140", f);*/
		//assertEquals("Resubmission of Posters, Step 4: Demonstration for Productionization Core 1", g);
		//assertEquals("24.02", h);
		assertTrue(i.addAssignmentsIToDatabase());
	}
	

	@Test
	public void ConnectToDatabaseTest() {
		ConnectToDatabase c = new ConnectToDatabase();
		assertTrue(c.checkForNewUser());
		//assertEquals("sjekker om ny bruker eksisterer",true,c.checkForNewUser());
		//assertNotEquals(false, c.checkForNewUser());
		String a = c.getUserName();
		String b = c.getUserPassword();
		String d = Integer.toString(c.getStudentId());
		assertEquals("ingridbs", a);
		assertEquals("In0191*s", b);
		assertEquals("119", d);
		
	}
	
	@Test
	public void ExamsTest() {
		Exams e = new Exams();
		e.setExams();
		String a = e.getExamDate().get(0);
		assertEquals("22.05.2017", a);
		String b = e.getExamDate().get(1);
		assertEquals("30.05.2017", b);
		String c = e.getExamDate().get(2);
		assertEquals("07.06.2017", c); 
		String d = e.getCourseCode().get(0);
		assertEquals("TTM4100", d);
		String f = e.getCourseCode().get(1);
		assertEquals("TFY4125", f);
		String g = e.getCourseCode().get(2);
		assertEquals("TDT4145", g);
		String h = e.getCourseName().get(0);
		assertEquals("Kommunikasjon - Tjenester og nett", h);
		String i = e.getCourseName().get(1);
		assertEquals("Fysikk . Skriftlig eksamen", i);
		String j = e.getCourseName().get(2);
		assertEquals("Datamodellering og databasesystemer", j);
		assertTrue(e.addExamsToDatabase());
	    
	} 
	
	@Test
	public void LoginProcessTest() {
		LoginProcess l = new LoginProcess("ingridbs","In0191*s",driver);
		String a = l.getUserName();
		assertEquals("ingridbs", a);
		String b = l.getUserPassword();
		assertEquals("In0191*s", b);
				 
	}



}
