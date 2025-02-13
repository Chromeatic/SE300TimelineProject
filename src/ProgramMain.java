import java.util.*;

public class ProgramMain {

    private TimelineCollection timelines = new TimelineCollection();
    public static void main(String[] args) throws Exception {

        ProgramMain program = new ProgramMain();

        program.runMain();
        
    }

    public void runMain()
    {
        menu();
    }

    public void menu()
    {
        String input;
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("1. Create Timeline\n2. Manual Sort Timeline\n3. Display Timeline\n4. End Program");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    Timeline newTimeline = new Timeline();
                    newTimeline.createTimeline();
                    timelines.addTimeline(newTimeline);
                    System.out.println("Added timline to index: " + (timelines.getSize() - 1) + "\n");
                    break;
                case "2":
                    System.out.println("What timeline to sort?");
                    input = Integer.toString(Integer.parseInt(scanner.nextLine()));
                    System.out.println("");
                    timelines.getTimeline(Integer.parseInt(input)).sortTimeline(scanner);
                    System.out.println("");
                    break;
                case "3":
                    do
                    {

                        System.out.println("What timeline to display?");
                        input = Integer.toString(Integer.parseInt(scanner.nextLine()));
                        System.out.println("");
                        if(!(Integer.parseInt(input) >= 0 && Integer.parseInt(input) < timelines.getSize()))
                        {
                            System.out.println("Invalid input. Try again");
                        }

                    } while (!(Integer.parseInt(input) >= 0 && Integer.parseInt(input) < timelines.getSize()));

                    timelines.getTimeline(Integer.parseInt(input)).displayTimeline();
                    System.out.println("");
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                System.out.println("Invalid input. Try again");
                break;
                }
        } while (input != "4");

    }
}


