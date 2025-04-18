import java.util.ArrayList;
import java.util.Scanner;

public class TimelineSortMode extends Timeline{
    
    public TimelineSortMode()
    {
        super();
        
    }

    public void sortTimeline(ArrayList<Event> events, Scanner scanner)
    {
        int index1 = 0;
        int index2 = -1;
        if(events.size() <= 1)
        {
            System.out.println("INVALID EVENTS LIST SIZE; events list must have more than one event...");
            return;
        }
        while (!(index1 >= 0 && index1 < index2 && index2 < events.size()) && events.size() != 1)
        {
            String prompt = "Enter first index to swap: Enter a number between 0 and " + (events.size()-1);

            System.out.println(prompt);
            index1 = Integer.parseInt(scanner.nextLine());
            System.out.println(prompt);
            index2 = Integer.parseInt(scanner.nextLine());
            if(!(index1 >= 0 && index1 < index2 && index2 < events.size()))
            {
                System.out.println("Invalid input. Try again");
            }
        } 
        swap(events, index1, index2);
    }


    public static void main(String[] args) {

        TimelineSortMode sort = new TimelineSortMode();

        Event event1 = new Event("Event A", 1979_03_26, "(info)");
        Event event2 = new Event("Event B", 1980_12_20, "(info)");
        Event event3 = new Event("Event C", 1999_09_19, "(info)");
        Event event4 = new Event("Event D", 2004_01_02, "(info)");
        Event event5 = new Event("Event E", 2005_08_09, "(info)");
        Event event6 = new Event("Event F", 2019_04_18, "(info)");
        

        ArrayList<Event> sortedEvents = sort.getSortedEventList();
        sortedEvents.add(event1);
        sortedEvents.add(event2);
        sortedEvents.add(event3);
        sortedEvents.add(event4);
        sortedEvents.add(event5);
        sortedEvents.add(event6);
        
        ArrayList<Event> shuffledEvents = sort.shuffleList(sortedEvents);
        boolean[] hints = sort.getHints(shuffledEvents, sortedEvents);
        boolean[] decadeHints = sort.getDecadeHints(shuffledEvents, sortedEvents);

        Scanner scan = new Scanner(System.in);
        sort.sortTimeline(shuffledEvents, scan);
        
    }
}
