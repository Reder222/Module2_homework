package org.application.consoleApplication;


import org.application.dataClasses.UserData;
import org.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

@Controller
public class ConsoleDatabaseApplication {

    InputOutputController inputOutputController;

    @Autowired
    UserService userService;


    public ConsoleDatabaseApplication() {
        inputOutputController = InputOutputController.getInstance();
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
        inputOutputController.showMessage(str);
    }

    private void createEntry() {
        inputOutputController.emptyLine();

        inputOutputController.showMessage("Input name: ");
        String name = inputOutputController.readLine();
        inputOutputController.showMessage("Input email: ");
        String email = inputOutputController.readLine();
        inputOutputController.showMessage("Input Age: ");
        int age = inputOutputController.readInt();

        inputOutputController.showMessage(userService.create(name, email, age));

    }


    private void readEntries() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("All persistent entries:");
        userService.getAll().forEach(entry -> inputOutputController.showMessage(entry.toString()));
    }

    private void updateEntry() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Input id:");
        int id = inputOutputController.readInt();

        UserData handledObject = userService.getByID(id);
        if (handledObject == null) {
            inputOutputController.showError("Entry not found");
            return;
        }

        inputOutputController.showMessage("""
                What do you want to do?
                1 Change name
                2 Change email
                3 Change age
                4 Exit
                Input a number:""");

        while (true)
            switch (inputOutputController.readInt()) {
                case 1: {
                    inputOutputController.showMessage("Input name: ");
                    handledObject.setName(inputOutputController.readLine());
                    break;
                }
                case 2: {
                    inputOutputController.showMessage("Input email: ");
                    handledObject.setEmail(inputOutputController.readLine());
                    break;
                }
                case 3: {
                    inputOutputController.showMessage("Input age: ");
                    handledObject.setAge(inputOutputController.readInt());
                    break;
                }
                case 4: {
                    userService.update(handledObject);
                    return;
                }
                default: {
                    inputOutputController.showError("Please enter a valid number");
                    break;
                }
            }


    }

    private void deleteEntry() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Input id:");
        int id = inputOutputController.readInt();

        if (userService.delete(id)) {
            inputOutputController.showMessage("Entry deleted");
        } else inputOutputController.showMessage("Entry not found");

    }

    private void changeTable() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Not implemented yet");
    }

    private void exit() {
        Thread.currentThread().interrupt();
    }

    public void run() {
        while (!Thread.interrupted()) {
            showInterface();
            switch (inputOutputController.readInt()) {
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
                    inputOutputController.showError("Invalid command");
                    break;
                }
            }

        }
        inputOutputController.close();
    }

}
