package inc.emeraldsoff.megaprospectspro.ui_data.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;

public class activity_add_diary extends activity_main {
    String diary_page, app_userid, date;
    private Context mcontext;
    private Source cache = Source.CACHE;
    private SharedPreferences mpref;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:mm:ss a", Locale.US);
    private SimpleDateFormat folderdoc = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE", Locale.US);
    private SimpleDateFormat foldername = new SimpleDateFormat("YYYY-MMMM-dd-EEEE", Locale.US);
    private SimpleDateFormat fullFormat_time_doc = new SimpleDateFormat("YYYY-MMMM-dd-EEEE-hh-mm-ss-a", Locale.US);
    private TextInputEditText note;
    private TextView date_view;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_note);
        mcontext = this;
        super.menucreate();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        writeDiary();


    }

    private void writeDiary() {
        date_view = findViewById(R.id.diary_date);
        note = findViewById(R.id.diary_text);
        save = findViewById(R.id.save_note);
        final Date timestamp = Calendar.getInstance().getTime();
        final String folder_name = foldername.format(timestamp);
        final String folder_doc = folderdoc.format(timestamp);
        date_view.setText(fullFormat_time.format(timestamp));
        app_userid = mpref.getString("userID", "");
        final String collection = "prospect" + "/" + app_userid;
        date = com.google.firebase.Timestamp.now().toDate().toString();
        diary_page = Objects.requireNonNull(note.getText()).toString().trim();
        note.requestFocus();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diary_page = note.getText().toString().trim();
                Map<String, Object> folder = new HashMap<>();
                folder.put("folder_doc", folder_doc);
                folder.put("timestamp", timestamp);
                if (validation())
                    fdb.collection(collection + "/" + "personal_diary")
                            .document(folder_name)
                            .set(folder)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Map<String, Object> client = new HashMap<>();
                                    client.put("data", diary_page);
                                    client.put("timestmp", timestamp);
                                    client.put("timestmp_mod", fullFormat_time.format(timestamp));
                                    fdb.collection(collection + "/" + "personal_diary" + "/" + folder_name + "/" + "pages")
                                            .document(fullFormat_time_doc.format(timestamp))
                                            .set(client)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toasty.success(mcontext, "Diary Page saved successfully..!!",
                                                            4, true).show();
                                                    startActivity(new Intent(mcontext, activity_diary_content.class));
                                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toasty.error(mcontext, "Failed to save Diary Page..!! Something went wrong.",
                                                            4, true).show();
                                                    startActivity(new Intent(mcontext, activity_diary_content.class));
                                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                    finish();
                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toasty.error(mcontext, "Failed to create folder..!! Something went wrong.",
                                            4, true).show();
                                    startActivity(new Intent(mcontext, activity_diary_content.class));
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    finish();
                                }
                            });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mcontext, activity_diary_content.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private boolean validation() {
        if (diary_page.isEmpty()) {
            note.setError("Cannot save without any data..!!");
            note.requestFocus();
            return false;
        }
        return true;
    }


//    save.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            diary_page = note.getText().toString().trim();
//            Map<String, Object> client = new HashMap<>();
//            client.put("diary_page", diary_page);
//            client.put("timestamp", timestamp);
//            if (validation())
//                fdb.collection(collection + "/" + "personal_diary")
//                        .document(fullFormat_time_doc.format(timestamp))
//                        .set(client)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toasty.success(mcontext, "Diary Page saved successfully..!!",
//                                        4, true).show();
//                                startActivity(new Intent(mcontext, activity_diary_content.class));
//                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                                finish();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toasty.error(mcontext, "Failed to save Diary Page..!! Something went wrong.",
//                                        4, true).show();
//                                startActivity(new Intent(mcontext, activity_diary_content.class));
//                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                                finish();
//                            }
//                        });
//        }
//    });

}
