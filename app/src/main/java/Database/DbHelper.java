package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "products.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String DROP_TABLE = "DROP TABLE " + DbSchema.ProductTable.NAME;
        String CREATE_TABLE = "CREATE TABLE " + DbSchema.ProductTable.NAME + "(" +
                DbSchema.ProductTable.Cols.ID + " INTEGER PRIMARY KEY ," +
                DbSchema.ProductTable.Cols.THUMBNAIL +" TEXT," +
                DbSchema.ProductTable.Cols.NAME + " TEXT, "+
                DbSchema.ProductTable.Cols.UNIT_PRICE + " DOUBLE," +
                DbSchema.ProductTable.Cols.QUANTITY + " INTEGER" + ")";

        db.execSQL(CREATE_TABLE);
       // Toast.makeText(c, "delete ok" , Toast.LENGTH_LONG).show();
        Log.d(TAG, "Database tables created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Products", "Products: upgrading DB; dropping/recreating tables.");
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ProductTable.NAME);

        onCreate(db);
    }

}
