package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.HoldRecord>{

    private Context context;
    private ArrayList<Doctor> recordsList;

    AppDatabase appDatabase;

    public DoctorAdapter(Context context, ArrayList<Doctor> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HoldRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_row,parent,false);
        return new HoldRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoldRecord holder, int position) {
        Doctor doctor = recordsList.get(position);
        String Dname = doctor.getName();
        String Dspecialization = doctor.getSpecialization();
        String appDays = doctor.getDays();
        String Dtime = doctor.getAppTime();

        Bitmap profileImage = doctor.getImg();

        holder.name.setText(Dname);
        holder.specialization.setText(Dspecialization);
        holder.app_days.setText(appDays);
        holder.app_time.setText(Dtime);
        holder.profileTv.setImageBitmap(profileImage);

        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //later
                SharedPreferences sharedPreferences = context.getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

// Convert the day to a string representation
                String today = "";
                switch (dayOfWeek) {
                    case Calendar.SUNDAY:
                        today = "Sunday";
                        break;
                    case Calendar.MONDAY:
                        today = "Monday";
                        break;
                    case Calendar.TUESDAY:
                        today = "Tuesday";
                        break;
                    case Calendar.WEDNESDAY:
                        today = "Wednesday";
                        break;
                    case Calendar.THURSDAY:
                        today = "Thursday";
                        break;
                    case Calendar.FRIDAY:
                        today = "Friday";
                        break;
                    case Calendar.SATURDAY:
                        today = "Saturday";
                        break;
                }


                appDatabase = new AppDatabase(context);
                int result = appDatabase.register_app(username,Dname,today,Dtime);

                if(result==1){
                    Toast.makeText(context, "Appointment Placed Successfully!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }



    @Override
    public int getItemCount() {
        return recordsList.size(); //no of records
    }

    class HoldRecord extends RecyclerView.ViewHolder{

        ImageView profileTv;
        TextView name,specialization,app_days,app_time;
        Button appointment;

        public HoldRecord(@NonNull View itemView) {
            super(itemView);

            profileTv = itemView.findViewById(R.id.profileTv);
            name = itemView.findViewById(R.id.name);
            specialization = itemView.findViewById(R.id.specialization);
            app_days = itemView.findViewById(R.id.app_days);
            app_time = itemView.findViewById(R.id.app_time);
            appointment = itemView.findViewById(R.id.appointment);

        }
    }
}
