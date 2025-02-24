import java.util.*;

public class Timeline2 
{

    private ArrayList<Event2> events = new ArrayList<Event2>();

    //NEW; CONSTRUCTOR
    public Timeline2()
    {
        events = new ArrayList<Event2>();
    }

    //NEW; 'get' method
    public ArrayList<Event2> getEventList() { return events; }

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
            // String time = scanner.nextLine(); // TODO implement Event constructor for 'time' variable of integer-type
            int time = Integer.parseInt(scanner.nextLine());
            // int time = 1979_03_26;            // <-- placeholder
            System.out.println("Enter the description of the event: ");
            String info = scanner.nextLine();
            Event2 newEvent = new Event2(name, time, info);
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

        scanner.close();
    }

    public void displayTimeline()
    {
        for (int i = 0; i < events.size(); i++) 
        {
            System.out.println(i + ". " + events.get(i).getName() + " (" + events.get(i).getTime() + ") - " + events.get(i).getInfo());
        }
    }

    public void swap(int index1, int index2)
    {
        Event2 temp = events.get(index1);
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

    


    //NEW
    public boolean isBefore(Event2 currentEvent, Event2 nextEvent)
    {
        if(currentEvent.getTime() < nextEvent.getTime()) return true;

        return false;
    }

    //NEW
    // Uses the values for years to give a 'hint'--not the timeline indices
    public boolean[] getSequenceFlow(ArrayList<Event2> events) // TODO: IMPLEMENT IN 'HINT' FEATURE FOR 'PIVOT' MODE
    {
        boolean[] sequenceFlow = new boolean[events.size()-1];
        
        for(int i = 0; i < events.size()-1; i++)
        {
            sequenceFlow[i] = isBefore(events.get(i), events.get(i+1));
        }

        return sequenceFlow;
    }

    // TEST MAIN
    public static void main(String[] args)
    {
        Event2 event1 = new Event2("Event A", 1979_03_26, "(info)");
        Event2 event2 = new Event2("Event B", 2019_04_18, "(info)");
        Event2 event3 = new Event2("Event C", 1980_12_20, "(info)");
        Event2 event4 = new Event2("Event D", 1999_09_19, "(info)");
        Event2 event5 = new Event2("Event E", 2005_08_09, "(info)");
        Event2 event6 = new Event2("Event F", 2004_01_02, "(info)");

        Timeline2 timeline = new Timeline2();
        // timeline.events.add(event1);
        // timeline.events.add(event2);
        // timeline.events.add(event3);
        // timeline.events.add(event4);
        // timeline.events.add(event5);
        // timeline.events.add(event6);
        ArrayList<Event2> events = timeline.getEventList();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);

        // boolean[] sequenceFlow = timeline.getSequenceFlow(timeline.events);
        boolean[] sequenceFlow = timeline.getSequenceFlow(events);

        for(int i = 0; i < sequenceFlow.length; i++)
        {
            if(sequenceFlow[i])
            {
                System.out.println(1);
            }
            else
            {
                System.out.println(0);
            }
        }
        System.out.println();
        
        for(int i = 0; i < sequenceFlow.length; i++)
        {
            if(sequenceFlow[i])
            {
                System.out.printf("%s [%d]   is   before %s [%d]\n", 
                events.get(i).getName(), events.get(i).getYear(), 
                events.get(i+1).getName(), events.get(i+1).getYear());
            }
            else
            {
                System.out.printf("%s [%d] is NOT before %s [%d]\n",
                events.get(i).getName(), events.get(i).getYear(), 
                events.get(i+1).getName(), events.get(i+1).getYear());
            }
        }
        System.out.println();


        System.out.println("HINT:");
        for(int i = 0; i < sequenceFlow.length; i++)
        {
            System.out.print("= [" + events.get(i).getName() + "] =");
            if(sequenceFlow[i])
            {
                System.out.print("=");
            }
            else
            {
                System.out.print("/");
            }
        }
        System.out.println("= [" + events.get(sequenceFlow.length).getName() + "] =");
        System.out.println();

    }
}
