import java.util.ArrayList;

public class BeforeAndAfterMode extends Timeline{

    private ArrayList<Event> eventsInWaiting;
    private ArrayList<Event> eventsDiscarded;
    private Event contenderEvent;
    private Event oppositionEvent;


    public BeforeAndAfterMode()
    {
        super();
        eventsInWaiting = getShuffledEventList();
        eventsDiscarded = new ArrayList<Event>();
    }

    public ArrayList<Event> getDiscardedEvents() { return eventsDiscarded; }
    public ArrayList<Event> getWaitingEvents() { return eventsInWaiting; }
    public Event getContenderEvent() { return contenderEvent; }
    public Event getOppositionEvent() { return oppositionEvent; }



    public boolean shiftContenderAndOpposition(ArrayList<Event> eventsInWaiting)
    {
        if(eventsInWaiting.size() == 0)
        {
            System.out.println("All shifts expended; Waitlist has reached end...");
            return false;
        }
        if(contenderEvent != null)
        {
            eventsDiscarded.add(contenderEvent);
        }
        if(oppositionEvent == null) // first shift hasn't been made 
        {
            reassignOpposition(eventsInWaiting);
        }
        reassignContender(eventsInWaiting);
        reassignOpposition(eventsInWaiting);

        if(checkSameDate(contenderEvent, oppositionEvent))
        {
            System.out.println("Contender and Opposition contain exact same date");
            return false;
        }

        // System.out.println("this contender" + getContenderEvent().getName());
        // System.out.println("this opposition" + getOppositionEvent().getName());
        // System.out.println("events in waiting:");
        // debugging_printEventList(eventsInWaiting);
        // System.out.println("discarded events:");
        // debugging_printEventList(eventsDiscarded);

        return true;
    }
    
    public void reassignOpposition(ArrayList<Event> eventsInWaiting)
    {
        if(eventsInWaiting.size() == 0)
        {
            System.out.println("Cannot reassign; events waiting list is empty...");
            return;
        }
        int randomIndex = getRandomListIndex(eventsInWaiting.size());
        oppositionEvent = eventsInWaiting.get(randomIndex);
        
        eventsInWaiting.remove(randomIndex);
    }
    public void reassignContender(ArrayList<Event> eventsInWaiting)
    {
        contenderEvent = oppositionEvent;
    }

    public void debugging_printEventList(ArrayList<Event> list)
    {
        if(list.size() == 0)
        {
            System.out.println("(list is empty)");
        }
        int i = 0;
        while(i < list.size())
        {
            System.out.println(list.get(i).getName());
            i++;
        }
    }

    public boolean isBefore(Event contender, Event opposition)
    {
        return (contender.getTime() < opposition.getTime());
    }
    public boolean isAfter(Event contender, Event opposition)
    {
        return (contender.getTime() > opposition.getTime());
    }
    public boolean checkSameDate(Event contender, Event opposition)
    {
        return (contender.getTime() == contender.getTime());
    }

    public boolean getAndJudgeGuess(boolean guess, Event contender, Event opposition)
    {
        /* guess:
         * false = is before (is not after)
         * true = is after
         */
        
        return(isAfter(contender, opposition) == guess);
    }

    public static void main(String[] args) {
        BeforeAndAfterMode baaMode = new BeforeAndAfterMode();

        Event event1 = new Event("Event A", 1979_03_26, "(info)");
        Event event2 = new Event("Event B", 1980_12_20, "(info)");
        Event event3 = new Event("Event C", 1999_09_19, "(info)");
        Event event4 = new Event("Event D", 2004_01_02, "(info)");
        Event event5 = new Event("Event E", 2005_08_09, "(info)");
        Event event6 = new Event("Event F", 2019_04_18, "(info)");

        
        
        ArrayList<Event> sortedEvents = baaMode.getSortedEventList();
        sortedEvents.add(event1);
        sortedEvents.add(event2);
        sortedEvents.add(event3);
        sortedEvents.add(event4);
        sortedEvents.add(event5);
        sortedEvents.add(event6);
        ArrayList<Event> shuffledEvents = baaMode.shuffleList(sortedEvents);


        int beforeCount = 0;
        int afterCount = 0;

        int i = 0;
        while(i < 5)
        {
            baaMode.shiftContenderAndOpposition(shuffledEvents);
            if(baaMode.getAndJudgeGuess(true, baaMode.getContenderEvent(), baaMode.getOppositionEvent()))
            {
                afterCount++;
            }
            else
            {
                beforeCount++;
            }
            i++;
        }
        System.out.println(beforeCount + " ; " + afterCount);
        
        
        
        
        
    }
    
}
