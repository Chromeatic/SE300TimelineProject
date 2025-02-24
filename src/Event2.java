public class Event2 {

    private String name;
    private int time;
    private String info;
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
    static final int MIN_TIME = 0000_01_01; 
    static final int MAX_TIME = 2024_12_31; 
    
    public Event2(String eventName, int eventTime, String eventInfo) 
    {
        name = eventName;
        time = eventTime;
        info = eventInfo;

    }

    public void setTime()
    {
        if(time < MIN_TIME || time > MAX_TIME)
        {
            time = MAX_TIME;
        }

        // disassemble
        int year = getYear();
        int month = getMonth();
        int day = getDay();

        // set
        setYear(year);
        setMonth(month);
        setDay(day);

        // reassemble
        time = year*(10000) + month*(100) + day;
    }
    public void setYear(int year)
    {
        //
    }
    public void setMonth(int month)
    {
        //
    }
    public void setDay(int day)
    {
        //
    }

    public String getName() { return name; }

    public int getTime() { return time; }

    // isolate respective digits to get each value of time
    public int getYear()  { return (time % 1_0000_00_00) / 1_00_00; }
    public int getMonth() { return (time %      1_00_00) /    1_00; }
    public int getDay()   { return (time %         1_00) /       1; }

    // not to be accessed out of class
    // private int isolateTimeValue(int timeValue, int minDigit, int maxDigit)
    // {
    //     maxDigit %= 1_0000_00_00; // if this parameter exceeds the limit, set it within the limit

    //     //

    //     return timeValue;
    // }

    public String getInfo() { return info; }

}
