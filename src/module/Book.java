package module;

public class Book {
	private int id;
	private String book_title;
	private String author;
	private String brief;
	private String publisher;
	private String content;
	private String category;
	public Book() {
		
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String book_title, String author, String brief, String publisher, String content,
			String category) {
		
		this.id = id;
		this.book_title = book_title;
		this.author = author;
		this.brief = brief;
		this.publisher = publisher;
		this.content = content;
		this.category = category;
	}
	public Book(String book_title, String author, String brief, String publisher, String content, String category) {
		
		this.book_title = book_title;
		this.author = author;
		this.brief = brief;
		this.publisher = publisher;
		this.content = content;
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBook_title() {
		return book_title;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
