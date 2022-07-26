package view;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		AdminView adminView = new AdminView();
		UserView userView = new UserView();
		Scanner scanner = null;
		int choice;
		try {
			scanner = new Scanner(System.in);
			do {
				System.out.println("1. Admin login " + "\n2. User login" + "\n3. Exit");
				System.out.println("Select: ");
				choice = Integer.parseInt(scanner.nextLine());
				
				switch (choice) {
				case 1:
					adminView.adminLogin(scanner);
					break;
				case 2:
					userView.userLogin(scanner);
					break;
				default:
					choice = 3;
					System.out.println("User Exited !!!!");
					break;
				}

			} while (choice!=3);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

	}
}
