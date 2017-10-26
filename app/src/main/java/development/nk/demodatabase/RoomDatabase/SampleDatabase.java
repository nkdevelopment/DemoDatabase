package development.nk.demodatabase.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by NKdevelopment on 6/10/2017.
 */

@Database(entities = {University.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract ContactsRoomDAO daoAccess();
}
