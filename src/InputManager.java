import java.util.Scanner;

public class InputManager {



    CommandManager command = new CommandManager();
    Scanner scanner = new Scanner(System.in);




    public void gameLoop ()
    {



        System.out.println("Welcome! To Explorers RPG Type a command to start OR /help for help");

        while (true) {
            System.out.print("> ");
            String[] input = scanner.nextLine().trim().split(" ");



            switch (input[0].toLowerCase()) {

                case "/help":
                    command.help();
                    break;
                case "/new":
                    command.newGame(input);
                    break;
                case "/move":
                    command.move(input);
                    break;
                case "/grab":
                    command.grab(input);
                    break;
                case "/drop":
                    command.drop(input);
                    break;

                default:

                    System.out.println(input[0] + ": Invalid Command Please try Again");



            }



            System.out.println("---------------------------------------------------");

        }


    }







}
