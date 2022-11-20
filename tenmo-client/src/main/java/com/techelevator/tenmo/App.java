package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.RestAccountService;
import com.techelevator.tenmo.services.RestTransferService;
import com.techelevator.util.BasicLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final RestAccountService restAccountService = new RestAccountService();
    private final RestTransferService restTransferService = new RestTransferService();
    private final Scanner scanner = new Scanner(System.in);
    private final Transfer transfer = new Transfer();
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        restAccountService.setAuthToken(currentUser.getToken());
        restTransferService.setAuthToken(currentUser.getToken());
        restTransferService.setUser(currentUser);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                System.out.println("Want to view your pending requests? Upgrade today!");
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                System.out.println("Want to request transfers? Upgrade today!");
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub !!!!!!!"
        Account account = restAccountService.getAccountBalance();
        System.out.println("Your balance is: " + account.getBalance());

//        System.out.println("Get BigDecimal: " + restAccountService.getAccountBalance());
    }

    private void viewTransferHistory() {

        restTransferService.getTransferHistory();

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {

        // TODO Auto-generated method stub

        int menuSelection = -1;
        while (menuSelection != 0) {
            System.out.println("Please pick a user ID");
            System.out.println(Arrays.toString(restAccountService.getAllUsers()));
            String input = scanner.nextLine();
            int newInt = Integer.parseInt(input);
            if (restAccountService.getUserById(newInt) != null) {
                System.out.println("Enter the amount you want to send");
                //stores the input amount
                String transferAmount = scanner.nextLine();
                //here is the transfer below
                BigDecimal createdTransfer = transfer.processMoney(transferAmount);

                //tries to make the transfer
                try {
                    createdTransfer.compareTo(createdTransfer);
                    Transfer transfer = new Transfer();
                    transfer.setStatus_id(2);
                    transfer.setType_id(2);
                    transfer.setAcct_from(restAccountService.getAccountIdByUserId(currentUser.getUser().getId()));
                    transfer.setAcct_to(restAccountService.getAccountIdByUserId(newInt));
                    transfer.setAmount(createdTransfer);
                    //transfer receipt output after successful
                    System.out.println(transfer);

                    //transfer happens via client
                    restTransferService.sendMoney(transfer);

                    mainMenu();

//                    consoleService.printMainMenu();
//                    menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");

                } catch (NumberFormatException n) {
                    BasicLogger.log(n.getMessage());
                }

                //System.out.println(consoleService.promptForBigDecimal("Enter your amount: "));
//                System.out.println("Approved. You have sent money to: ");

            } else {
                System.out.println("Invalid Id, please enter a valid ID");
            }
        }
        //restTransferService.sendMoney()

    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

}
