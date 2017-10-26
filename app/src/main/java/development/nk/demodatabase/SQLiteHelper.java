package development.nk.demodatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.sql.Types.VARCHAR;

/**
 * Created by NKdevelopment on 23/10/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="RateMyDocDataBase";

    public static final String TABLE_NAME="TableDoctors";
    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_doctor_id="doctor_id";
    public static final String Table_Column_2_name="name";
    public static final String Table_Column_3_surname="surname";
    public static final String Table_Column_4_region_id="region_id";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" " +
                "("+Table_Column_ID+" INTEGER PRIMARY KEY, "
                + Table_Column_1_doctor_id+" INT, "
                + Table_Column_2_name+" VARCHAR, "
                + Table_Column_3_surname+" VARCHAR, "
                + Table_Column_4_region_id+" INT)";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
