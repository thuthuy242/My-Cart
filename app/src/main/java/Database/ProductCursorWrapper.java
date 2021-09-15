package Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import Model.Product;

public class ProductCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProduct(){
        long id =  getLong(getColumnIndex(DbSchema.ProductTable.Cols.ID));

        String thumbnail =  getString(getColumnIndex(DbSchema.ProductTable.Cols.THUMBNAIL));
        String name =  getString(getColumnIndex(DbSchema.ProductTable.Cols.NAME));

        int unit_price =  getInt(getColumnIndex(DbSchema.ProductTable.Cols.UNIT_PRICE));
        int quantity = getInt(getColumnIndex(DbSchema.ProductTable.Cols.QUANTITY));

        Product product = new Product(id,thumbnail,name, unit_price, quantity);

        return product;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();

        moveToFirst();
        while (!isAfterLast()){
            Product product = getProduct();
            products.add(product);

            moveToNext();
        }
        return  products;
    }


    public Product getProductByID(){
        Product product = null;
        moveToFirst();
        if(!isAfterLast()){
            product = getProduct();
        }

        return product;
    }
}
