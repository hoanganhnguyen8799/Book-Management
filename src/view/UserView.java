package view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import connectdb.*;
import module.*;

public class UserView {
	private static UserDAO userDAO = new UserDAO();
	private static BookCaseDAO bookCaseDAO = new BookCaseDAO();
	private static BookDAO bookDAO = new BookDAO();
	private static BookView bookView = new BookView();

	public void userLogin(Scanner scanner) throws SQLException {
		System.out.println("=========== User login=========== ");
		System.out.println("User name :");
		String username1 = scanner.nextLine();
		System.out.println("Password: ");
		String password1 = scanner.nextLine();
		User user = userDAO.checkLoginUser(username1, password1);
		if (user != null) {
			int choice1;
			
			do {
				System.out.println("Hello "+user.getUsername()+", Please select a function bellow by entering the corresponding number");
				System.out.println("1.Search Book" + "\n2.View List Book" + "\n3.View Book Detail" + "\n4.View BookCase"
						+ "\n5.Edit BookCase" + "\n6. Log out");
				System.out.println("(User) Select :");
				choice1 = Integer.parseInt(scanner.nextLine());
				BookCase bookcase = bookCaseDAO.getBookCase_By_UserID(user.getId());
				switch (choice1) {
				case 1:
					System.out.println("==========Search Book==========");

					bookView.searchBook(scanner);

					System.out.println("===========================");
					break;
				case 2:
					System.out.println("==========View List Book==========");

					BookView.showAllBook();

					System.out.println("===========================");
					break;
				case 3:
					System.out.println("==========View Book Detail==========");
					List<Book> list = bookDAO.getAllBook();
					System.out.println("Enter Book' ID: ");
					int bookid = Integer.parseInt(scanner.nextLine());
					System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", "ID", "Title", "Author", "Publisher", "Category");
					for (Book i : list) {
						if (bookid == i.getId()) {
							BookView.show_BookDetail(i);
							System.out.println(" Content: "+i.getContent());
						}
					}

					System.out.println("===========================");
					break;
				case 4:
					System.out.println("==========View BookCase ==========");
					if (bookcase == null) {
						System.out.println("Your BookCase does not exist ");
					} else {
						System.out.println("BooKCase: " + bookcase.getName());
						;
						showBookCase(bookcase);
					}

					System.out.println("===========================");
					break;
				case 5:
					System.out.println("==========Edit BookCase==========");
					bookcase = user_Edit_BookCase(scanner, user, bookcase);
					System.out.println("===========================");

					break;
				default:
					choice1 = 6;
					System.out.println("Log out !!!");
					System.out.println("===========================");
					break;
				}
			} while (choice1 != 6);
		} else {
			System.err.println("Wrong username or password !!!");
			System.out.println("===========================");
		}
	}
	// ===========================================================================================================================

	public static BookCase user_Edit_BookCase(Scanner scanner, User user, BookCase bookcase) throws SQLException {
	
		if (bookcase==null) {
			System.out.println("Your BookCase does not exit !!!");
			System.out.println("Create BookCase :");
			System.out.println("Enter BookCase's name: ");
			String bookcaseName = scanner.nextLine();

			bookCaseDAO.create_New_BookCase(bookcaseName, user.getId());
			bookcase = bookCaseDAO.getBookCase_By_UserID(user.getId());
		}
		List<CustomPair<Book, Date>> listOf_New_Books = null;
		int t;
		do {
			System.out.println("1.Add a book" + "\n2.Delete a book" + "\n3.Clear BookCase" + "\n4.Exit");
			t = Integer.parseInt(scanner.nextLine());
			switch (t) {
			case 1:
				System.out.println("========= ADD A BOOK =========");
				user_Add_A_Book(scanner, bookcase.getId());
				if (listOf_New_Books != null) {
					listOf_New_Books.clear();
				}

				listOf_New_Books = bookDAO.get_list_Book_Of_BookCase(bookcase.getId());
				bookcase.setListOfBook(listOf_New_Books);
				System.out.println("===========================");
				break;
			case 2:
				System.out.println("========= REMOVE A BOOK =========");
				if (bookcase.getListOfBook().isEmpty() ) {
					System.out.println("Your BookCase has no book !!!");
				} else {
					user_Delete_A_Book_From_Bookcase(scanner, bookcase.getId());
				}

				
				break;
			case 3:
				System.out.println("========= CLEAR BOOKCASE =========");
				clearBookCase(bookcase.getId());
				System.out.println("===========================");
				break;
			
			default:
				break;
			}
		} while (t != 4);
		return bookcase;

	}

	// Edit Bookcase ===> Add a book
	public static void user_Add_A_Book(Scanner scanner, int bookcaseID) throws SQLException {

		List<Book> listOfAllBook = bookDAO.getAllBook();
		if (listOfAllBook.isEmpty() ) {
			System.err.println("THe shelf is empty !! ");

		} else {
			System.out.println("List of all books in shelf :");
			for (Book i : listOfAllBook) {
				System.out.print(" " + i.getId());
			}
			System.out.println();
			System.out.println("Enter bookID to add into your bookcase:");
			int bookid = Integer.parseInt(scanner.nextLine());

			int check = bookDAO.add_A_Book_into_Bookcase(bookcaseID, bookid);
			if (check > 0) {
				System.out.println("Add a book into bookcase success !!!");
			} else {
				System.err.println("Add a book FAIL !!!!");
			}

		}

	}

	// Edit bookcase ===> delete a Book
	public static void user_Delete_A_Book_From_Bookcase(Scanner scanner, int bookcaseID) throws SQLException {
		System.out.println("Enter Book'ID to remove: ");
		int bookID = Integer.parseInt(scanner.nextLine());
		int check = bookDAO.deleteABook_from_BookCase(bookcaseID, bookID);
		if (check > 0) {
			System.out.println("Remove successfully !!");
		} else {
			System.err.println("Remove FAIL !!!");
		}
		System.out.println("=============================");

	}

	public static void showBookCase(BookCase bookcase) {
		List<CustomPair<Book, Date>> listBook_Of_BookCase = bookcase.getListOfBook();

		System.out.printf("%-12s%-30s%-12s\n", "ID", "Book", "CreateDate");
		for (CustomPair<Book, Date> i : listBook_Of_BookCase) {
			System.out.printf("%-12s%-30s%-12s\n", i.getKey().getId(), i.getKey().getBook_title(), i.getValue());

		}
	}

	// Edit bookcase ==> Clear Bookcase
	public static void clearBookCase(int bookcaseID) throws SQLException {
		int check = bookDAO.clearBookCase(bookcaseID);
		if (check > 0) {
			System.out.println("Your BookCase is empty !!!");
		} else {
			System.err.println("Clear FAIL !!!");
		}
	}

	
}
