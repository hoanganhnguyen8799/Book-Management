package view;

import java.sql.SQLException;
import java.util.Scanner;

import connectdb.BookDAO;
import connectdb.UserDAO;
import module.User;

public class AdminView {
	private static UserDAO userDAO = new UserDAO();
	private static BookDAO bookDAO = new BookDAO();

	public void adminLogin(Scanner scanner) throws NumberFormatException, Exception {
		System.out.println("=========== Admin login=========== ");
		System.out.println("User name :");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		User admin =userDAO.checkLoginAdmin(username, password);
		
		if ( admin!=null) {

			int choice1;
			do {
				System.out.println("Hello "+admin.getUsername()+", Please select a function bellow by entering the corresponding number");
				System.out.println("1.Create Book" + "\n2.Update Book" + "\n3.Delete Book" + "\n4. Log out");
				System.out.println("(Admin) Select :");
				choice1 = Integer.parseInt(scanner.nextLine());
				switch (choice1) {
				case 1:
					admin_createBook(scanner);
					break;
				case 2:
					admin_updateBook(scanner);
					break;
				case 3:
					admin_deleteBook(scanner);
					break;
				default:
					choice1 = 4;
					System.out.println("Log out");
					System.out.println("=========================");
					break;
				}
			} while (choice1 != 4);

		}

	}

	public static void admin_createBook(Scanner scanner) throws SQLException {
		System.out.println("=========== Created Book=========== ");

		System.out.println("Enter Book's Title: ");
		String title = scanner.nextLine();

		System.out.println("Enter Book's Author: ");
		String author = scanner.nextLine();

		System.out.println("Enter Book's Brief: ");
		String brief = scanner.nextLine();

		System.out.println("Enter Publisher: ");
		String publisher = scanner.nextLine();

		System.out.println("Enter Content: ");
		String content = scanner.nextLine();

		System.out.println("Enter Category:");
		String category = scanner.nextLine();

		int check = bookDAO.createBook(title, author, brief, publisher, content, category);
		if (check > 0) {
			System.out.println("Create book successfully ! ");
		} else {
			System.out.println("Create book fail !");
		}
	}

	public static void admin_updateBook(Scanner scanner) throws ClassNotFoundException, SQLException {
		System.out.println("======== Update Book ========");
		System.out.println("Enter Book's ID: ");
		int bookID = Integer.parseInt(scanner.nextLine());
		System.out.println("Enter Content: ");
		String content = scanner.nextLine();
		int check = bookDAO.update(bookID, content);
		if (check != 0) {
			System.out.println("Update success!!");
		} else {
			System.err.println("Update FAIL !!");
		}
	}

	public static void admin_deleteBook(Scanner scanner) throws ClassNotFoundException, SQLException {
		System.out.println("======== Delete Book ========");
		System.out.println("Enter Book's ID: ");
		int bookID = Integer.parseInt(scanner.nextLine());
		int check = bookDAO.delete(bookID);
		if (check != 0) {
			System.out.println("Delete success !!!");
		} else {
			System.err.println("Delete FAIL !!");
		}

	}

}
