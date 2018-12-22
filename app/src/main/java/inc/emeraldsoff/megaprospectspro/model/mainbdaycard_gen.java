package inc.emeraldsoff.megaprospectspro.model;

import java.util.Date;

public class mainbdaycard_gen {
    private String client_name, mobile_no;
    private String anni_dd, bday_dd;
    private Date anni_code, bday_code;

    public mainbdaycard_gen() {
    }

    public mainbdaycard_gen(String client_name, String mobile_no, String anni_dd, String bday_dd, Date anni_code, Date bday_code) {
        this.client_name = client_name;
        this.mobile_no = mobile_no;
        this.anni_dd = anni_dd;
        this.bday_dd = bday_dd;
        this.anni_code = anni_code;
        this.bday_code = bday_code;
    }


    public String getClient_name() {
        return client_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getAnni_dd() {
        return anni_dd;
    }

    public String getBday_dd() {
        return bday_dd;
    }

    public Date getAnni_code() {
        return anni_code;
    }

    public Date getBday_code() {
        return bday_code;
    }
}
