package inc.emeraldsoff.megaprospectspro.ui_data.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.adapter.diary_folder_adapter;
import inc.emeraldsoff.megaprospectspro.model.diarycard_folder_gen;
import inc.emeraldsoff.megaprospectspro.ui_data.fragment_Home.activity_home;

public class activity_diary_content extends activity_main {
    SharedPreferences mpref;
    RecyclerView id_recycleview;
    //    ScrollView scrollview;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    Source cache = Source.CACHE;
    FloatingActionButton fab;
    int fab_ht;
    private Context mcontext;
    private diary_folder_adapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth = FirebaseAuth.getInstance();
        int SPLASH_DISPLAY_LENGTH = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.startListening();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        mcontext = this;
        super.menucreate();
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        setDiary();
        fab_action();


    }

    private void setDiary() {
        mpref = getSharedPreferences("User", MODE_PRIVATE);

        final String app_userid = mpref.getString("userID", "");
        Query query, next;
        String collection = "prospect" + "/" + app_userid;
        final CollectionReference cliref = fdb.collection(collection + "/" + "personal_diary");
        try {
            query = cliref.orderBy("timestamp", Query.Direction.DESCENDING)
            ;
            FirestoreRecyclerOptions<diarycard_folder_gen> options = new FirestoreRecyclerOptions.Builder<diarycard_folder_gen>()
                    .setQuery(query, diarycard_folder_gen.class)
                    .build();
            adapter = new diary_folder_adapter(options);
            id_recycleview = findViewById(R.id.id_recycle_view);
            id_recycleview.setHasFixedSize(true);
            id_recycleview.setLayoutManager(new LinearLayoutManager(this));
            id_recycleview.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        adapter.setOnItemClickListener(new diary_folder_adapter.onItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String docid = documentSnapshot.getId();
//                mAuth = FirebaseAuth.getInstance();
//                String app_userid = mAuth.getUid();
                startActivity(new Intent(mcontext, activity_diary_content_pages.class).putExtra("docid", docid));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    public void fab_action() {
        fab = findViewById(R.id.fab_diary);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mcontext, activity_add_diary.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(mcontext, activity_home.class));
        isDestroyed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
