import java.util.*;

public class Timeline 
{

    private ArrayList<Event> events = new ArrayList<Event>();

    public void createTimeline()
    {
        boolean check = true;
        String valid = "X";

        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Enter the name of the event: ");
            String name = scanner.nextLine();
            System.out.println("Enter the date of the event: ");
            String time = scanner.nextLine();
            System.out.println("Enter the description of the event: ");
            String info = scanner.nextLine();
            Event newEvent = new Event(name, Integer.parseInt(time), info);
            events.add(newEvent);
            do 
            {
                System.out.println("Do you wish to add another event y/n?");
                valid = scanner.nextLine();
                if(!(valid.equalsIgnoreCase("y") || valid.equalsIgnoreCase("n")))
                {
                    System.out.println("Invalid input, please enter Y or N");
                }
            } while(!(valid.equalsIgnoreCase("y") || valid.equalsIgnoreCase("n")));
            if(valid.equalsIgnoreCase("y"))
            {
                check = true;
            } else 
            {
                check = false;
            }

        } while (check);

    }

    public void displayTimeline()
    {
        for (int i = 0; i < events.size(); i++) 
        {
            System.out.println(events.get(i).getName() + " " + events.get(i).getTime() + " " + events.get(i).getInfo());
        }
    }
}