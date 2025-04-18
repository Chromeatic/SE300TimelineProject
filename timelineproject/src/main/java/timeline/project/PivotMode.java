import java.util.ArrayList;

public class PivotMode extends Timeline{

    private Event pivot;
    private int pivotIndex;
    private ArrayList<Event> eventBank;
    private ArrayList<Event> rightList;
    private ArrayList<Event> leftList;


    public PivotMode()
    {
        super();
    }


    public Event getPivotEvent() { return pivot; }
    public ArrayList<Event> getEventBank() { return eventBank; }
    public ArrayList<Event> getRightList() { return rightList; }
    public ArrayList<Event> getLeftList() { return leftList; }


    public void setPivotAndChoices()
    {
        ArrayList<Event> eventsList = getSortedEventList();
        setPivotIndex(eventsList);
        setEventChoices(eventsList, pivotIndex);
    }

    public void setPivotIndex(ArrayList<Event> eventsList)
    {
        int size = eventsList.size();
        int randomIndex = getRandomListIndex(size);
        randomIndex %= size-1;
        if(randomIndex == 0) randomIndex = 1;


        pivot = eventsList.get(randomIndex);

        pivotIndex = randomIndex;
    }

    public void setEventChoices(ArrayList<Event> eventsList, int pivotIndex)
    {
        eventBank = eventsList;
        eventBank.remove(pivotIndex);
    }

    public void moveToRight(Event thisEvent)
    {
        rightList.add(thisEvent);
    }
    public void moveToLeft(Event thisEvent)
    {
        leftList.add(thisEvent);
    }


    public static void main(String[] args) {

        PivotMode pivot = new PivotMode();
        
        Event event1 = new Event("Event A", 1979_03_26, "(info)");
        Event event2 = new Event("Event B", 1980_12_20, "(info)");
        Event event3 = new Event("Event C", 1999_09_19, "(info)");
        Event event4 = new Event("Event D", 2004_01_02, "(info)");
        Event event5 = new Event("Event E", 2005_08_09, "(info)");
        Event event6 = new Event("Event F", 2019_04_18, "(info)");
        
        ArrayList<Event> sortedEvents = pivot.getSortedEventList();
        sortedEvents.add(event1);
        sortedEvents.add(event2);
        sortedEvents.add(event3);
        sortedEvents.add(event4);
        sortedEvents.add(event5);
        sortedEvents.add(event6);
        // ArrayList<Event> shuffledEvents = pivot.shuffleList(sortedEvents);


        System.out.println();
        // int i = 0;
        // while(i < sortedEvents.size())
        // {
        //     System.out.print(sortedEvents.get(i).getName() + " - " + shuffledEvents.get(i).getName());
        //     System.out.println();
        //     i++;
        // }
        pivot.setPivotAndChoices();
        System.out.println("pivot: " + pivot.getPivotEvent().getName() + "\nrest of list:");
        int i = 0;
        while(i < pivot.getEventBank().size())
        {
            System.out.println(pivot.getEventBank().get(i).getName());
            i++;
        }
        
        


        


    }
}
