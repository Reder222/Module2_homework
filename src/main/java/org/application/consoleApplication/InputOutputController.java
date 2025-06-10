package org.application.consoleApplication;

import java.util.Scanner;

public class InputOutputController {

    private static InputOutputController inputOutputController;
    private static Scanner scanner;

    private InputOutputController() {
        scanner = new Scanner(System.in);
        inputOutputController = this;
    }

    public static InputOutputController getInstance() {
        if(inputOutputController == null) {
            return new InputOutputController();
        }
        return inputOutputController;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.err.println(message);
    }

    public void emptyLine(){
        showMessage("Press enter to continue");
        readLine();
    }

    public String readLine() {
        try  {
            return scanner.nextLine();
        }
        catch (Exception e) {
            showError("Invalid input. Try again.");
            return readLine();
        }

    }

    public int readInt() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            showError("Invalid input. Try again.");
            readLine();
            return readInt();
        }
    }

    public void close() {
        scanner.close();
        inputOutputController = null;
    }
}
