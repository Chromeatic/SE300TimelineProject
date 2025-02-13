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
            Event newEvent = new Event(name, time, info);
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

    public void swap(int index1, int index2)
    {
        Event temp = events.get(index1);
        events.set(index1, events.get(index2));
        events.set(index2, temp);
    }

    public void sortTimeline(Scanner scanner)
    {
        int index1 = 0;
        int index2 = -1;
        if(events.size() == 1)
        {
            System.out.println("Timeline only contains one event. Cannot swap");
        }
        while (!(index1 >= 0 && index1 < index2 && index2 < events.size()) && events.size() != 1)
        {
            System.out.println("Enter first index to swap: Enter a number between 0 and " + (events.size()-1));
            index1 = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter second index to swap: Enter a number between " + (index1+1) +" and " + (events.size()-1));
            index2 = Integer.parseInt(scanner.nextLine());
            if(!(index1 >= 0 && index1 < index2 && index2 < events.size()))
            {
                System.out.println("Invalid input. Try again");
            }
        } 
        swap(index1, index2);
    }
}