package inc.emeraldsoff.megaprospectspro.ui_data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;

import static android.content.ContentValues.TAG;

public class activity_editpeople extends activity_main {

    Source cache = Source.CACHE;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat fullFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    //    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
//    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.US);
//    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
    SimpleDateFormat day_monFormat = new SimpleDateFormat("dd-MM", Locale.US);
    String client_name, spouse, children;
    String address_i, address_ii, city, country, post_office, areapin, dist, state;
    //    Date now = new Date();
//    String nowdate = dayformat.format(now);
//    String month = monthFormat.format(now);
    String std, mobile_no, smobile_no, telephoneno, emailid;
    String gender, date, app_userid;
    String g, g1, g2, g3;
    String anni_dd, bday_dd;
    Date bday_code, anni_code;
    String o, o1, o2, o3, o4, o5, o6, o7, occupation, note, qualification;
    String oldmobile, oldclientname;
    private Context mcontext;
    private SharedPreferences mpref;
    private EditText clientname_e, spous, child;
    private EditText adri_road_house, adrii_vlg_area, adriii_city, adriv_po, adrv_pin, adrvi_dist, adrvii_state, adrviii_country;
    private EditText std_e, mob, smob, tele, email, not;
    private EditText ann_dd, bda_dd, qualifi;
    private RadioButton male, female, unspecified, sel, gov, stud, unemp, hom, otr, serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpeople);
        mcontext = this;
        super.menucreate();
        fetchingdataforedit();

    }

    protected void fetchingdataforedit() {

        mpref = getSharedPreferences("User", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        Button update = findViewById(R.id.save_data);

        clientname_e = findViewById(R.id.clname);
        spous = findViewById(R.id.spouse);
        child = findViewById(R.id.children);

        bda_dd = findViewById(R.id.bday_dd);

//        bda_mm = findViewById(R.id.bday_mm);
//        bda_yyyy = findViewById(R.id.bday_yyyy);
        ann_dd = findViewById(R.id.anni_dd);
        Button clear_bday = findViewById(R.id.clear_bday);
        Button clear_anni = findViewById(R.id.clear_anni);
//        ann_mm = findViewById(R.id.anni_mm);
//        ann_yyyy = findViewById(R.id.anni_yyyy);
        qualifi = findViewById(R.id.qualifi);

        adri_road_house = findViewById(R.id.addressi);
        adrii_vlg_area = findViewById(R.id.addressii);
        adriii_city = findViewById(R.id.addressiii);
        adriv_po = findViewById(R.id.addressiv);
        adrv_pin = findViewById(R.id.addressv);
        adrvi_dist = findViewById(R.id.addressvi);
        adrvii_state = findViewById(R.id.addressvii);
        adrviii_country = findViewById(R.id.addressviii);

        std_e = findViewById(R.id.std_code);
        mob = findViewById(R.id.mob_no);
        smob = findViewById(R.id.sec_mob_no);
        tele = findViewById(R.id.telephone_no);
        email = findViewById(R.id.email_id);
        not = findViewById(R.id.note);

        RadioGroup gender_grp = findViewById(R.id.gender_select);
        RadioGroup occu_grp = findViewById(R.id.occu_grp);
        sel = findViewById(R.id.self);
        gov = findViewById(R.id.govtservice);
        stud = findViewById(R.id.student);
        unemp = findViewById(R.id.unemployed);
        hom = findViewById(R.id.home);
        otr = findViewById(R.id.other);
        serv = findViewById(R.id.service);
        male = findViewById(R.id.g_male);
        female = findViewById(R.id.g_female);
        unspecified = findViewById(R.id.g_unspecified);

        g1 = male.getText().toString();
        g2 = female.getText().toString();
        g3 = unspecified.getText().toString();

        o1 = sel.getText().toString();
        o2 = gov.getText().toString();
        o3 = stud.getText().toString();
        o4 = serv.getText().toString();
        o5 = hom.getText().toString();
        o6 = otr.getText().toString();
        o7 = unemp.getText().toString();

        Button save = findViewById(R.id.save);

        final DatePickerDialog.OnDateSetListener dateget1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                annidate();
            }
        };
        final DatePickerDialog.OnDateSetListener dateget2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bdadate();
            }
        };

        ann_dd.setOnClickListener(new View.OnClickListener() {
            Calendar myCalendar = Calendar.getInstance();

            @Override
            public void onClick(View v) {

                if (!ann_dd.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Exit")
                            .setMessage("Are you sure to change this Anniversary Date?")

                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new DatePickerDialog(mcontext, dateget1, myCalendar
                                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                }
                            }).setNegativeButton("no", null).show();
                } else {
                    new DatePickerDialog(mcontext, dateget1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }


            }
        });

        bda_dd.setOnClickListener(new View.OnClickListener() {
            Calendar myCalendar = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                if (!bda_dd.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Exit")
                            .setMessage("Are you sure to change this BirthDay Date?")

                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new DatePickerDialog(mcontext, dateget2, myCalendar
                                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                }
                            }).setNegativeButton("no", null).show();
                } else {
                    new DatePickerDialog(mcontext, dateget2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }


            }
        });

        clear_bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Exit")
                        .setMessage("Are you sure to clear BirthDay Date?")

                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                bda_dd.setText("");
                            }
                        }).setNegativeButton("no", null).show();
//                bda_dd.setText("");
            }
        });

        clear_anni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mcontext).setIcon(R.drawable.ic_warning_pink_24dp).setTitle("Exit")
                        .setMessage("Are you sure to clear Anniversary Date?")

                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ann_dd.setText("");
                            }
                        }).setNegativeButton("no", null).show();
            }
        });


        final String docid = getIntent().getStringExtra("docid");
//        mpref = getSharedPreferences("User", MODE_PRIVATE);
        app_userid = mpref.getString("userID", "");
//        final String docid = docid;
//        Toast.makeText(mcontext,"Docpath: "+docid,Toast.LENGTH_LONG).show();
        String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
        DocumentReference user = fdb.document(collection + "/" + docid);
        user.get(cache).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

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
                    StringBuilder anni_dd = new StringBuilder();
//                    StringBuilder anni_mm = new StringBuilder();
//                    StringBuilder anni_yyyy = new StringBuilder();
                    StringBuilder bday_dd = new StringBuilder();
//                    StringBuilder bday_mm = new StringBuilder();
//                    StringBuilder bday_yyyy = new StringBuilder();
                    StringBuilder qualification = new StringBuilder();
                    StringBuilder occupatn = new StringBuilder();
                    StringBuilder note = new StringBuilder();

                    if (doc != null) {
                        client_name.append(doc.get("client_name"));
                        spouse.append(doc.get("spouse"));
                        children.append(doc.get("children"));
                        address_i.append(doc.get("address_i"));
                        address_ii.append(doc.get("address_ii"));
                        city.append(doc.get("city"));
                        country.append(doc.get("country"));
                        post_office.append(doc.get("post_office"));
                        areapin.append(doc.get("areapin"));
                        dist.append(doc.get("dist"));
                        state.append(doc.get("state"));
                        std.append(doc.get("std"));
                        mobile_no.append(doc.get("mobile_no"));
                        smobile_no.append(doc.get("smobile_no"));
                        telephoneno.append(doc.get("telephoneno"));
                        emailid.append(doc.get("emailid"));
                        gnder.append(doc.get("gender"));
                        anni_dd.append(doc.get("anni_dd"));
//                    anni_mm.append(doc.get("anni_mm"));
//                    anni_yyyy.append(doc.get("anni_yyyy"));
                        bday_dd.append(doc.get("bday_dd"));
//                    bday_mm.append(doc.get("bday_mm"));
//                    bday_yyyy.append(doc.get("bday_yyyy"));
                        qualification.append(doc.get("qualification"));
                        occupatn.append(doc.get("occupation"));
                        note.append(doc.get("note"));
                        oldclientname = client_name.toString();

                        toolbar.setTitle(oldclientname);

                        clientname_e.setText(client_name.toString());
                        spous.setText(spouse.toString());
                        child.setText(children.toString());
                        bda_dd.setText(bday_dd.toString());
//                    bda_mm.setText(bday_mm.toString());
//                    bda_yyyy.setText(bday_yyyy.toString());
                        ann_dd.setText(anni_dd.toString());
//                    ann_dd.setText(anni_dd.toString());
//                    ann_mm.setText(anni_mm.toString());
//                    ann_yyyy.setText(anni_yyyy.toString());

                        adri_road_house.setText(address_i.toString());
                        adrii_vlg_area.setText(address_ii.toString());
                        adriii_city.setText(city.toString());
                        adriv_po.setText(post_office.toString());
                        adrv_pin.setText(areapin.toString());
                        adrvi_dist.setText(dist.toString());
                        adrvii_state.setText(state.toString());
                        adrviii_country.setText(country.toString());

                        std_e.setText(std.toString());
                        oldmobile = mobile_no.toString();
                        mob.setText(mobile_no.toString());
                        smob.setText(smobile_no.toString());
                        tele.setText(telephoneno.toString());
                        email.setText(emailid.toString());
                        not.setText(note.toString());
                        qualifi.setText(qualification.toString());
                        g = gnder.toString();
                        o = occupatn.toString();

                        if (g.equals(g1)) {
                            male.toggle();
                        } else if (g.equals(g2)) {
                            female.toggle();
                        } else if (g.equals(g3)) {
                            unspecified.toggle();
                        }

                        if (o.equals(o1)) {
                            sel.toggle();
                        } else if (o.equals(o2)) {
                            gov.toggle();
                        } else if (o.equals(o3)) {
                            stud.toggle();
                        } else if (o.equals(o4)) {
                            serv.toggle();
                        } else if (o.equals(o5)) {
                            hom.toggle();
                        } else if (o.equals(o6)) {
                            otr.toggle();
                        } else if (o.equals(o7)) {
                            unemp.toggle();
                        }
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


        occu_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.unemployed:
                        o = unemp.getText().toString();
                        break;
                    case R.id.student:
                        o = stud.getText().toString();
                        break;
                    case R.id.home:
                        o = hom.getText().toString();
                        break;
                    case R.id.self:
                        o = sel.getText().toString();
                        break;
                    case R.id.govtservice:
                        o = gov.getText().toString();
                        break;
                    case R.id.service:
                        o = serv.getText().toString();
                        break;
                    case R.id.other:
                        o = otr.getText().toString();
                        break;
                }
            }
        });

        gender_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.g_male:
                        g = male.getText().toString();
                        break;
                    case R.id.g_female:
                        g = female.getText().toString();
                        break;
                    case R.id.g_unspecified:
                        g = unspecified.getText().toString();
                        break;
//                    default:
//                        g = male.getText().toString();
//                        break;
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpref = getSharedPreferences("User", MODE_PRIVATE);
                app_userid = mpref.getString("userID", "");
                try {
                    data_allocation();
                    if (validateInputs(client_name, mobile_no)) {
                        if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
                            if (!bday_dd.isEmpty() || !bday_dd.equals("")) {
                                bday_code = day_monFormat.parse(day_monFormat.format(fullFormat.parse(bday_dd)));
                            } else {
                                bday_code = null;
                            }
                            if (!anni_dd.isEmpty() || !anni_dd.equals("")) {
                                anni_code = day_monFormat.parse(day_monFormat.format(fullFormat.parse(anni_dd)));
                            } else {
                                anni_code = null;
                            }
                            if (oldmobile.equals(mobile_no) && oldclientname.equals(client_name)) {
                                String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                                DocumentReference user = fdb.document(collection + "/" + docid);
//                        Toast.makeText(mcontext, user+" and "+docid, Toast.LENGTH_LONG).show();
                                user.update("client_name", client_name);
                                user.update("spouse", spouse);
                                user.update("children", children);
                                user.update("gender", gender);
                                user.update("address_i", address_i);
                                user.update("address_ii", address_ii);
                                user.update("city", city);
                                user.update("post_office", post_office);
                                user.update("areapin", areapin);
                                user.update("dist", dist);
                                user.update("state", state);
                                user.update("country", country);
                                user.update("std", std);
                                user.update("mobile_no", mobile_no);
                                user.update("smobile_no", smobile_no);
                                user.update("telephoneno", telephoneno);
                                user.update("emailid", emailid);
                                user.update("anni_dd", anni_dd);
//                            user.update("anni_mm", anni_mm);
//                            user.update("anni_yyyy", anni_yyyy);
                                user.update("bday_dd", bday_dd);
//                            user.update("bday_mm", bday_mm);
                                user.update("note", note);
//                            user.update("bday_yyyy", bday_yyyy);
                                user.update("bday_code", bday_code);
                                user.update("anni_code", anni_code);
                                user.update("qualification", qualification);
                                user.update("occupation", occupation);
                                user.update("date", date)
                                        .isSuccessful();
                                Toasty.success(mcontext, "Person Details Edited Successfully..!!",
                                        Toast.LENGTH_LONG, true).show();

                                startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docid));
//                                        startActivity(new Intent(mcontext, show_data.class));
                            } else {
                                final String collection = "prospect" + "/" + app_userid;
                                DocumentReference user = fdb.document(collection + "/" + "client_basic_data" + "/" + docid);
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Map<String, Object> client = new HashMap<>();
                                        client.put("client_name", client_name);
                                        client.put("spouse", spouse);
                                        client.put("children", children);
                                        client.put("gender", gender);
                                        client.put("address_i", address_i);
                                        client.put("address_ii", address_ii);
                                        client.put("city", city);
                                        client.put("post_office", post_office);
                                        client.put("areapin", areapin);
                                        client.put("dist", dist);
                                        client.put("state", state);
                                        client.put("country", country);
                                        client.put("std", std);
                                        client.put("mobile_no", mobile_no);
                                        client.put("smobile_no", smobile_no);
                                        client.put("telephoneno", telephoneno);
                                        client.put("emailid", emailid);
                                        client.put("anni_dd", anni_dd);
                                        client.put("bday_dd", bday_dd);
                                        client.put("note", note);
                                        client.put("bday_code", bday_code);
                                        client.put("anni_code", anni_code);
                                        client.put("qualification", qualification);
                                        client.put("occupation", occupation);
                                        client.put("date", date);
                                        client.put("app_userid", app_userid);
//                                        String collection1 = "prospect" + "/" + app_userid;
                                        fdb.collection(collection + "/" + "client_basic_data")
                                                .document(mobile_no + " " + client_name + " ")
                                                .set(client)
                                                .isSuccessful();
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        Toasty.success(mcontext, "Person Details Edited Successfully..!!",
                                                Toast.LENGTH_LONG, true).show();

                                        String docidupdate = mobile_no + " " + client_name + " ";

                                        startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docidupdate));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toasty.error(mcontext, "Something went wrong...!!!",
                                                Toast.LENGTH_LONG, true).show();
                                    }
                                });
                            }
                        } else {
                            Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
                                    4, true).show();
                        }
                    } else {
                        Toasty.error(mcontext, "Something went wrong...!!!",
                                Toast.LENGTH_LONG, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(mcontext, e.getMessage(), Toast.LENGTH_LONG, true).show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(activity_showpeopledetails.this, editdataActivity.class).putExtra("docid", docid));
//                mAuth = FirebaseAuth.getInstance();
                mpref = getSharedPreferences("User", MODE_PRIVATE);
                app_userid = mpref.getString("userID", "");
                try {
                    data_allocation();
                    if (validateInputs(client_name, mobile_no)) {
                        if (isOnline() || mpref.getBoolean("IF_VALID", true)) {
                            if (!bday_dd.isEmpty() || !bday_dd.equals("")) {
                                bday_code = day_monFormat.parse(day_monFormat.format(fullFormat.parse(bday_dd)));
                            }
                            if (!anni_dd.isEmpty() || !anni_dd.equals("")) {
                                anni_code = day_monFormat.parse(day_monFormat.format(fullFormat.parse(anni_dd)));
                            }
                            if (oldmobile.equals(mobile_no) && oldclientname.equals(client_name)) {
                                String collection = "prospect" + "/" + app_userid + "/" + "client_basic_data";
                                DocumentReference user = fdb.document(collection + "/" + docid);
//                        Toast.makeText(mcontext, user+" and "+docid, Toast.LENGTH_LONG).show();
                                user.update("client_name", client_name);
                                user.update("spouse", spouse);
                                user.update("children", children);
                                user.update("gender", gender);
                                user.update("address_i", address_i);
                                user.update("address_ii", address_ii);
                                user.update("city", city);
                                user.update("post_office", post_office);
                                user.update("areapin", areapin);
                                user.update("dist", dist);
                                user.update("state", state);
                                user.update("country", country);
                                user.update("std", std);
                                user.update("mobile_no", mobile_no);
                                user.update("smobile_no", smobile_no);
                                user.update("telephoneno", telephoneno);
                                user.update("emailid", emailid);
                                user.update("anni_dd", anni_dd);
//                            user.update("anni_mm", anni_mm);
//                            user.update("anni_yyyy", anni_yyyy);
                                user.update("bday_dd", bday_dd);
//                            user.update("bday_mm", bday_mm);
                                user.update("note", note);
//                            user.update("bday_yyyy", bday_yyyy);
                                user.update("bday_code", bday_code);
                                user.update("anni_code", anni_code);
                                user.update("qualification", qualification);
                                user.update("occupation", occupation);
                                user.update("date", date)
                                        .isSuccessful();
                                Toasty.success(mcontext, "Person Details Edited Successfully..!!",
                                        Toast.LENGTH_LONG, true).show();

                                startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docid));
//                                        startActivity(new Intent(mcontext, show_data.class));
                            } else {
                                final String collection = "prospect" + "/" + app_userid;
                                DocumentReference user = fdb.document(collection + "/" + "client_basic_data" + "/" + docid);
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Map<String, Object> client = new HashMap<>();
                                        client.put("client_name", client_name);
                                        client.put("spouse", spouse);
                                        client.put("children", children);
                                        client.put("gender", gender);
                                        client.put("address_i", address_i);
                                        client.put("address_ii", address_ii);
                                        client.put("city", city);
                                        client.put("post_office", post_office);
                                        client.put("areapin", areapin);
                                        client.put("dist", dist);
                                        client.put("state", state);
                                        client.put("country", country);
                                        client.put("std", std);
                                        client.put("mobile_no", mobile_no);
                                        client.put("smobile_no", smobile_no);
                                        client.put("telephoneno", telephoneno);
                                        client.put("emailid", emailid);
                                        client.put("anni_dd", anni_dd);
                                        client.put("bday_dd", bday_dd);
                                        client.put("note", note);
                                        client.put("bday_code", bday_code);
                                        client.put("anni_code", anni_code);
                                        client.put("qualification", qualification);
                                        client.put("occupation", occupation);
                                        client.put("date", date);
                                        client.put("app_userid", app_userid);
//                                        String collection1 = "prospect" + "/" + app_userid;
                                        fdb.collection(collection + "/" + "client_basic_data")
                                                .document(mobile_no + " " + client_name + " ")
                                                .set(client)
                                                .isSuccessful();
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        Toasty.success(mcontext, "Person Details Edited Successfully..!!",
                                                Toast.LENGTH_LONG, true).show();

                                        String docidupdate = mobile_no + " " + client_name + " ";

                                        startActivity(new Intent(mcontext, activity_showpeopledetails.class)
                                                .putExtra("docid", docidupdate));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toasty.error(mcontext, "Something went wrong..!!",
                                                Toast.LENGTH_LONG, true).show();
                                    }
                                });
                            }
                        } else {
                            Toasty.error(mcontext, "Turn on wifi or mobile data to edit..!!",
                                    4, true).show();
                        }
                    } else {
                        Toasty.error(mcontext, "Something went wrong..!!",
                                Toast.LENGTH_LONG, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(mcontext, "Something went wrong..!!",
                            Toast.LENGTH_LONG, true).show();
                }
            }
        });
    }

    private void annidate() {
        ann_dd.setText(fullFormat.format(myCalendar.getTime()));
    }

    private void bdadate() {
        bda_dd.setText(fullFormat.format(myCalendar.getTime()));
    }

    private void data_allocation() {
        client_name = clientname_e.getText().toString().toUpperCase().trim();
        spouse = spous.getText().toString().trim();
        children = child.getText().toString().trim();
        gender = g;
        bday_dd = bda_dd.getText().toString().trim();

        anni_dd = ann_dd.getText().toString().trim();

        address_i = adri_road_house.getText().toString().trim();
        address_ii = adrii_vlg_area.getText().toString().trim();
        city = adriii_city.getText().toString().trim();
        post_office = adriv_po.getText().toString().trim();
        areapin = adrv_pin.getText().toString().trim();
        dist = adrvi_dist.getText().toString().trim();
        state = adrvii_state.getText().toString().trim();
        country = adrviii_country.getText().toString().trim();
        std = std_e.getText().toString().trim();
        mobile_no = mob.getText().toString().trim();
        smobile_no = smob.getText().toString().trim();
        telephoneno = tele.getText().toString().trim();
        emailid = email.getText().toString().trim();
        note = not.getText().toString().trim();
        qualification = qualifi.getText().toString().trim();
        occupation = o;
//        app_userid = userid;
        date = com.google.firebase.Timestamp.now().toDate().toString();
    }

    private boolean validateInputs(String client_name, String mobile_no) {
        if (client_name.isEmpty()) {
            clientname_e.setError("Name Required..!!");
            clientname_e.requestFocus();
            return false;
        } else if (mobile_no.isEmpty()) {
            mob.setError("Mobile Number Required..!!");
            mob.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String docid = getIntent().getStringExtra("docid");
        startActivity(new Intent(mcontext, activity_showpeopledetails.class).putExtra("docid", docid));
        finish();
    }

}
