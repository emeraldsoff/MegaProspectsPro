package inc.emeraldsoff.megaprospectspro.ui_data.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.ui_data.activity_editpeople;

public class activity_show_diary_page extends activity_main {

    Source cache = Source.CACHE;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    String folder_name, docid, path;
    private Context mcontext;
    private SharedPreferences mpref;
    private TextView note, time;
    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:mm:ss a", Locale.US);
    private SimpleDateFormat folderdoc = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE", Locale.US);
    private SimpleDateFormat foldername = new SimpleDateFormat("YYYY-MMMM-dd-EEEE", Locale.US);
    private SimpleDateFormat fullFormat_time_doc = new SimpleDateFormat("YYYY-MMMM-dd-EEEE-hh-mm-ss-a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);
        mcontext = this;
        super.menucreate();
        fetchingdata();


    }

    protected void fetchingdata() {

        mpref = getSharedPreferences("User", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.menu_save_copy_share_delete);
//        toolbar.getMenu().hasVisibleItems();
//        del = toolbar.getMenu().getItem(R.id.menu_delete);
//        edit = toolbar.getMenu().getItem(R.id.menu_edit);
//        copy = toolbar.getMenu().getItem(R.id.menu_copy);
//        share = toolbar.getMenu().getItem(R.id.menu_share);
//        time = findViewById(R.id.diary_date);
        note = findViewById(R.id.diary_text);
//        Button edit = findViewById(R.id.edit);
//        Button delete = findViewById(R.id.del);

        folder_name = getIntent().getStringExtra("folder_name");
        docid = getIntent().getStringExtra("docid");
        final String path = getIntent().getStringExtra("path");

        final String app_userid = mpref.getString("userID", "");
//        String collection = "prospect" + "/" + app_userid + "/" + "personal_dairy" + "/" + folder_name + "/pages";
//        DocumentReference user = fdb.document(collection + "/" + docid);
        String collection = "prospect" + "/" + app_userid;

        DocumentReference user = fdb.collection(collection + "/" + "personal_diary" + "/" +
                folder_name + "/" + "pages").document(docid);
//        DocumentReference user = fdb.collection("prospect" + "/" + app_userid + "/" + "personal_dairy" + "/" + folder_name + "/pages").document(docid);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    StringBuilder tm = new StringBuilder();
                    StringBuilder note_text = new StringBuilder();
//                    String dt ;
                    if (doc != null) {

                        tm.append(doc.get("timestmp_mod"));
                        note_text.append(doc.get("data"));
//                        dt=tm.toString();
                        toolbar.setTitle(tm.toString());

                        note.setText(note_text.toString());

                    } else {
                        Toasty.error(mcontext, "Document is not available..!!",
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

//        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
//                    startActivity(new Intent(mcontext, activity_editpeople.class).putExtra("docid", docid));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                } else {
//                    //TODO comment 1st line
////                    startActivity(new Intent(mcontext, activity_editpeople.class).putExtra("docid", docid));
//                    Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
//                            4, true).show();
//                }
//                return false;
//            }
//        });
//
//        del.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {

//                if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
//                    new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Delete")
//                            .setMessage("Are you sure to delete this document?")
//                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
////                                    mAuth = FirebaseAuth.getInstance();
////                                    String app_userid = mAuth.getUid();
//                                    String collection = "prospect" + "/" + app_userid;
//
//                                    DocumentReference user = fdb.collection(collection + "/" + "personal_diary" + "/" +
//                                            folder_name + "/" + "pages").document(docid);
////                                fdb.document(collection+"/"+docid)
//                                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Toasty.success(mcontext, "Diary page deleted From DataBase Successfully..!!",
//                                                    Toast.LENGTH_LONG, true).show();
//
//                                            startActivity(new Intent(mcontext, activity_diary_content_pages.class));
//                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                            finish();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toasty.error(mcontext, "Error deleting document..!!",
//                                                    Toast.LENGTH_LONG, true).show();
//                                        }
//                                    });
//                                }
//                            }).setNegativeButton("No", null).show();
//                } else {
//                    //TODO comment 1st line
//                    Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
//                            4, true).show();
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this,"startActivity(new Intent(mcontext, activity_searchpeople.class));",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mcontext, activity_diary_content_pages.class).putExtra("docid", folder_name));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
