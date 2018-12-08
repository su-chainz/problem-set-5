import java.util.Scanner;

public class ATM {

	private Database database;
	public ATM(Database database) {
		this.database = database;
	}
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
	
	public void showMenu() {
		
		Scanner in = new Scanner(System.in);
		int choiceAdvanced = 100;
		int choiceSimple = 0;
		String PINCheck;
		int bankAccountNumCheck;
		System.out.println("Hello welcome to the ATM!");
		do {
			System.out.println(" 1 \t Open an Account\n 2\t Log into an Account\n 3\t Exit");
			choiceSimple = Integer.parseInt(in.nextLine());
			if(choiceSimple == 1) {
				String PINinput;
				String last_nameInput;
				String first_nameInput;
				String dobInput;
				String phoneInput;
				String addressInput;
				String cityInput;
				String stateInput;
				String postalCodeInput;
				do  {
					System.out.println("Please enter a PIN number (4 numbers).");
					PINinput = in.nextLine();
					if (PINinput.length() != 4) {
						System.out.println("Please enter a valid PIN.");
					}
				} while (PINinput.length() != 4);
				do  {
					System.out.println("What is your last name? (max 20 characters)");
					last_nameInput = in.nextLine();
					if (last_nameInput.length() > 20) {
						System.out.println("Please enter a valid last name.");
					}
				} while (last_nameInput.length() > 20);
				do  {
					System.out.println("What is your first name? (max 15 characters)");
					first_nameInput = in.nextLine();
					if (first_nameInput.length() > 15) {
						System.out.println("Please enter a valid first name.");
					}
				} while (first_nameInput.length() > 15);
				do  {
					System.out.println("What is your date of birth? (format as follows: YYYYMMDD)");
					dobInput = in.nextLine();
					if (dobInput.length() != 8) {
						System.out.println("Please enter a valid date of birth.");
					}
				} while (dobInput.length() != 8);
				do  {
					System.out.println("What is your phone number? (10 numbers)");
					phoneInput = in.nextLine();
					if (phoneInput.length() != 10) {
						System.out.println("Please enter a valid phone number.");
					}
				} while (phoneInput.length() != 10);
				do  {
					System.out.println("What is your address? (max 30 characters)");
					addressInput = in.nextLine();
					if (addressInput.length() > 30) {
						System.out.println("Please enter a valid adddress.");
					}
				} while (addressInput.length() > 30);
				do  {
					System.out.println("What city do you live in? (max 30 characters)");
					cityInput = in.nextLine();
					if (cityInput.length() > 30) {
						System.out.println("Please enter a valid city.");
					}
				} while (cityInput.length() > 30);
				do  {
					System.out.println("What state do you live in? (2 letter abbreviation)");
					stateInput = in.nextLine();
					if (stateInput.length() != 2) {
						System.out.println("Please enter a valid state.");
					}
				} while (stateInput.length() != 2);
				do  {
					System.out.println("What is your postal code? (5 numbers)");
					postalCodeInput = in.nextLine();
					if (postalCodeInput.length() != 5) {
						System.out.println("Please enter a valid postal code.");
					}
				} while (postalCodeInput.length() != 5);
				int BANewNumber = database.getNextBankAccountNumber();
				System.out.println("Your generated bank account number: " + BANewNumber);
				
				User newUser = new User(PINinput, last_nameInput, first_nameInput, dobInput, phoneInput, addressInput, cityInput, stateInput, postalCodeInput);
				BankAccount newAccount = new BankAccount(BANewNumber, newUser, 0.00, "Y");
				database.addBankAccount(newAccount);
				
			}
			else if(choiceSimple == 2) {
				System.out.println("Please enter your bank account number.");
				bankAccountNumCheck = Integer.parseInt(in.nextLine());
				System.out.println("Please enter your PIN.");
				PINCheck = in.nextLine(); 
				BankAccount account1 = database.getAccount(bankAccountNumCheck);
				if(database.getAccount(bankAccountNumCheck) == null) {
					System.out.println("This account has been closed");
				}
				else if(account1.getUser().getPIN().equals(PINCheck)) {
					while (choiceAdvanced != 8) {
						System.out.println(" 1\t Withdraw Money \n 2 \t Deposit Money \n 3 \t View Balance \n 4 \t Transfer Money \n 5 \t View Information \n 6 \t Update Personal Information \n 7 \t Close your account \n 8 \t Exit");
						choiceAdvanced = Integer.parseInt(in.nextLine());
						if (choiceAdvanced == 1) {
							System.out.println("Enter how much you would like to withdraw.");
							double withdrawAmount_ = in.nextDouble();
							if(withdrawAmount_ <= account1.getBalance() ) {
								account1.withdraw(withdrawAmount_);
								System.out.println("New Balance: $" + account1.getBalance());
								database.updateAccounts();
							}
							else {
								System.out.println("Can't withdraw what you don't have!");
								System.out.println("New Balance: $" + account1.getBalance());
							}
						}
						else if (choiceAdvanced == 2) {
							System.out.println("Enter how much you would like to deposit.");
							double depositAmount_ = in.nextDouble();
							account1.deposit(depositAmount_);
							System.out.println("New Balance: $" + account1.getBalance());
							database.updateAccounts();
						}
						else if(choiceAdvanced == 3) {
							System.out.println("New Balance: $" + account1.getBalance());
						}
						else if(choiceAdvanced == 4) {
							System.out.println("How much would you like to transfer?");
							double transferamount = in.nextDouble();
							System.out.println("Please enter the bank account number that you want to transfer to.");
							int BANuminput = Integer.parseInt(in.nextLine());
							BankAccount accreceive = database.getAccount(BANuminput);
							if(account1.transfer(transferamount, accreceive) == false) {
								System.out.println("Can't transfer what you don't have!");
							}
							else {
								System.out.println("Transfer successful. New Balance: $" + account1.getBalance());
								database.updateAccounts();
							}
							
						}
						else if(choiceAdvanced == 7) {
							System.out.println("Are you sure you would like to close your account? Type 'N' if you would like to close your account");
							String closeChoice = in.nextLine();
							if(account1.setClose(closeChoice) == true) {
								System.out.println("Your acccount has been closed");
								database.updateAccounts();
								break;
							}
							else if(account1.setClose(closeChoice) == false) {
								System.out.println("Your account has not been closed.");
							}
						}
						else if(choiceAdvanced == 5) {
								System.out.println("Your name is " + account1.getUser().getFirst_Name() + " " + account1.getUser().getLast_Name());
								System.out.println("Your date of birth is " + account1.getUser().getdob().substring(6,8) + "/" + account1.getUser().getdob().substring(4,6) + "/" + account1.getUser().getdob().substring(0,4));
								System.out.println("Your phone number is " + account1.getUser().getPhone());
								System.out.println("Your street address is " + account1.getUser().getAddress());
								System.out.println("Your city is " + account1.getUser().getCity());
								System.out.println("Your state is " + account1.getUser().getState());
								System.out.println("Your postal code is " + account1.getUser().getPostal_Code());
								System.out.println(" \n ");
						}
						else if(choiceAdvanced == 6) {
							int personalInfoChoice = 100;
							do {
								System.out.println("What personal information would you like to update? \n 1 \t Phone Number \n 2 \t Address \n 3 \t City \n 4 \t State \n 5 \t Postal Code \n 6 \t PIN \n 7 \t Exit");  
								personalInfoChoice = Integer.parseInt(in.nextLine());
								if (personalInfoChoice == 1) {
									String input;
									do {
										System.out.println("What is your new phone number? Only 10 characters!");
										input = in.nextLine();
									} while(input.length() != 10);
									input = padRight(input, 10);
									account1.getUser().setPhone(input);
								}
								else if (personalInfoChoice == 2) {
									String input;
									do {
										System.out.println("What is your new street address? Only 30 characters please!");
										input = in.nextLine();
									} while(input.length() > 30);
									input = padRight(input, 30);
									account1.getUser().setAddress(input);
								}
								else if (personalInfoChoice == 3) {
									String input;
									do {
										System.out.println("What is your new city? Only 30 characters please!");
										input = in.nextLine();
									} while(input.length() > 30);
									input = padRight(input, 30);
									account1.getUser().setAddress(input);
								}
								else if (personalInfoChoice == 4) {
									String input;
									do {
										System.out.println("What is your new state? Two character abbrieviation please!");
										input = in.nextLine();
									} while(input.length() != 2);
									input = padRight(input, 2);
									account1.getUser().setState(input);
								}
								else if (personalInfoChoice == 5) {
									String input;
									do {
										System.out.println("What is your new postal code?");
										input = in.nextLine();
									} while(input.length() != 5);
									input = padRight(input, 5);
									account1.getUser().setPostal_Code(input);
								}
								else if (personalInfoChoice == 6) {
									String input;
										System.out.println("Please enter your current PIN to confirm your identity.");
										String PINChangeCheck = in.nextLine();
										if(PINChangeCheck == account1.getUser().getPIN() ) {
											System.out.println("PIN check confirmed, please enter the new PIN you would like. 4 characters only");
											input = in.nextLine();
											while(input.length() != 4) {
												System.out.println("That PIN is an incorrect amount of characters.");
												input = in.nextLine();
											}
											input = padRight(input, 4);
											account1.getUser().setPIN(input);
										}
										else {
											System.out.println("PIN's do not match");
										}
								}
								database.updateAccounts();	
							}while(personalInfoChoice != 7);
							
						}
					}
				}
				else {
					System.out.println("Sorry that PIN number is incorrect for the given bank account number.");
				}
			}
		} while(choiceSimple != 3);
		database.updateAccounts();
		System.out.println("Thank you for visiting!");
		in.close();
	}
}