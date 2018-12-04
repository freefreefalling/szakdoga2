package hu.oe.szakdolgozat.n46god.ezerszigethorgszt;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok.HttpKeres;
import hu.oe.szakdolgozat.n46god.ezerszigethorgszt.segedOsztalyok.IKeszVan;

public class IdopontFoglalas extends AppCompatActivity {

    private JSONObject napok = null;
    Date d = new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idopont_foglalas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final MaterialCalendarView simpleCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView); // get the reference of CalendarView

        simpleCalendarView.setCurrentDate(d); // get selected date in milliseconds

        simpleCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(new Date(System.currentTimeMillis()))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        honapBeolvas((d.getYear()+1900), d.getMonth()+1, simpleCalendarView);
        simpleCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(getApplicationContext(), IdopontFoglalasNap.class);
                intent.putExtra("EV", date.getYear() + "");
                intent.putExtra("HONAP", (date.getMonth()+1) + "");
                intent.putExtra("NAP", date.getDay() + "");
                startActivity(intent);
            }
        });
        simpleCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                honapBeolvas(date.getYear(), date.getMonth() + 1, simpleCalendarView);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void Calendar(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, "Learn Android");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home suit home");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Download Examples");

// Setting dates
        Date date;
        date = new Date(2018-1900,11,31);
        //This method returns the time in millis
        date = new Date(2018-1900,11,32);
        //This method returns the time in millis

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                DatumMasodpercben(2018, 11, 29));
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                DatumMasodpercben(2018, 12, 3));

// make it a full day event
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

// make it a recurring Event
        //intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

// Making it private and shown as busy
        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        intent.setData(CalendarContract.Events.CONTENT_URI);
        startActivity(intent);
    }

    long DatumMasodpercben(int ev, int honap, int nap, int ora, int perc){
        Date date;
        date = new Date(ev-1900,honap-1,nap);
        //This method returns the time in millis
        return date.getTime()+(ora*3600);
    }

    long DatumMasodpercben(int ev, int honap, int nap){
        return DatumMasodpercben(ev, honap, nap, 6, 0);
    }

    public void honapBeolvas(int ev, final int honap, final MaterialCalendarView calendar) {

        HttpKeres http = new HttpKeres();
        http.interfaceHozzaAd(new IKeszVan() {
            @Override
            public void keszVan(String string) {
                Log.i("asd ", string);
                try {

                    JSONObject jObject = new JSONObject(string);
                    napok = jObject;

                    calendar.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            return true;
                        }
                        @Override
                        public void decorate(DayViewFacade view) {
                            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
                            view.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.szabad));
                        }
                    });
                    calendar.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            try {
                                return napok.getString(day.getDay()+"") == "3" ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                        @Override
                        public void decorate(DayViewFacade view) {
                            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
                            view.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fogyoban));
                        }
                    });
                    calendar.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            try {
                                return napok.getString(day.getDay()+"") == "2" && day.getMonth()+1 == honap;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                        @Override
                        public void decorate(DayViewFacade view) {
                            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
                            view.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.van_mar));
                        }
                    });

                    calendar.addDecorator(new DayViewDecorator() {
                        @Override
                        public boolean shouldDecorate(CalendarDay day) {
                            CalendarDay date= CalendarDay.today();
                            return date != null && day.equals(date);
                        }

                        @Override
                        public void decorate(DayViewFacade view) {
                            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
                            view.addSpan(new ForegroundColorSpan(Color.WHITE));
                        }

                    });
                }
                catch (JSONException e){
                    Log.getStackTraceString(e);
                }
            }
        });

        http.adatKerese(HttpKeres.PHP_URL + "foglalas/v/android/" + ev + "/" + honap);
    }

}
