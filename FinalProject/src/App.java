import java.util.Scanner; 

public class App {

    private static boolean runUseCase(GirlsWhoCode obj, int useCase) {
        switch (useCase) {
            case 1:
                obj.facilitatorAssistAttendance();
                return true;
            case 2:
                obj.dropStudent();
                return true;
            case 3:
                obj.semesterCurriculum();
                return true;
            case 4:
                obj.getSessionLeadersAndTopics();
                return true;
            case 5:
                obj.updateSessionTopic();
                return true;
            case 6:
                obj.insertNewStudent();
                return true;
            case 7:
                obj.insertExecMember();
                return true; 
            default:
                System.out.println("Use Case " + useCase + " not found"); 
                return false;
        }
    }

    public static void main(String[] args) throws Exception {
        //introduction
        System.out.println("\nWelcome to the Girls Who Code Database!\n");
        String possibleUseCasesStr = "Possible use cases to run: " + 
            "\n\t1. Find the number of sessions each facilitator has assisted for a specific course during a semester." + 
            "\n\t2. Remove a student from a course" + 
            "\n\t3. Get the topics of each session in a course" + 
            "\n\t4. Get the facilitators teaching a session" + 
            "\n\t5. Change the topic/title of a sesion in a specific course" + 
            "\n\t6. Enroll a new student in a course." + 
            "\n\t7. Insert a new exec member. If an exec member of that position already exists in the same academic year, then updates the values to 'Co' + [PositionName]" + 
            "\n\n\tAt any time, input 'help' to see these use cases again or 'quit' to stop running the program.\n";
        System.out.println(possibleUseCasesStr);

        GirlsWhoCode gwc = new GirlsWhoCode(); 
        Scanner sc = new Scanner(System.in); 
        boolean continueAsk = true; 
        
        //continuously ask for user input until user quits
        while(continueAsk) {
            System.out.println("\nWhich use case would you like to run? (input the number): ");
            String input = sc.nextLine();
            if(input.equals("quit")) {
                continueAsk = false;
                break; 
            } else if(input.equals("help")) {
                System.out.println(possibleUseCasesStr); 
            } else {
                runUseCase(gwc, Integer.parseInt(input)); 
            }

            System.out.println("\nWould you like to continue? (Y/N): ");
            continueAsk = sc.nextLine().equals("Y");  
        }
        
        System.out.println("\nThank you for using the Girls Who Code database!"); 
    }
}
