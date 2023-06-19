package es.studium.singnuploginrealtime.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();
    public static ArrayList<Event> eventsForDate(String date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }
        return events;
    }
    private String name;
    private String date;
    private String username;
    public Event(){
    }
    public Event(String name, String date)
    {
        this.name = name;
        this.date = date;
    }
    public Event(String name, String date, String username)
    {
        this.name = name;
        this.date = date;
        this.username = username;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

}
