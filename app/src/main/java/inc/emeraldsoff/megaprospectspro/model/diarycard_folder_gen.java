package inc.emeraldsoff.megaprospectspro.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class diarycard_folder_gen {
    private String folder_doc, time;
    private Date timestamp;
    //    private Date date;
    private SimpleDateFormat folderdoc = new SimpleDateFormat("YYYY-MMMM-dd', 'EEEE", Locale.US);
    private SimpleDateFormat foldername = new SimpleDateFormat("YYYY-MMMM-dd-EEEE", Locale.US);

    public diarycard_folder_gen() {
    }

    public diarycard_folder_gen(String folder_doc, String time, Date timestamp) {
        this.folder_doc = folder_doc;
        this.time = time;
        this.timestamp = timestamp;
    }

    public String getFolder_doc() {
        return folder_doc;
    }

    public String getTime() {
        return time;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
