package org.ConsoleApplication;

import org.DAOs.UserDAO;
import org.dataClasses.UserData;

import java.util.Scanner;

public class ConsoleDatabaseApplication {

    UserDAO handledDAO;
    Scanner scanner;

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.flush();
        System.err.println(message);
    }

    public String readLine() {
        try  {
            return scanner.nextLine();
        }
        catch (Exception e) {
            showError("Invalid input.");
            return null;
        }

    }

    public int readInt() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            showError("Invalid input.");
            return -1;
        }
    }

    private void showInterface() {
        String str = """
                Simple database console application
                Application supports CRUD commands
                
                What do you want to do?
                1 Create a new entry
                2 Read all entries
                3 Update an entry by id
                4 Delete an entry
                5 Change table
                6 Exit
                
                Input a number:
                """;
        showMessage(str);
    }

    private void createEntry() {
        readLine();
        showMessage("Input name: ");
        String name = readLine();
        showMessage("Input email: ");
        String email = readLine();
        showMessage("Input Age: ");
        int age = readInt();


        handledDAO.create(new UserData(name, email, age));
        showMessage("Entry created");
    }


    private void readEntries() {
        showMessage("All persistent entries:");
        handledDAO.findAll().forEach(System.out::println);
        readLine();
    }

    private void updateEntry() {
        showMessage("Input id:");
        int id = readInt();

        UserData handledObject = handledDAO.findById(id);
        if (handledObject == null) {
            showError("Entry not found");
            readLine();
            return;
        }

        showMessage("""
                What do you want to do?
                1 Change name
                2 Change email
                3 Change age
                4 Exit
                Input a number:""");

        try {
            switch (readInt()) {
                case 1: {
                    readLine();
                    showMessage("Input name: ");
                    handledObject.setName(readLine());
                    break;
                }
                case 2: {
                    readLine();
                    showMessage("Input email: ");
                    handledObject.setEmail(readLine());
                    break;
                }
                case 3: {
                    readLine();
                    showMessage("Input age: ");
                    try {
                        handledObject.setAge(readInt());
                    } catch (Exception e) {
                        showError("Please enter a valid number");
                        readLine();
                        return;
                    }
                    break;
                }
                case 4: {
                    return;
                }
                default: {
                    showError("Please enter a valid number");
                    readLine();
                    return;

                }
            }

        } catch (Exception e) {
            showError("Please enter a valid number");
            readLine();
            return;
        }
        handledDAO.update(handledObject);

    }

    private void deleteEntry() {
        showMessage("Input id:");
        int id = readInt();

        UserData handledObject = handledDAO.findById(id);
        if (handledObject == null) {
            showError("Entry not found");
            readLine();
            return;
        }
        handledDAO.delete(handledObject);
    }

    private void changeTable() {
        showMessage("Not implemented yet");
    }

    private void exit() {
        Thread.currentThread().interrupt();
    }

    public void run() {
        scanner = new Scanner(System.in);
        handledDAO = new UserDAO();
        while (!Thread.interrupted()) {
            showInterface();
            int choice = readInt();
            switch (choice) {
                case 1: {
                    createEntry();
                    break;
                }
                case 2: {
                    readEntries();
                    break;
                }
                case 3: {
                    updateEntry();
                    break;
                }
                case 4: {
                    deleteEntry();
                    break;
                }
                case 5: {
                    changeTable();
                    break;
                }
                case 6: {
                    exit();
                    break;
                }
                default: {
                    showError("Invalid command");
                    break;
                }
            }

        }
        scanner.close();
    }

}
