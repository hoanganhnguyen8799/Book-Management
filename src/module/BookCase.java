package module;

import java.sql.Date;
import java.util.List;

public class BookCase {
	private int id;
	private String name;
	private List<CustomPair<Book, Date>> listOfBook;
	private int userid;
	public BookCase() {
		
	}
	public BookCase(int id, String name, List<CustomPair<Book, Date>> listOfBook, int userid) {
		
		this.id = id;
		this.name = name;
		
		this.listOfBook = listOfBook;
		
		
		this.userid = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<CustomPair<Book, Date>> getListOfBook() {
		return listOfBook;
	}
	public void setListOfBook(List<CustomPair<Book, Date>> listOfBook) {
		this.listOfBook = listOfBook;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	
	
	
}
