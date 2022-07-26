package connectdb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import module.Book;
import module.CustomPair;

public class BookDAO extends DAO {
	// This query to select list of books, create date from Book table and Contain table by bookcase id.
	private static final String SELECT_LIST_BOOK_OF_A_BOOKCASE = "select Book.bookid,Book.title,Book.author,Book.brief,Book.publisher,Book.content,Book.category,"
			+ " create_date from Contain,Book " + "where book_case_id=? and Contain.bookid=Book.bookid";
	// This query to insert a book into book table
	private static final String CREATE_BOOK = "insert into Book(title,author,brief,publisher,content,category) values (?,?,?,?,?,?)";
	//This query to delete a book by bookID and bookcaseID in Contain table.
	private static final String DELETE_BOOK_FROM_BOOKCASE = "delete Contain where book_case_id =? and bookid=?";
	//This query to insert book id, create date , bookcaseID into Contain table
	private static final String ADD_A_BOOK_INTO_BOOKCASE = "insert into Contain(book_case_id,bookid,create_date) values (?,?,?)";
	//This query to select all books in Book table
	private static final String SELECT_ALL_BOOK = "select Book.bookid,Book.title,Book.author,Book.brief,Book.publisher,Book.content,Book.category from Book";
	// This query to delete all book in Contain table by bookcaseID
	private static final String CLEAR_BOOKCASE = "delete Contain where book_case_id =?";

	/**
	 * @param title
	 * @param author
	 * @param brief
	 * @param publisher
	 * @param content
	 * @param category
	 * @return Returns the number of lines affected when executing statements
	 * @throws SQLException
	 */
	public int createBook(String title, String author, String brief, String publisher, String content,
			String category) throws SQLException {
		
		Connection connect = getConnect();
		PreparedStatement stm = null;
		try {
			stm = connect.prepareStatement(CREATE_BOOK);
			stm.setString(1, title);
			stm.setString(2, author);
			stm.setString(3, brief);
			stm.setString(4, publisher);
			stm.setString(5, content);
			stm.setString(6, category);
			
			return stm.executeUpdate();

			
		} finally {

			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		

	}

	/** This method to update a book in Book table in database
	 * @param id
	 * @param content
	 * @return Returns the number of lines affected when executing statements
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int update(int id, String content) throws ClassNotFoundException, SQLException {
		int updateStatus = 0;
		Connection conn = getConnect();
		String sql = "UPDATE Book set content='" + content + "' WHERE bookid='" + id + "'";
		Statement stm1 = conn.createStatement();
		updateStatus = stm1.executeUpdate(sql);
		stm1.close();
		conn.close();
		return updateStatus;
	}

	/** This method to delete a book from Book table in database
	 * @param id
	 * @return Returns the number of lines affected when executing statements
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int delete(int id) throws SQLException, ClassNotFoundException {
		int deleteStatus = 0;

		Connection connection = getConnect();
		String sql = "DELETE FROM Book WHERE bookid ='" + id + "'";
		Statement stm1 = connection.createStatement();
		deleteStatus = stm1.executeUpdate(sql);
		stm1.close();
		connection.close();
		return deleteStatus;
	}

	/** This method to add a book into Bookcase' user
	 * @param bookcaseid
	 * @param bookid
	 * @return Returns the number of lines affected when executing statements
	 */
	public int add_A_Book_into_Bookcase(int bookcaseid, int bookid) {
		Connection connect = getConnect();
		PreparedStatement stm = null;
		java.sql.Date date1 = new java.sql.Date(System.currentTimeMillis());
		int check = -1;
		try {
			stm = connect.prepareStatement(ADD_A_BOOK_INTO_BOOKCASE);
			stm.setInt(1, bookcaseid);
			stm.setInt(2, bookid);
			stm.setString(3, date1.toString());
			check =stm.executeUpdate();
			stm.close();
			connect.close();
		} catch (SQLException e) {
			
		} finally {
			
			if(stm!=null) { try {
				stm.close();
			} catch (SQLException e) {
//				System.err.println("Book already existed !");
				
			}}
			if(connect!=null) { try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return check;

	}

	/** This method is used to get all books from Book table
	 * @return Return a list of all books 'list'
	 * @throws SQLException
	 */
	public List<Book> getAllBook() throws SQLException {
		Connection connect = getConnect();
		ResultSet res =null;
		PreparedStatement stm = null;
		List<Book> list = new ArrayList<>();
		try {
			stm = connect.prepareStatement(SELECT_ALL_BOOK);
			res = stm.executeQuery();
			while (res.next()) {
				Book book = null;
				int bookid = res.getInt(1);
				String bookTitle = res.getString(2);
				String author = res.getString(3);
				String brief = res.getString(4);
				String publisher = res.getString(5);
				String content = res.getString(6);
				String category = res.getString(7);
				book = new Book(bookid, bookTitle, author, brief, publisher, content, category);
				list.add(book);
			}

		} finally {

			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		return list;

	}

	/**This method is used to get list of books and create date in Bookcase'user by bookcase id
	 * @param bookcaseId
	 * @return	Return a list contain pair of 'Book' and 'Date'
	 * @throws SQLException
	 */
	public List<CustomPair<Book, Date>> get_list_Book_Of_BookCase(int bookcaseId) throws SQLException {
		Connection connect = getConnect();
		ResultSet res =null;
		PreparedStatement stm = null;
		List<CustomPair<Book, Date>> list = new ArrayList<>();
		Book book = null;
		try {
			stm = connect.prepareStatement(SELECT_LIST_BOOK_OF_A_BOOKCASE);
			stm.setInt(1, bookcaseId);
			 res = stm.executeQuery();
			while (res.next()) {
				int bookId = res.getInt(1);
				String bookTitle = res.getString(2);
				String author = res.getString(3);
				String brief = res.getString(4);
				String publisher = res.getString(5);
				String content = res.getString(6);
				String category = res.getString(7);
				
				
				book = new Book(bookId, bookTitle, author, brief, publisher, content, category);
				Date date = res.getDate(8);
				CustomPair<Book, Date> custompair = new CustomPair<Book, Date>(book, date);
				list.add(custompair);
			}

		}finally {

			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		return list;

	}

	/** Delete a row bookID and bookcaseID in Contain table by bookID and bookcaseID
	 * @param bookcaseID
	 * @param bookid
	 * @return Returns the number of lines affected when executing statements
	 * @throws SQLException
	 */
	public int deleteABook_from_BookCase(int bookcaseID, int bookid) throws SQLException {
		Connection connect = getConnect();
		PreparedStatement stm = null;
		int check = -1;
		try {
			stm = connect.prepareStatement(DELETE_BOOK_FROM_BOOKCASE);
			stm.setInt(1, bookcaseID);
			stm.setInt(2, bookid);
			check = stm.executeUpdate();
			stm.close();
			connect.close();
		} finally {
			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		return check;
	}
	
	/** Delete rows in Contain table by bookcaseID
	 * @param bookcaseID
	 * @return
	 * @throws SQLException
	 */
	public int clearBookCase(int bookcaseID) throws SQLException {
		Connection connect = getConnect();

		PreparedStatement stm = null;
		int check = -1;
		try {
			stm = connect.prepareStatement(CLEAR_BOOKCASE);
			stm.setInt(1, bookcaseID);
			check = stm.executeUpdate();
			stm.close();
			connect.close();
		}finally {

			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		}
		return check;

	}

}
