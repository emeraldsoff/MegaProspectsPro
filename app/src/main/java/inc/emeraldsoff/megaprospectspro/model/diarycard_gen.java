package inc.emeraldsoff.megaprospectspro.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class diarycard_gen {
    private String diary_page, time;
    private Date timestamp;
    //    private Date date;
    private SimpleDateFormat fullFormat_time = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE', 'hh:mm:ss a", Locale.US);

    public diarycard_gen() {
    }

    public diarycard_gen(String diary_page, String time, Date timestamp) {
        this.diary_page = diary_page;
        this.time = time;
        this.timestamp = timestamp;
    }

    public String getDiary_page() {
        return diary_page;
    }

    public String getTime() {
        time = fullFormat_time.format(timestamp);
        return time;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
