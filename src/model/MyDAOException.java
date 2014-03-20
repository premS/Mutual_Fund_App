//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//21-Nov-2013
package model;

public class MyDAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MyDAOException(Exception e) { super(e); }
	public MyDAOException(String s)    { super(s); }
}
