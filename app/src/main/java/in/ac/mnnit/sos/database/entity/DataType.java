package in.ac.mnnit.sos.database.entity;

/**
 * Created by Banda Prashanth Yadav on 1/3/17.
 */

public class DataType {
    static final String TYPE_HOME = "Home";
    static final String TYPE_MOBILE = "Mobile";
    static final String TYPE_WORK = "Work";
    static final String TYPE_MAIN = "Main";
    static final String TYPE_WORK_FAX = "Work Fax";
    static final String TYPE_HOME_FAX = "Home Fax";
    static final String TYPE_PAGER = "Pager";
    static final String TYPE_WORK_MOBILE = "Work Mobile";
    static final String TYPE_OTHER = "Other";
    static final String TYPE_CUSTOM = "Custom";

    public static final String[] type = new String[]{
            TYPE_HOME,
            TYPE_MOBILE,
            TYPE_WORK,
            TYPE_MAIN,
            TYPE_WORK_FAX,
            TYPE_HOME_FAX,
            TYPE_PAGER,
            TYPE_WORK_MOBILE,
            TYPE_OTHER,
            TYPE_CUSTOM
    };
}
