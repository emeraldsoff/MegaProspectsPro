package inc.emeraldsoff.megaprospectspro.ui_data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SearchView;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.adapter.cli_adapter;
import inc.emeraldsoff.megaprospectspro.model.clicard_gen;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home.activity_home;

import static inc.emeraldsoff.megaprospectspro.Constants.MY_PERMISSIONS_CALL_PHONE;

public class activity_searchpeople extends activity_main {
    SharedPreferences mpref;
    SearchView search;
    RecyclerView id_recycleview;
    //    ScrollView scrollview;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    Source cache = Source.CACHE;
    private Context mcontext;
    private cli_adapter cliadapter;

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth = FirebaseAuth.getInstance();
        int SPLASH_DISPLAY_LENGTH = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                access_data();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpeople);
        super.menucreate();
        mcontext = this;
        searchaction();

    }

    public void searchaction() {
//        fdb.setFirestoreSettings(settings);
        search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    setuprecycle();
                    cliadapter.startListening();
                } else {
                    setUpRecycleview(newText.toUpperCase());
                    cliadapter.startListening();
                }
                return false;
            }
        });
        setuprecycle();
    }

    private void setUpRecycleview(CharSequence q) {
        mpref = getSharedPreferences("User", MODE_PRIVATE);

        final String app_userid = mpref.getString("userID", "");
        Query query;
//        CharSequence charSequence = q;
        String collection = "prospect" + "/" + app_userid;
        final CollectionReference cliref = fdb.collection(collection + "/" + "client_basic_data");
        try {

            query = cliref

                    .orderBy("client_name", Query.Direction.ASCENDING)
//                    .orderBy("mobile_number")
//                    .whereLessThanOrEqualTo("client_name",q)
                    .startAt(q.toString().trim())
                    .endAt(q.toString().trim() + "\uf8ff")//"\uf8ff" is reqd to specify char type.
//                    .orderBy("mobile_no", Query.Direction.ASCENDING)
//                    .orderBy("smobile_no", Query.Direction.ASCENDING);
            ;
            FirestoreRecyclerOptions<clicard_gen> options = new FirestoreRecyclerOptions.Builder<clicard_gen>()
                    .setQuery(query, clicard_gen.class)
                    .build();
            cliadapter = new cli_adapter(options);
            id_recycleview = findViewById(R.id.id_recycle_view);
            id_recycleview.setHasFixedSize(true);
            id_recycleview.setLayoutManager(new LinearLayoutManager(this));
            id_recycleview.setAdapter(cliadapter);
        } catch (Exception e) {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        cliadapter.setOnItemClickListener(new cli_adapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                clicard_gen cli = documentSnapshot.toObject(clicard_gen.class);
                String docid = documentSnapshot.getId();
//                String docpath = documentSnapshot.getReference().getPath();
                String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                final DocumentReference user = fdb.document(collection + "/" + docid);
//                Source cache=Source.CACHE;
                user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                    ActivityCompat.requestPermissions(activity_searchpeople.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_CALL_PHONE);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", call, null));
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

                            }
                        });
            }
        });
        cliadapter.setOnLongItemClickListener(new cli_adapter.onLongItemClickListener() {
            @Override
            public void onLongItemClick(DocumentSnapshot documentSnapshot, final int position) {
                //                clicard_gen cli = documentSnapshot.toObject(clicard_gen.class);
                String docid = documentSnapshot.getId();
//                String docpath = documentSnapshot.getReference().getPath();
                startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docid));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    private void setuprecycle() {
//        mAuth = FirebaseAuth.getInstance();
        mpref = getSharedPreferences("User", MODE_PRIVATE);

        final String app_userid = mpref.getString("userID", "");
        Query query, next;
        String collection = "prospect" + "/" + app_userid;
        final CollectionReference cliref = fdb.collection(collection + "/" + "client_basic_data");
        try {
            query = cliref.orderBy("client_name", Query.Direction.ASCENDING)
                    .orderBy("mobile_no", Query.Direction.ASCENDING)
                    .orderBy("smobile_no", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<clicard_gen> options = new FirestoreRecyclerOptions.Builder<clicard_gen>()
                    .setQuery(query, clicard_gen.class)
                    .build();
            cliadapter = new cli_adapter(options);
            id_recycleview = findViewById(R.id.id_recycle_view);
            id_recycleview.setHasFixedSize(true);
            id_recycleview.setLayoutManager(new LinearLayoutManager(this));
            id_recycleview.setAdapter(cliadapter);
        } catch (Exception e) {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        cliadapter.setOnItemClickListener(new cli_adapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String docid = documentSnapshot.getId();
//                mAuth = FirebaseAuth.getInstance();
//                String app_userid = mAuth.getUid();
                String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                final DocumentReference user = fdb.document(collection + "/" + docid);
                user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                    ActivityCompat.requestPermissions(activity_searchpeople.this,
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
        cliadapter.setOnLongItemClickListener(new cli_adapter.onLongItemClickListener() {
            @Override
            public void onLongItemClick(DocumentSnapshot documentSnapshot, final int position) {

//                clicard_gen cli = documentSnapshot.toObject(clicard_gen.class);
                String docid = documentSnapshot.getId();
//                String docpath = documentSnapshot.getReference().getPath();
                startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docid));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
//                Toast.makeText(mcontext,"Position: "+position+" DOCID: "+docid,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void access_data() {
        try {
            if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
                cliadapter.startListening();
            } else {
                Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
                        4, true).show();
            }
        } catch (Exception e) {
//            Toasty.error(mcontext, e+"",
//                    Toast.LENGTH_LONG,true).show();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
    }

    private void stop_data() {
        try {
            cliadapter.stopListening();
        } catch (Exception e) {
//            Toasty.error(mcontext, e+"",
//                    Toast.LENGTH_LONG,true).show();
            Toasty.error(mcontext, "Something went wrong..!!",
                    Toast.LENGTH_LONG, true).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        access_data();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop_data();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mcontext, activity_home.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
