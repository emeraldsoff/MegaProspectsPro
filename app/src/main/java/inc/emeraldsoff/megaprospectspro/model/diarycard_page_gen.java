package inc.emeraldsoff.megaprospectspro.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class diarycard_page_gen {
    private String data, timestmp_mod, time;
    private Date timestmp;
    //    private Date date;
    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:mm:ss a", Locale.US);

    public diarycard_page_gen() {
    }

    public diarycard_page_gen(String data, String time, String timestmp_mod, Date timestmp) {
        this.data = data;
        this.time = time;
        this.timestmp = timestmp;
        this.timestmp_mod = timestmp_mod;
    }

    public String getdata() {
        return data;
    }

    public String gettimestmp_mod() {
        return timestmp_mod;
    }

    public String getTime() {
        time = fullFormat_time.format(timestmp);
        return time;
    }

    public Date gettimestmp() {
        return timestmp;
    }
}
