package Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import Model.Product;

public class ProductManager  {
    private static ProductManager instance;

    private static final String INSERT_STMT =
            "INSERT INTO " + DbSchema.ProductTable.NAME + "(id, name, thumbnail, unit_price, quantity) VALUES (?, ?, ?, ?, ?)";

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static ProductManager getInstance(Context context){
        if(instance == null){
            instance = new ProductManager(context);
        }
        return instance;
    }

    public ProductManager(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean addProduct(Product product){
        SQLiteStatement stm = db.compileStatement(INSERT_STMT);

        stm.bindLong(1, product.getId());

        stm.bindString(3, product.getThumbnail());
        stm.bindString(2, product.getName());
        stm.bindDouble(4, product.getUnitPrice());
        stm.bindString(5, String.valueOf(product.getQuantity()));

        long id = stm.executeInsert();
        if(id > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateQuantity(Product product){
        ContentValues cv = new ContentValues();
        cv.put(DbSchema.ProductTable.Cols.ID, product.getId());
        cv.put(DbSchema.ProductTable.Cols.NAME, product.getName());
        cv.put(DbSchema.ProductTable.Cols.THUMBNAIL, product.getThumbnail());
        cv.put(DbSchema.ProductTable.Cols.UNIT_PRICE, product.getUnitPrice());
        cv.put(DbSchema.ProductTable.Cols.QUANTITY, product.getQuantity());

        int result = db.update(DbSchema.ProductTable.NAME, cv,DbSchema.ProductTable.Cols.ID + "= ?", new String[]{product.getId()+""});
        return result > 0;


    }


    public Product findProductById(long id){
        String sql = "SELECT * FROM " + DbSchema.ProductTable.NAME + " WHERE "+  DbSchema.ProductTable.Cols.ID+ " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id+""});

        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);

        return cursorWrapper.getProductByID();
    }

    public List<Product> getAllData(){
        String sql = "SELECT * FROM " + DbSchema.ProductTable.NAME;
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);
        return cursorWrapper.getProducts();
    }

    public boolean delete (long id){
        int result = db.delete(DbSchema.ProductTable.NAME,
                DbSchema.ProductTable.Cols.ID + "= ?", new String[]{id+""} );

        return result> 0;
    }

    public int countPrice(){
        String sql = "SELECT SUM("+ DbSchema.ProductTable.Cols.QUANTITY +" * "  +DbSchema.ProductTable.Cols.UNIT_PRICE+  ") AS total FROM " + DbSchema.ProductTable.NAME;
        Cursor cursor =db.rawQuery(sql, null);

        int total = 0;
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            total = cursor.getInt(0);
        }
        total = cursor.getInt(0);
        return total;
    }


}
