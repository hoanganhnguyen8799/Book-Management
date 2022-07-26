package view;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import connectdb.BookDAO;
import module.Book;

public class BookView {
	private static BookDAO bookDAO = new BookDAO();

	public static void show_BookDetail(Book book) {
		System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", book.getId(), book.getBook_title(), book.getAuthor(),
				book.getPublisher(), book.getCategory());
	}

	public void searchBook(Scanner scanner) throws SQLException {
		int choice;
		List<Book> list = bookDAO.getAllBook();
		do {
			System.out.println("Please select type:");
			System.out.println("1.By name" + "\n2.By author" + "\n3.By category");
			choice = Integer.parseInt(scanner.nextLine());
			String input;
			int count = 0;
			switch (choice) {
			case 1:
				System.out.println("Enter book'name:");
				input = scanner.nextLine();
				System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", "ID", "Title", "Author", "Publisher", "Category");
				for (Book i : list) {
					if (i.getBook_title().contains(input)) {
						show_BookDetail(i);
						count++;
					}
				}
				System.out.println("About " + count + " results ");
				count = 0;
				break;
			case 2:
				System.out.println("Enter book'author:");
				input = scanner.nextLine();
				System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", "ID", "Title", "Author", "Publisher", "Category");
				for (Book i : list) {
					if (i.getAuthor().contains(input)) {
						show_BookDetail(i);
						count++;
					}
				}
				System.out.println("About " + count + " results ");
				count = 0;
				break;
			case 3:
				System.out.println("Enter book'category:");
				input = scanner.nextLine();
				System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", "ID", "Title", "Author", "Publisher", "Category");
				for (Book i : list) {
					if (i.getCategory().contains(input)) {
						show_BookDetail(i);
						count++;
					}
				}
				System.out.println("About " + count + " results ");
				count = 0;
				choice = 3;
				break;
			default:
				choice = 4;
				break;
			}
		} while (choice != 4);
	}

	public static void showAllBook() throws SQLException {
		List<Book> list_All_Book = bookDAO.getAllBook();
		if (list_All_Book.isEmpty() ) {
			System.out.println("The shelf is empty !!");
		} else {
			System.out.printf("%-8s%-35s%-10s%-15S%-10s\n", "ID", "Title", "Author", "Publisher", "Category");
			for (Book i : list_All_Book) {
				show_BookDetail(i);
			}

		}
	}
}
