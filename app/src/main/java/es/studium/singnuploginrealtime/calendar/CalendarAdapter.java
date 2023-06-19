package es.studium.singnuploginrealtime.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

import es.studium.singnuploginrealtime.R;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private final String username;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener, String username)
    {
        this.days = days;
        this.onItemListener = onItemListener;
        this.username = username;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            System.out.println(username + "------");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WeekInfo");
            Query checkUserDatabase = reference.orderByChild("date").equalTo(String.valueOf(date));
            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && !date.equals(CalendarUtils.selectedDate)){
                    } else {
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }
    }
    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
