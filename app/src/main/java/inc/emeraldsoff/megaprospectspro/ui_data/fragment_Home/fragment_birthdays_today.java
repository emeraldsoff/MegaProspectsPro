package inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.adapter.mainbday_adapter;
import inc.emeraldsoff.megaprospectspro.model.clicard_gen;

import static android.content.Context.MODE_PRIVATE;
import static inc.emeraldsoff.megaprospectspro.Constants.MY_PERMISSIONS_CALL_PHONE;

//import com.crashlytics.android.Crashlytics;

public class fragment_birthdays_today extends Fragment {

    private final int day = 1000 * 60 * 60 * 24;
    private Context mcontext;
    private Source cache = Source.CACHE;
    private SharedPreferences mpref;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    //    Calendar myCalendar = Calendar.getInstance();
//    Calendar calendar = Calendar.getInstance();
    private Date now = new Date();
    //    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
//    private Date futuredate = null;
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

    private RecyclerView bday_list;
    private mainbday_adapter bday_adapter;

    @Override
    public void onStart() {
        super.onStart();
        access_bday_data();
    }

    @Override
    public void onPause() {
        super.onPause();
        access_bday_data_close();
    }

    @Override
    public void onResume() {
        super.onResume();
        access_bday_data();
    }

    @Override
    public void onStop() {
        super.onStop();
        access_bday_data_close();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_events_viewer, container, false);


        mcontext = getActivity();
        mpref = Objects.requireNonNull(mcontext)
                .getSharedPreferences("User", MODE_PRIVATE);
        bday_list = v.findViewById(R.id.id_recycle_view);

        try {
            setupbdayrecycle();
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

    private void access_bday_data() {
        try {
            bday_adapter.startListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
    }

    private void access_bday_data_close() {
        try {
            bday_adapter.stopListening();
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
    }

    private void setupbdayrecycle() {
//        mAuth = FirebaseAuth.getInstance();
        mpref = Objects.requireNonNull(mcontext)
                .getSharedPreferences("User", MODE_PRIVATE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(now);
//        calendar.add(Calendar.DAY_OF_YEAR, +7);
//        String date;
        try {
//            futuredate = day_monFormat.parse(day_monFormat.format(calendar.getTime()));
            currentdate = day_monFormat.parse(day_monFormat.format(now));
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

                    .orderBy("bday_code", Query.Direction.ASCENDING)
                    .whereEqualTo("bday_code", currentdate)
//                    .whereGreaterThanOrEqualTo("bday_code", currentdate)
//                    .whereLessThanOrEqualTo("bday_code", futuredate)
            ;

            FirestoreRecyclerOptions<clicard_gen> options = new FirestoreRecyclerOptions.Builder<clicard_gen>()
                    .setQuery(query, clicard_gen.class)
                    .build();
            bday_adapter = new mainbday_adapter(options);
            bday_list.setHasFixedSize(true);
            bday_list.setLayoutManager(new LinearLayoutManager(mcontext));
            bday_list.setAdapter(bday_adapter);


//            mainbday_adapter.startListening();
        } catch (Exception e) {
//                Crashlytics.getInstance();
//                Crashlytics.log(e.getMessage());
            e.printStackTrace();
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        bday_adapter.setOnItemClickListener(new mainbday_adapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String docid = documentSnapshot.getId();
//                mAuth = FirebaseAuth.getInstance();
//                String app_userid = mAuth.getUid();
//                collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                final DocumentReference user = fdb.document(collection + "/" + "client_basic_data" + "/" + docid);
                user.get(cache).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            StringBuilder mobile_no = new StringBuilder();
                            if (doc != null) {
                                mobile_no.append(doc.get("mobile_no"));
                                String call = String.valueOf(mobile_no);
                                if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_CALL_PHONE);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", call, null));
                                    startActivity(intent);
                                }
                            } else {
                                Toasty.error(mcontext, "Something went wrong..!!",
                                        Toast.LENGTH_LONG, true).show();
                            }
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(mcontext, "Something went wrong..!!",
                                        Toast.LENGTH_LONG, true).show();
                            }
                        });
            }
        });
        bday_adapter.setOnLongItemClickListener(new mainbday_adapter.onLongItemClickListener() {
            @Override
            public void onLongItemClick(DocumentSnapshot documentSnapshot, final int position) {
                String docid = documentSnapshot.getId();
//                mAuth = FirebaseAuth.getInstance();
//                String app_userid = mAuth.getUid();
//                collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                final DocumentReference user = fdb.document(collection + "/" + "client_basic_data" + "/" + docid);
                user.get(cache).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    //                    @SuppressLint("MissingPermission")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            StringBuilder mobile_no = new StringBuilder();
                            StringBuilder client_name = new StringBuilder();
                            StringBuilder gender = new StringBuilder();
                            if (doc != null) {
                                client_name.append(doc.get("client_name"));
                                mobile_no.append(doc.get("mobile_no"));
                                gender.append(doc.get("gender"));
//                            String call = String.valueOf(mobile_no);
//                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", call, null));
//                                SmsManager smsManager = SmsManager.getDefault();
//                                if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.SEND_SMS)
//                                        '!'= PackageManager.PERMISSION_GRANTED) {
//                                    ActivityCompat.requestPermissions(MainActivity.this,
//                                            new String[]{Manifest.permission.SEND_SMS},
//                                            MY_PERMISSIONS_REQUEST_SEND_SMS);
//                                }
//                                else{
                                try {
                                    String message;
                                    switch (gender.toString()) {
                                        case "Female":
                                            message = "Mrs./Miss " + client_name + " On your special day, I wish " +
                                                    "you good health, happiness, and a fantastic birthday..!!";
                                            break;
                                        case "Male":
                                            message = "Mr. " + client_name + " On your special day, I wish " +
                                                    "you good health, happiness, and a fantastic birthday..!!";
                                            break;
                                        default:
                                            message = "Mr./Mrs./Miss " + client_name + " On your special day, I wish " +
                                                    "you good health, happiness, and a fantastic birthday..!!";
                                            break;
                                    }

//                                    smsManager.sendTextMessage(String.valueOf(mobile_no), null,
//                                            "Mr./Mrs./Miss " + client_name + " Wish you a very happy anniversary." +
//                                                    " May your a celebration of love turn out as beautiful as the both of you." +
//                                                    " Best wishes to you both on this momentous occasion. Congratulations..!!",
//                                            null, null);

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mobile_no));
                                    intent.putExtra("sms_body", message);
                                    startActivity(intent);

                                } catch (Exception e) {
//                                        Crashlytics.getInstance();
//                                        Crashlytics.log(e.getMessage());
                                    e.printStackTrace();
                                    Toasty.error(mcontext, "Something went wrong..!!",
                                            Toast.LENGTH_LONG, true).show();
                                }
//                                StyleableToast.makeText(mcontext, "Message Sent Successfully..!!",
//                                        2000, R.style.successful).show();
//                                }
////                            startActivity(intent);
//                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                            sendIntent.putExtra(String.valueOf(mobile_no), "Mr./Mrs./Miss " +client_name+ " Wish you a very happy anniversary." +
//                                    " May your a celebration of love turn out as beautiful as the both of you." +
//                                    " Best wishes to you both on this momentous occasion. Congratulations..!!");
//                            sendIntent.setType("vnd.android-dir/mms-sms");
//                            startActivity(sendIntent);
                            } else {
                                Toasty.error(mcontext, "Something went wrong..!!",
                                        Toast.LENGTH_LONG, true).show();
                            }

                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(mcontext, "Something went wrong..!!",
                                        Toast.LENGTH_LONG, true).show();
                            }
                        });

            }
        });
    }

}
