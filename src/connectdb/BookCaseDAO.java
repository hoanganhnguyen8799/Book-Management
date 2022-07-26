package connectdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import module.Book;
import module.BookCase;
import module.CustomPair;

public class BookCaseDAO extends DAO {
	// insert a book in to Book table
	private static final String CREATE_NEW_BOOKCASE = "insert into BookCase(book_case_name,userid) values (?,?)";
	// select bookcase from BookCase table by userID
	private static final String SELECT_BOOKCASE_BY_USERID = "select BookCase.book_case_id ,BookCase.book_case_name from BookCase where BookCase.userid =?";



	/** Add a BookCase into BookCase table
	 * @param bookcaseName
	 * @param userid
	 * @return Returns the number of lines affected when executing statements
	 * @throws SQLException
	 */
	public int create_New_BookCase(String bookcaseName, int userid) throws SQLException {
		Connection connect = getConnect();
		
		int check =-1;
		try {
			PreparedStatement stm = connect.prepareStatement(CREATE_NEW_BOOKCASE);
			stm.setString(1, bookcaseName);
			stm.setInt(2, userid);

			
			
			check= stm.executeUpdate();
		}  finally {

			if(connect!=null) { connect.close();}
			
		}
		return check;
		
	}
	
	/** Select Bookcase from BookCase table by userID
	 * 
	 * @param userid
	 * @return Return a BookCase of user
	 * @throws SQLException
	 */
	public BookCase getBookCase_By_UserID(int userid) throws SQLException {
		Connection connect = getConnect();
		BookDAO bookDAO = new BookDAO();
		BookCase bookcase = null;
		int bookcaseID =-1;
		String bookcaseName = null;
		PreparedStatement stm = null;
		ResultSet res =null;
		try {
			stm = connect.prepareStatement(SELECT_BOOKCASE_BY_USERID);
			stm.setInt(1, userid);
			res = stm.executeQuery();
			while (res.next()) {
				bookcaseID = res.getInt(1);
				bookcaseName = res.getString(2);

			}
			// Check bookcase exist ?
			if (bookcaseID < 0)
				return null;
			// get list books of bookcase by bookcaseID
			List<CustomPair<Book, Date>> listOfBook = bookDAO.get_list_Book_Of_BookCase(bookcaseID);
			bookcase = new BookCase(bookcaseID, bookcaseName, listOfBook, userid);
		} finally {
			if(res!=null) {res.close();}
			if(stm!=null) { stm.close();}
			if(connect!=null) { connect.close();}
		
			
		}

		
		return bookcase;

	}

}
