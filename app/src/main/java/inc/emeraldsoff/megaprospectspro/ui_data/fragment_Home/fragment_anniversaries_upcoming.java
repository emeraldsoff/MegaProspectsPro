package inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.adapter.mainanni_adapter;
import inc.emeraldsoff.megaprospectspro.model.clicard_gen;

import static android.content.Context.MODE_PRIVATE;

public class fragment_anniversaries_upcoming extends Fragment {

    private final int day = 1000 * 60 * 60 * 24;
    private Context mcontext;
    private Source cache = Source.CACHE;
    private SharedPreferences mpref;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    //    Calendar myCalendar = Calendar.getInstance();
//    Calendar calendar = Calendar.getInstance();
    private Date now = new Date();
    //    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
    private Date futuredate = null;
    private Date currentdate = null;
    //    SimpleDateFormat fullFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
//    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.US);
//    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
    private SimpleDateFormat day_monFormat = new SimpleDateFormat("dd-MM", Locale.US);
    //    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    private final long ONE_DAY = 24 * 60 * 60 * 1000;
//    Date now = new Date();
//    String dateString = formatter.format(now);

    private RecyclerView anni_list;
    private mainanni_adapter anni_adapter_up;

    @Override
    public void onStart() {
        super.onStart();
        access_anni_data();
    }

    @Override
    public void onPause() {
        super.onPause();
        access_anni_data_close();
    }

    @Override
    public void onResume() {
        super.onResume();
        access_anni_data();
    }

    @Override
    public void onStop() {
        super.onStop();
        access_anni_data_close();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_events_viewer, container, false);

        mcontext = getActivity();
        mpref = Objects.requireNonNull(mcontext)
                .getSharedPreferences("User", MODE_PRIVATE);
        anni_list = v.findViewById(R.id.id_recycle_view);

        try {
            setupannirecycle();
        } catch (Exception e) {
//            Crashlytics.getInstance();
//            Crashlytics.log(e.getMessage());
//            Toasty.error(mcontext, e+"",
//                    Toast.LENGTH_LONG,true).show();
            e.printStackTrace();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
        return v;
    }

    private void access_anni_data() {
        try {
            anni_adapter_up.startListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
    }

    private void access_anni_data_close() {
        try {
            anni_adapter_up.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
//            Toasty.error(mcontext, "Something went wrong..!!",
//                    Toast.LENGTH_LONG, true).show();
        }
    }

    private void setupannirecycle() {
//        mAuth = FirebaseAuth.getInstance();
        mpref = Objects.requireNonNull(mcontext)
                .getSharedPreferences("User", MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        calendar2.setTime(now);
        calendar2.add(Calendar.DAY_OF_YEAR, +30);
//        String date;
        try {
            futuredate = day_monFormat.parse(day_monFormat.format(calendar2.getTime()));
            currentdate = day_monFormat.parse(day_monFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String app_userid = mpref.getString("userID", "");

//        querydate();

        Query query;

        final String collection = "prospect" + "/" + app_userid;
        final CollectionReference cliref = fdb.collection(collection + "/" + "client_basic_data");
        try {

            query = cliref
//                    .orderBy("anni_code")
//                    .startAt("anni_code", currentdate)
//                    .endAt("anni_code", futuredate)
                    .orderBy("anni_code", Query.Direction.ASCENDING)
                    .whereGreaterThanOrEqualTo("anni_code", currentdate)
//                    .startAt("anni_code",currentdate)
//                    .endAt(futuredate)
//                    .whereLessThanOrEqualTo("anni_code", futuredate)
            ;

            FirestoreRecyclerOptions<clicard_gen> options = new FirestoreRecyclerOptions.Builder<clicard_gen>()
                    .setQuery(query, clicard_gen.class)
                    .build();
            anni_adapter_up = new mainanni_adapter(options);
            anni_list.setHasFixedSize(true);
            anni_list.setLayoutManager(new LinearLayoutManager(mcontext));
            anni_list.setAdapter(anni_adapter_up);


//            mainanni_adapter.startListening();
        } catch (Exception e) {
//                Crashlytics.getInstance();
//                Crashlytics.log(e.getMessage());
            e.printStackTrace();
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
