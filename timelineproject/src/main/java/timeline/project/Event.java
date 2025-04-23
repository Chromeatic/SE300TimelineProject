public class Event {

    private String name;
    private int time;
    private String info;
    private int decade;
    /**
     * The 'time' format is as follows:
     * 
     *          year : first (4) digits 
     * month of year : next  (2) digits
     *  day of month : last  (2) digits
     * 
     * 
     * OTHER NOTES:
     * ----- -----
     * 
     * Consider the example:
        * A the value of month 1 (January) is written as "01" instead of "1"; 
        * otherwise, the value would mess up the digits (e.g. the hundreds place, thousands place, so on).
        * The same principle applies to the value for days for its respective digits.
     * 
     * The underscores are for no other purpose besides readability.
     * e.g. The value 1979_03_26 is equivalent to the value 19790326.
     */
    static final int MIN_TIME = 0001_01_01; 
    static final int MAX_TIME = 9999_12_31; 

    static final int MIN_YEAR = 0001;
    static final int MAX_YEAR = 9999;
    static final int MIN_MONTH = 1;
    static final int MAX_MONTH = 12;
    // static final int MIN_DAY = 1;
    // static final int MAX_DAY = 31;

    
    public Event(String eventName, int eventTime, String eventInfo) 
    {
        name = eventName;
        time = eventTime;
        info = eventInfo;

        setTime();

        setDecade();
    }

    public void setTime()
    {
        // disassemble
        int year = getYear();
        int month = getMonth();
        int day = getDay();

        // set
        year = checkYear(year);
        month = checkMonth(month);
        day = checkDay(day, month);

        System.out.println(year + " " + month + " " + day);

        // reassemble
        time = year*(10000) + month*(100) + day;
    }
    public int checkYear(int year)
    {
        if(year < MIN_YEAR || year > MAX_YEAR)
        {
            year = MIN_YEAR;
        }
        return year;
    }
    public int checkMonth(int month)
    {
        if(month < MIN_MONTH || month > MAX_MONTH)
        {
            month = MIN_MONTH;
        }
        return month;
    }
    public int checkDay(int day, int month)
    {
        int max;
        boolean isOddMonth = (month % 2 == 1);
        boolean isBeforeAugust = (month < 8);

        if(month == 2)
        {
            if(day > 29) day = 29;
            return day;
        }
        
        if(isOddMonth && isBeforeAugust
        || !isOddMonth && !isBeforeAugust)
        {
            max = 31;
        }
        else
        {
            max = 30;
        }

        if(day > max) day = 1;
        return day;
    }


    // public void setName(String name)
    // {

    // }
    public void setDecade()
    {
        int year = getYear();

        int firstDigit = year % 10;
        decade = year - firstDigit;
    }

    public int getTime() { return time; }
    // isolate respective digits to get each value of time
    public int getYear()  { return (time % 1_0000_00_00) / 1_00_00; }
    public int getMonth() { return (time %      1_00_00) /    1_00; }
    public int getDay()   { return (time %         1_00) /       1; }

    
    public int getDecade() { return decade; }

    

    // not to be accessed out of class
    // private int isolateTimeValue(int timeValue, int minDigit, int maxDigit)
    // {
    //     maxDigit %= 1_0000_00_00; // if this parameter exceeds the limit, set it within the limit

    //     //

    //     return timeValue;
    // }

    public String getName() { return name; }
    public String getInfo() { return info; }
    
    

    public static void main(String[] args) {
        
        Event event = new Event("name", 1_01_63, "info");
        event = new Event("name", 1021_02_63, "info");
        event = new Event("name", 1021_03_63, "info");
        event = new Event("name", 1021_04_63, "info");
        event = new Event("name", 1021_05_63, "info");
        event = new Event("name", 1021_06_63, "info");
        event = new Event("name", 1021_07_63, "info");
        event = new Event("name", 1021_08_63, "info");
        event = new Event("name", 1021_09_63, "info");
        event = new Event("name", 1021_10_63, "info");
        event = new Event("name", 1021_11_63, "info");
        event = new Event("name", 1021_12_63, "info");


    }

}