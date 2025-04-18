import java.util.ArrayList;
import java.util.Scanner;

public class TimelineSortMode extends Timeline{

    private Event[] timelineArray;
    private int[] decadesArray;

    
    public TimelineSortMode()
    {
        super();
        timelineArray = new Event[getShuffledEventList().size()];
    }

    public void setDecadesArray(ArrayList<Event> eventList)
    {
        //
    }

    public void moveEventFromBankToArray(ArrayList<Event> eventBank, Event[] eventArray, int srcIndex, int dstIndex)
    {
        Event eventToMove = eventBank.get(srcIndex);
        eventArray[dstIndex] = eventToMove;

        eventBank.remove(eventToMove);
    }
    public void moveEventFromArrayToBank(ArrayList<Event> eventBank, Event[] eventArray, int srcIndex, int dstIndex)
    {
        Event eventToMove = eventArray[srcIndex];
        eventBank.add(eventToMove);

        eventArray[dstIndex] = null;
    }
    public void moveEventInsideArray(ArrayList<Event> eventBank, Event[] eventArray, int srcIndex, int dstIndex)
    {
        Event eventToMove = eventArray[srcIndex];

        if(eventArray[dstIndex] != null)
        {
            eventBank.add(eventArray[dstIndex]);
        }
        eventArray[dstIndex] = eventToMove;

        eventArray[srcIndex] = null;
    }

    // public boolean[] getHints(Event[] timelineArray)
    // {
    //     boolean[] hints = new boolean[timelineArray.length];
        

    //     return hints;
    // }

    public boolean[] getDecadeHints(Event[] timelineArray)
    {
        boolean[] hints = new boolean[timelineArray.length];



        return hints;
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
