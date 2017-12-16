import java.sql.*;
import java.util.Scanner;

/*

  Created by Yujie Zhou, Teammate Peng Ouyang.

  My apologize for using duplicated codes to save time.

  But all the required functions are all included(SELECT\INSERT\UPDATE\DELETE\JOIN).

  Thanks for taking time to check my codes and have a nice day.

 */

public class Quiz {

    //Declaim variables
    static int i=1;
    static String inputAnswer;
    static String correctAnswer;
    static String mode;
    static int count = 0;
    static int score = 0;

    //Main program, loop to choose functions and do the job
    public static void main(String[] args) {

        while(true) {
            promptEnterKey_ChooseMode();
            if(mode.equals("a"))
                AddPerson();
            else if(mode.equals("b"))
                UpdatePerson();
            else if(mode.equals("c"))
                DeletePerson();
            else if (mode.equals("d"))
                JoinPerson();
            else if(mode.equals("s")) {
                CountQuestions();
                System.out.println("There's "+count+" questions in total."+"\r\n");
                while(i<count) {
                    new Quiz().GetQuestion();
                    promptGetAnswer();
                    new Quiz().GetAnswer();
                    if (inputAnswer.equals(correctAnswer)) {
                        score++;
                        System.out.println("Congratulations! Your answer is correct!" + "\r\n");
                    } else System.out.println("Sorry your answer is not correct" + "\r\n");
                    System.out.println("Current Score: "+score+" out of "+count+"\r\n");
                    i++;
                    promptEnterKey_GetQuestion();
                    if(i==count)
                    {
                        System.out.println("All questions answered!"+"\r\n"+"Your final score: "+score+" out of "+count+". Congratulations!"+"\r\n");
                    }
                }
            }
        }
    }

    //Method used to choose functions
    public static void promptEnterKey_ChooseMode(){
        System.out.println("Please input one character to choose the function you want to use:"+"\r\n"+"a. Add one Person"+"\r\n"
                +"b. Update one Person's info"+"\r\n"+"c. Delete one Person's info"+"\r\n"+"d. Show team info"+"\r\n"
                +"s. Start the Quiz");
        Scanner scanner = new Scanner(System.in);
        mode = scanner.nextLine();
    }

    //Method used to indicate user to press Enter to get questions
    public static void promptEnterKey_GetQuestion(){
        System.out.println("Press \"ENTER\" to Get one Question...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    //Method used to indicate user to input answer
    private static void promptGetAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your answer...");
        inputAnswer = scanner.nextLine();
    }

    //Method used to add a person in database using INSERT
    private static void AddPerson(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input Peron's Name...");
        String name = scanner.nextLine();
        System.out.println("Please input Peron's Lastname...");
        String lastname = scanner.nextLine();
        System.out.println("Please input Peron's Email...");
        String email = scanner.nextLine();
        System.out.println("Please input Peron's GSM Number...");
        String gsmnumber = scanner.nextLine();
        System.out.println("Please input Peron's Address...");
        String address = scanner.nextLine();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");
            String query = "INSERT INTO a17_sd210.Person (Name,LastName,Email,GSMnumber,Address)VALUES (?,?,?,?,?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, name);
            preparedStmt.setString (2, lastname);
            preparedStmt.setString (3, email);
            preparedStmt.setString (4, gsmnumber);
            preparedStmt.setString (5, address);
            // execute the preparedstatement
            preparedStmt.execute();
            con.close();
            System.out.println("Successfully added!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to  update a person's infomation in database using UPDATE
    private static void UpdatePerson(){
        int ID = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input Peron's Name you want to update...");
        String name = scanner.nextLine();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("Select * From a17_sd210.Person WHERE Name ='"+name+"'");
            while (rs.next()) {
                ID = rs.getInt("ID");
                System.out.println("Name: "+rs.getString("Name") + "\r\n" + "LastName: " + rs.getString("LastName")+"\r\n"
                        +"Email: "+rs.getString("Email")+"\r\n"+"GSM Number: "+rs.getString("GSMnumber")+"\r\n"+"Address: "+rs.getString("Address")+"\r\nb");
            }
            System.out.println("Please input which Peron's info you want to update(One capital letter)...");
            String update = scanner.nextLine();
            switch (update){
                case "N":
                    System.out.println("Please input new Name...");
                    String Newname = scanner.nextLine();
                    stat.execute("UPDATE a17_sd210.Person SET Name ='"+Newname+"'WHERE ID = '"+ID+"'");
                    break;
                    case "L":
                        System.out.println("Please input new LastName...");
                        String NewLastname = scanner.nextLine();
                        stat.execute("UPDATE a17_sd210.Person SET LastName ='"+NewLastname+"'WHERE ID = '"+ID+"'");
                        break;
                        case "E":
                            System.out.println("Please input new Email...");
                            String Email = scanner.nextLine();
                            stat.execute("UPDATE a17_sd210.Person SET Email ='"+Email+"'WHERE ID = '"+ID+"'");
                            break;
                            case "G":
                                System.out.println("Please input new GSM Number...");
                                String GSM = scanner.nextLine();
                                stat.execute("UPDATE a17_sd210.Person SET GSMnumber ='"+GSM+"'WHERE ID = '"+ID+"'");
                                break;
                                case "A":
                                    System.out.println("Please input new Address...");
                                    String Address = scanner.nextLine();
                                    stat.execute("UPDATE a17_sd210.Person SET Name ='"+Address+"'WHERE ID = '"+ID+"'");
                                    break;

                                    default: System.out.println("Wrong Input!");
            }

            System.out.println("Update successful!"+"\r\n");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to delete one person's information in database using DELETE
    private static void DeletePerson(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input Peron's Name you want to delete...");
        String name = scanner.nextLine();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");
            String query = "DELETE FROM a17_sd210.Person WHERE Name = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,name);
            // execute the preparedstatement
            preparedStmt.execute();
            con.close();
            System.out.println("Delte Successful!");


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to show the team info using JOIN
    private static void JoinPerson(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("Select * From a17_sd210.Person INNER JOIN " +
                    "a17_sd210.Teams ON a17_sd210.Person.ID = a17_sd210.Teams.PersonID");
            while (rs.next()) {
                System.out.println(rs.getString("Name")+" is in team "+rs.getString("TeamName") + ".\r\n");
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to get questions from database using SELECT
    private void GetQuestion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");

            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("Select * From a17_sd210.Question WHERE id ='"+i+"'");
            while (rs.next()) {
                System.out.println(rs.getString("Question") + "\r\n" + "\r\n" + rs.getString("Description")+"\r\n");
                System.out.println("*****************************************");
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to get answers from database using SELECT
    private void GetAnswer() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("Select * From a17_sd210.Answers WHERE id ='"+i+"'");
            while (rs.next()) {
                System.out.println("The answer is: "+rs.getString("Answer") + "\r\n");
                correctAnswer = rs.getString("Answer");
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Method used to get number of questions from database using SELECT
    private static void CountQuestions() {
        count = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
        }

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://studev.groept.be:3306/a17_sd210",
                    "a17_sd210", "a17_sd210");

            Statement stat = con.createStatement();
            ResultSet rs1 = stat.executeQuery("Select * From a17_sd210.Question");
            while (rs1.next()){
                count++;
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}