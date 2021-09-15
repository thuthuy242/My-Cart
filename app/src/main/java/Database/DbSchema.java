package Database;

public class DbSchema {
    public final class ProductTable{

        public static final String NAME ="products";

        public final class Cols{
            public static final String ID ="id";

            public static final String THUMBNAIL ="thumbnail";
            public static final String NAME ="name";
            public static final String UNIT_PRICE ="unit_price";
            public static final String QUANTITY = "quantity";
        }
    }
}
