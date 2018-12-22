package inc.emeraldsoff.megaprospectspro.ui_data;

import android.app.DatePickerDialog;
import android.content.Context;
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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import inc.emeraldsoff.megaprospectspro.R;
import inc.emeraldsoff.megaprospectspro.activity_main;
import inc.emeraldsoff.megaprospectspro.ui_data.fragmentHome.activity_home;

import static android.content.ContentValues.TAG;

public class activity_addpeople extends activity_main {

    FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .setPersistenceEnabled(true)
            .build();
    Calendar myCalendar = Calendar.getInstance();
    //    Calendar myCalendar2 = Calendar.getInstance();
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
    String g;
    String anni_dd, bday_dd;
    Date bday_code, anni_code;
    String occu, occupation, note, qualification;
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
        setContentView(R.layout.activity_addpeople);
        mcontext = this;
        super.menucreate();
        addingperson();

    }

    protected void addingperson() {

        mpref = getSharedPreferences("User", MODE_PRIVATE);


        Button save = findViewById(R.id.save_data);
//        ConstraintLayout add_cust = findViewById(R.id.add_cust);
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

        Button update = findViewById(R.id.save);


        final DatePickerDialog.OnDateSetListener dateget1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.YEAR, 0);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR_OF_DAY, 0);
//                myCalendar.set(Calendar.MINUTE, 0);
//                myCalendar.set(Calendar.SECOND, 0);
//                myCalendar.set(Calendar.MILLISECOND, 0);
                annidate();
            }
        };
        final DatePickerDialog.OnDateSetListener dateget2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.YEAR, 0);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR_OF_DAY, 0);
//                myCalendar.set(Calendar.MINUTE, 0);
//                myCalendar.set(Calendar.SECOND, 0);
//                myCalendar.set(Calendar.MILLISECOND, 0);
                bdadate();
            }
        };

        ann_dd.setOnClickListener(new View.OnClickListener() {
            Calendar myCalendar = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(mcontext, dateget1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        bda_dd.setOnClickListener(new View.OnClickListener() {
            Calendar myCalendar = Calendar.getInstance();

            @Override
            public void onClick(View v) {
                new DatePickerDialog(mcontext, dateget2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        clear_bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bda_dd.setText("");
                bday_code = null;
            }
        });

        clear_anni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ann_dd.setText("");
                anni_code = null;
            }
        });

        occu_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.unemployed:
                        occu = unemp.getText().toString();
                        break;
                    case R.id.student:
                        occu = stud.getText().toString();
                        break;
                    case R.id.home:
                        occu = hom.getText().toString();
                        break;
                    case R.id.self:
                        occu = sel.getText().toString();
                        break;
                    case R.id.govtservice:
                        occu = gov.getText().toString();
                        break;
                    case R.id.service:
                        occu = serv.getText().toString();
                        break;
                    case R.id.other:
                        occu = otr.getText().toString();
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    data_allocation();
                    if (validateInputs(client_name, mobile_no)) {

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
                        String collection = "prospect" + "/" + app_userid;
//                        if (isOnline()) {
//                            fdb.collection(collection + "/" + "client_basic_data")
//                                    .document(mobile_no + " " + client_name + " ")
//                                    .set(client)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Log.d(TAG, "DocumentSnapshot successfully written!");
////                                    Toast.makeText(mcontext, "Person Details Added To Database...", Toast.LENGTH_LONG).show();
//                                            new StyleableToast
//                                                    .Builder(mcontext)
//                                                    .text("Person Details Added To DataBase Successfully..!!")
//                                                    .iconEnd(R.drawable.ic_done_all_black_24dp)
//                                                    .textColor(getResources().getColor(R.color.dark_red))
//                                                    .backgroundColor(Color.GREEN)
//                                                    .show();
//                                            startActivity(new Intent(mcontext, addclient_activity.class));
//                                            finish();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.w(TAG, "Error writing document", e);
////                                    Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
//                                            new StyleableToast
//                                                    .Builder(mcontext)
//                                                    .text("Error writing document..!!")
//                                                    .textColor(getResources().getColor(R.color.dark_red))
//                                                    .backgroundColor(Color.RED)
//                                                    .show();
//                                        }
//                                    });
//                        } else {
                        fdb.collection(collection + "/" + "client_basic_data")
                                .document(mobile_no + " " + client_name + " ")
                                .set(client)
                                .isSuccessful();
                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                                    Toast.makeText(mcontext, "Person Details Added To Database...", Toast.LENGTH_LONG).show();
                        Toasty.success(mcontext, "Person Details Added To DataBase Successfully..!!",
                                Toast.LENGTH_LONG, true).show();
                        startActivity(new Intent(mcontext, activity_home.class));
                        finish();
//                        }

                    } else {
                        Toasty.error(mcontext, "Something went wrong...!!!",
                                Toast.LENGTH_LONG, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(mcontext, e.getMessage(),
                            Toast.LENGTH_LONG, true).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    data_allocation();
                    if (validateInputs(client_name, mobile_no)) {

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
                        String collection = "prospect" + "/" + app_userid;
//                        if (isOnline()) {
//                            fdb.collection(collection + "/" + "client_basic_data")
//                                    .document(mobile_no + " " + client_name + " ")
//                                    .set(client)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Log.d(TAG, "DocumentSnapshot successfully written!");
////                                    Toast.makeText(mcontext, "Person Details Added To Database...", Toast.LENGTH_LONG).show();
//                                            new StyleableToast
//                                                    .Builder(mcontext)
//                                                    .text("Person Details Added To DataBase Successfully..!!")
//                                                    .iconEnd(R.drawable.ic_done_all_black_24dp)
//                                                    .textColor(getResources().getColor(R.color.dark_red))
//                                                    .backgroundColor(Color.GREEN)
//                                                    .show();
//                                            startActivity(new Intent(mcontext, addclient_activity.class));
//                                            finish();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.w(TAG, "Error writing document", e);
////                                    Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
//                                            new StyleableToast
//                                                    .Builder(mcontext)
//                                                    .text("Error writing document..!!")
//                                                    .textColor(getResources().getColor(R.color.dark_red))
//                                                    .backgroundColor(Color.RED)
//                                                    .show();
//                                        }
//                                    });
//                        } else {
                        fdb.collection(collection + "/" + "client_basic_data")
                                .document(mobile_no + " " + client_name + " ")
                                .set(client)
                                .isSuccessful();
                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                                    Toast.makeText(mcontext, "Person Details Added To Database...", Toast.LENGTH_LONG).show();
                        Toasty.success(mcontext, "Person Details Added To DataBase Successfully..!!",
                                Toast.LENGTH_LONG, true).show();
                        startActivity(new Intent(mcontext, activity_home.class));
                        finish();
//                        }

                    } else {
                        Toasty.error(mcontext, "Something went wrong...!!!",
                                Toast.LENGTH_LONG, true).show();
                    }
                } catch (Exception e) {
                    Toasty.error(mcontext, e.getMessage(),
                            Toast.LENGTH_LONG, true).show();
                }
            }
        });
    }

    private void annidate() {
//        String dd,mm,yyyy;
//        dd=dayFormat.format(c.getTime());
//        mm=monthFormat.format(c.getTime());
//        yyyy=yearFormat.format(c.getTime());
        ann_dd.setText(fullFormat.format(myCalendar.getTime()));
        try {
            anni_code = day_monFormat.parse(day_monFormat.format(myCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(mcontext, "Anniversary:" + anni_code, Toast.LENGTH_LONG).show();
//        ann_mm.setText(fullFormat.format(myCalendar.getTime()));
//        ann_yyyy.setText(yearFormat.format(myCalendar.getTime()));
    }

    private void bdadate() {
        bda_dd.setText(fullFormat.format(myCalendar.getTime()));
        try {
            bday_code = day_monFormat.parse(day_monFormat.format(myCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(mcontext, "Birthday:" + bday_code, Toast.LENGTH_LONG).show();
//        bda_mm.setText(monthFormat.format(myCalendar.getTime()));
//        bda_yyyy.setText(yearFormat.format(myCalendar.getTime()));
    }

    private void data_allocation() {
        mpref = getSharedPreferences("User", MODE_PRIVATE);
        client_name = clientname_e.getText().toString().toUpperCase().trim();
        spouse = spous.getText().toString().trim();
        children = child.getText().toString().trim();
        gender = g;
        bday_dd = bda_dd.getText().toString().trim();
//        bday_code = bday_dd;
        anni_dd = ann_dd.getText().toString().trim();
//        anni_code = anni_dd;
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
        occupation = occu;
        app_userid = mpref.getString("userID", "");
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
//        Toast.makeText(this,"startActivity(new Intent(show_data_vivid.this, show_data.class));",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mcontext, activity_home.class));
        finish();
    }

}
