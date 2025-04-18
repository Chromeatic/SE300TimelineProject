import java.util.*;
import java.math.*;

public class Timeline 
{

    private ArrayList<Event> sortedEvents;
    private ArrayList<Event> shuffledEvents;
    private ArrayList<Event> sortedEventsInDecades;

    public Timeline()
    {
        sortedEvents = new ArrayList<Event>();
        shuffledEvents = shuffleList(sortedEvents);
    }

    public ArrayList<Event> getSortedEventList() { return sortedEvents; }
    public ArrayList<Event> getShuffledEventList() { return shuffledEvents; }

    public ArrayList<Event> getListInDecades() { return sortedEventsInDecades; }



    // public ArrayList<Event2> completelyShuffleList(ArrayList<Event2> sortedEvents)
    // {
    //     ArrayList<Event2> shuffledEvents = shuffleList(sortedEvents);
    //     boolean[] hints = new boolean[shuffledEvents.size()-1];
    //     boolean[] decadeHints = new boolean[shuffledEvents.size()-1];
        
    //     int i = 0;
    //     do
    //     {
    //         System.out.println();
    //         shuffledEvents = shuffleList(sortedEvents);

    //         hints = getHints(shuffledEvents, sortedEvents); 
    //         decadeHints = getDecadeHints(shuffledEvents, sortedEvents); 
    //         i++; 
    //         System.out.println("; # of iterations to completely shuffle list: " + i);
            

    //     }while( (isAllInorrect(hints) == false) && (isAllInorrect(decadeHints) == false) );

    //     return shuffledEvents;
    // }

    // boolean isAllInorrect(boolean[] hints)
    // {
    //     int i = 0;
    //     while(i < hints.length)
    //     {
    //         if(hints[i] == true)
    //         {
    //             System.out.println("correct at index: " + i);
    //             return false;
    //         }
    //         i++;
    //     }
    //     return true;
        
    // }

    public ArrayList<Event> shuffleList(ArrayList<Event> sortedEvents)
    {
        shuffledEvents = new ArrayList<Event>();
        int randomIndex = getRandomListIndex(sortedEvents.size());
        ArrayList<Integer> markedIndices = new ArrayList<>();

        while(markedIndices.size() < sortedEvents.size())
        {
            Event eventToAdd = sortedEvents.get(randomIndex);
            shuffledEvents.add(eventToAdd);
            System.out.println(randomIndex + " ("+ sortedEvents.get(randomIndex).getDecade() +")");
            markedIndices.add(randomIndex);

            boolean uniqueIndexFound;
            do
            {
                uniqueIndexFound = true;
                randomIndex = getRandomListIndex(sortedEvents.size());

                int i = 0;
                
                while(i < markedIndices.size())
                {
                    // return when list is finished; otherwise loop will be stuck trying to find an index that does not exist
                    if(markedIndices.size() == sortedEvents.size()) return shuffledEvents;

                    // go through marked indices and compare
                    if(randomIndex == markedIndices.get(i))
                    {
                        uniqueIndexFound = false; // if at least one match, index is not unique
                    }
                    i++;

                } 

            } while(!uniqueIndexFound);
        
        }

        // if shuffle functions as expected, it should not reach this statement;
        // a return of 'null' shall signify otherwise
        return null; 
    }

    public int getRandomListIndex(int listSize)
    {
        return (int) (Math.random() * (listSize));
    }


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
            int time = Integer.parseInt(scanner.nextLine());
            // int time = 1979_03_26;            // <-- placeholder
            System.out.println("Enter the description of the event: ");
            String info = scanner.nextLine();
            Event newEvent = new Event(name, time, info);
            sortedEvents.add(newEvent);
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

    public void displayTimeline(ArrayList<Event> events)
    {
        for (int i = 0; i < events.size(); i++) 
        {
            System.out.println(i + ". " + events.get(i).getName() + " (" + events.get(i).getTime() + ") - " + events.get(i).getInfo());
        }
    }

    public void swap(ArrayList<Event> events, int index1, int index2)
    {
        Event temp = events.get(index1);
        events.set(index1, events.get(index2));
        events.set(index2, temp);
    }

    // public void sortTimeline(ArrayList<Event> events, Scanner scanner)
    // {
    //     int index1 = 0;
    //     int index2 = -1;
    //     if(events.size() == 1)
    //     {
    //         System.out.println("Timeline only contains one event. Cannot swap");
    //     }
    //     while (!(index1 >= 0 && index1 < index2 && index2 < events.size()) && events.size() != 1)
    //     {
    //         System.out.println("Enter first index to swap: Enter a number between 0 and " + (events.size()-1));
    //         index1 = Integer.parseInt(scanner.nextLine());
    //         System.out.println("Enter second index to swap: Enter a number between " + (index1+1) +" and " + (events.size()-1));
    //         index2 = Integer.parseInt(scanner.nextLine());
    //         if(!(index1 >= 0 && index1 < index2 && index2 < events.size()))
    //         {
    //             System.out.println("Invalid input. Try again");
    //         }
    //     } 
    //     swap(events, index1, index2);
    // }
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

    // NEW
    public Event getEventByName(ArrayList<Event> events, String name)
    {
        int i = 0;
        while(i < events.size())
        {
            if(events.get(i).getName().equals(name))
            {
                return events.get(i);
            }
            i++;
        }
        return null;
    }
    // NEW
    public int getIndexByName(ArrayList<Event> events, String name)
    {
        int i = 0;
        while(i < events.size())
        {
            if(events.get(i).getName().equals(name))
            {
                return i;
            }
            i++;
        }
        return -1;
    }
    


    public boolean isBefore(Event currentEvent, Event nextEvent)
    {
        return (currentEvent.getTime() < nextEvent.getTime());
    }

    // Uses the values for years to give a 'hint'--not the timeline indices
    public boolean[] getSequenceFlow(ArrayList<Event> events) 
    {
        boolean[] sequenceFlow = new boolean[events.size()-1];
        
        for(int i = 0; i < events.size()-1; i++)
        {
            sequenceFlow[i] = isBefore(events.get(i), events.get(i+1));
        }

        return sequenceFlow;
    }

    public boolean[] getHints(ArrayList<Event> shuffledEvents, ArrayList<Event> sortedEvents)
    {
        boolean[] hints = new boolean[shuffledEvents.size() - 1];

        int i = 0;
        while(i < hints.length)
        {
            // System.out.print(shuffledEvents.get(i).getName());
            // System.out.print(" - ");
            // System.out.print(shuffledEvents.get(i+1).getName());
            // System.out.print(" : ");
            
            String thisEventName = shuffledEvents.get(i).getName();
            String nextEventNameShuffled = shuffledEvents.get(i+1).getName();
            int sortedIndex = getIndexByName(sortedEvents, thisEventName);
            int sortedIndexNext = sortedIndex + 1;
            if(sortedIndexNext >= sortedEvents.size())
            {
                hints[i] = false;
                System.out.println(hints[i]);
                i++;
                continue;
            }
            String nextEventNameInOrder = sortedEvents.get(sortedIndexNext).getName();

            boolean isCorrect;
            isCorrect = nextEventNameShuffled.equals(nextEventNameInOrder);
            hints[i] = isCorrect;

            System.out.print(isCorrect);
            

            System.out.println();
            i++;
        }

        return hints;
    }

    public boolean[] getDecadeHints(ArrayList<Event> shuffledEvents, ArrayList<Event> sortedEvents)
    {
        boolean[] decadeHints = new boolean[shuffledEvents.size() - 1];

        int i = 0;
        while(i < decadeHints.length)
        {
            int thisDecade = shuffledEvents.get(i).getDecade();
            int nextDecade = thisDecade + 10;
            
            if( (thisDecade + 10) == nextDecade)
            {
                decadeHints[i] = true;
            }
            else
            {
                decadeHints[i] = false;
            }

            i++;
        }
        return decadeHints;
    }
    

    // TEST MAIN
    public static void main(String[] args)
    {
        Event event1 = new Event("Event A", 1979_03_26, "(info)");
        Event event2 = new Event("Event B", 1980_12_20, "(info)");
        Event event3 = new Event("Event C", 1999_09_19, "(info)");
        Event event4 = new Event("Event D", 2004_01_02, "(info)");
        Event event5 = new Event("Event E", 2005_08_09, "(info)");
        Event event6 = new Event("Event F", 2019_04_18, "(info)");
        

        Timeline timeline = new Timeline();
        ArrayList<Event> sortedEvents = timeline.getSortedEventList();
        sortedEvents.add(event1);
        sortedEvents.add(event2);
        sortedEvents.add(event3);
        sortedEvents.add(event4);
        sortedEvents.add(event5);
        sortedEvents.add(event6);
        // Timeline3 shuffledTimeline = new Timeline3();
        // ArrayList<Event2> shuffledEvents = shuffledTimeline.getSortedEventList();
        // shuffledEvents.add(event2);
        // shuffledEvents.add(event1);
        // shuffledEvents.add(event4);
        // shuffledEvents.add(event5);
        // shuffledEvents.add(event3);
        // shuffledEvents.add(event6);
        
        // timeline.getHints(shuffledEvents, sortedEvents);

        // System.out.println(timeline.getRandomListIndex(sortedEvents.size()));
        ArrayList<Event> shuffledEvents = timeline.shuffleList(sortedEvents);
        boolean[] hints = timeline.getHints(shuffledEvents, sortedEvents);
        boolean[] decadeHints = timeline.getDecadeHints(shuffledEvents, sortedEvents);

        // Scanner scan = new Scanner(System.in);
        // timeline.sortTimeline(shuffledEvents, scan);
        
        // System.out.println();

        // int i = 0;
        // while(i < sortedEvents.size())
        // {
        //     System.out.println(sortedEvents.get(i).getDecade());
        //     i++;
        // }
        

        
        

    }
}