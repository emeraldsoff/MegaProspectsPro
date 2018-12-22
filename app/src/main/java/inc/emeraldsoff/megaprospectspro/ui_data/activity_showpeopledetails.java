package inc.emeraldsoff.megaprospectspro.ui_data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;

public class activity_showpeopledetails extends activity_main {

    Source cache = Source.CACHE;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private Context mcontext;
    private SharedPreferences mpref;
    private TextView clientname_e, spous, child;
    private TextView adri_road_house, adrii_vlg_area, adriii_city, adriv_po, adrv_pin, adrvi_dist, adrvii_state, adrviii_country;
    private TextView std_e, mob, smob, tele, email, not;
    private TextView ann, bda, gender, occupation, qualifi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpeopledetails);
        mcontext = this;
        super.menucreate();
        fetchingpersondetails();


    }

    protected void fetchingpersondetails() {

        mpref = getSharedPreferences("User", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);

        Button edit = findViewById(R.id.edit);
        Button delete = findViewById(R.id.save);
        clientname_e = findViewById(R.id.clname);
        spous = findViewById(R.id.spouse);
        child = findViewById(R.id.children);

        bda = findViewById(R.id.bday_1);
        ann = findViewById(R.id.anni_1);

        adri_road_house = findViewById(R.id.addressi);
        adrii_vlg_area = findViewById(R.id.addressii);
        adriii_city = findViewById(R.id.addressiii);
        adriv_po = findViewById(R.id.addressiv);
        adrv_pin = findViewById(R.id.addressv);
        adrvi_dist = findViewById(R.id.addressvi);
        adrvii_state = findViewById(R.id.addressvii);
        adrviii_country = findViewById(R.id.addressviii);

        qualifi = findViewById(R.id.qualifi);
        std_e = findViewById(R.id.std_code);
        mob = findViewById(R.id.mob_no);
        smob = findViewById(R.id.sec_mob_no);
        tele = findViewById(R.id.telephone_no);
        email = findViewById(R.id.email_id);
        not = findViewById(R.id.note);

        gender = findViewById(R.id.gender_select);
        occupation = findViewById(R.id.occu_grp);

        final String docid = getIntent().getStringExtra("docid");
//        Toast.makeText(mcontext,"Docpath: "+docid,Toast.LENGTH_LONG).show();
//        mAuth = FirebaseAuth.getInstance();
        final String app_userid = mpref.getString("userID", "");
        String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
        DocumentReference user = fdb.document(collection + "/" + docid);
        user.get(cache).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    StringBuilder toolbar_title = new StringBuilder();
                    StringBuilder client_name = new StringBuilder();
                    StringBuilder spouse = new StringBuilder();
                    StringBuilder children = new StringBuilder();
                    StringBuilder address_i = new StringBuilder();
                    StringBuilder address_ii = new StringBuilder();
                    StringBuilder city = new StringBuilder();
                    StringBuilder country = new StringBuilder();
                    StringBuilder post_office = new StringBuilder();
                    StringBuilder areapin = new StringBuilder();
                    StringBuilder dist = new StringBuilder();
                    StringBuilder state = new StringBuilder();
                    StringBuilder std = new StringBuilder();
                    StringBuilder mobile_no = new StringBuilder();
                    StringBuilder smobile_no = new StringBuilder();
                    StringBuilder telephoneno = new StringBuilder();
                    StringBuilder emailid = new StringBuilder();
                    StringBuilder gnder = new StringBuilder();
//                    StringBuilder date = new StringBuilder();
//                    StringBuilder app_userid = new StringBuilder();
                    StringBuilder anni_1 = new StringBuilder();
                    StringBuilder bday_1 = new StringBuilder();
                    StringBuilder qualification = new StringBuilder();
                    StringBuilder occupatn = new StringBuilder();
                    StringBuilder note = new StringBuilder();

                    if (doc != null) {
                        toolbar_title.append(doc.get("client_name"));
                        client_name.append("Name: ").append(doc.get("client_name"));
                        spouse.append(doc.get("spouse"));
                        children.append(doc.get("children"));
                        address_i.append("Road name/Area Name/House Number: ").append(doc.get("address_i"));
                        address_ii.append("Village/Area Name: ").append(doc.get("address_ii"));
                        city.append("City: ").append(doc.get("city"));
                        country.append("Country: ").append(doc.get("country"));
                        post_office.append("Post Office: ").append(doc.get("post_office"));
                        areapin.append("PIN: ").append(doc.get("areapin"));
                        dist.append("District: ").append(doc.get("dist"));
                        state.append("State: ").append(doc.get("state"));
                        std.append("STD Code:").append(doc.get("std"));
                        mobile_no.append("Mobile Number: ").append(doc.get("mobile_no"));
                        smobile_no.append("WhatsApp Number: ").append(doc.get("smobile_no"));
                        telephoneno.append("Telephone Number: ").append(doc.get("telephoneno"));
                        emailid.append("Email Id:").append(doc.get("emailid"));
                        gnder.append(doc.get("gender"));
                        anni_1.append(doc.get("anni_dd"));
                        bday_1.append(doc.get("bday_dd"));
                        qualification.append(doc.get("qualification"));
                        occupatn.append(doc.get("occupation"));
                        note.append(doc.get("note"));

                        toolbar.setTitle(toolbar_title.toString());

                        clientname_e.setText(client_name.toString());
                        spous.setText(spouse.toString());
                        child.setText(children.toString());

                        bda.setText(bday_1.toString());
                        ann.setText(anni_1.toString());

                        adri_road_house.setText(address_i.toString());
                        adrii_vlg_area.setText(address_ii.toString());
                        adriii_city.setText(city.toString());
                        adriv_po.setText(post_office.toString());
                        adrv_pin.setText(areapin.toString());
                        adrvi_dist.setText(dist.toString());
                        adrvii_state.setText(state.toString());
                        adrviii_country.setText(country.toString());

                        std_e.setText(std.toString());
                        mob.setText(mobile_no.toString());
                        smob.setText(smobile_no.toString());
                        tele.setText(telephoneno.toString());
                        email.setText(emailid.toString());
                        not.setText(note.toString());
                        qualifi.setText(qualification.toString());
                        gender.setText(gnder.toString());
                        occupation.setText(occupatn.toString());
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
                    startActivity(new Intent(mcontext, activity_editpeople.class).putExtra("docid", docid));
                    finish();
                } else {
                    //TODO comment 1st line
//                    startActivity(new Intent(mcontext, activity_editpeople.class).putExtra("docid", docid));
                    Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
                            4, true).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
                    new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Delete")
                            .setMessage("Are you sure to delete this document?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    mAuth = FirebaseAuth.getInstance();
//                                    String app_userid = mAuth.getUid();
                                    String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                                    DocumentReference user = fdb.document(collection + "/" + docid);
//                                fdb.document(collection+"/"+docid)
                                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toasty.success(mcontext, "Person Details Deleted From DataBase Successfully..!!",
                                                    Toast.LENGTH_LONG, true).show();
                                            startActivity(new Intent(mcontext, activity_searchpeople.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toasty.error(mcontext, "Error deleting document..!!",
                                                    Toast.LENGTH_LONG, true).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("no", null).show();
                } else {
                    //TODO comment 1st line
                    Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
                            4, true).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this,"startActivity(new Intent(mcontext, activity_searchpeople.class));",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mcontext, activity_searchpeople.class));
        finish();
    }

}
