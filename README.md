# RoomWordSampleKotlin
结合官网demo，学习room、viewModel、liveData
###  Room数据库

#### 数据库（Database）注解源码
```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Database {
    /**
     * The list of entities included in the database. Each entity turns into a table in the
     * database.
     *
     * @return The list of entities in the database.
     */
    Class<?>[] entities();

    /**
     * The list of database views included in the database. Each class turns into a view in the
     * database.
     *
     * @return The list of database views.
     */
    Class<?>[] views() default {};

    /**
     * The database version.
     *
     * @return The database version.
     */
    int version();

    /**
     * You can set the annotation processor argument ({@code room.schemaLocation}) to tell Room to
     * export the database schema into a folder. Even though it is not mandatory, it is a good
     * practice to have version history of your schema in your codebase and you should commit the
     * schema files into your version control system (but don't ship them with your app!).
     * <p>
     * When {@code room.schemaLocation} is set, Room will check this variable and if it is set to
     * {@code true}, the database schema will be exported into the given folder.
     * <p>
     * {@code exportSchema} is {@code true} by default but you can disable it for databases when
     * you don't want to keep history of versions (like an in-memory only database).
     *
     * @return Whether the schema should be exported to the given folder when the
     * {@code room.schemaLocation} argument is set. Defaults to {@code true}.
     */
    boolean exportSchema() default true;

    /**
     * List of AutoMigrations that can be performed on this Database.
     *
     * See {@link AutoMigration} for example code usage.
     *
     * For more complicated cases not covered by {@link AutoMigration}, runtime defined
     * {@link androidx.room.migration.Migration Migration} added with
     * {@link androidx.room.RoomDatabase.Builder#addMigrations addMigrations} can still be used.
     *
     * @return List of AutoMigration annotations.
     */
    AutoMigration[] autoMigrations() default {};
}
```
#### 数据库（Database）注解详解
**Class<?>[] entities()**
