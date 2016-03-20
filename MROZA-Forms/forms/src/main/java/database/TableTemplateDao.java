package database;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import database.TableTemplate;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TABLE_TEMPLATE.
*/
public class TableTemplateDao extends AbstractDao<TableTemplate, Long> {

    public static final String TABLENAME = "TABLE_TEMPLATE";

    /**
     * Properties of entity TableTemplate.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property CreateDate = new Property(3, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property IsArchived = new Property(4, boolean.class, "isArchived", false, "IS_ARCHIVED");
        public final static Property ProgramId = new Property(5, long.class, "programId", false, "PROGRAM_ID");
    };

    private DaoSession daoSession;

    private Query<TableTemplate> program_TableTemplateListQuery;

    public TableTemplateDao(DaoConfig config) {
        super(config);
    }
    
    public TableTemplateDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TABLE_TEMPLATE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'DESCRIPTION' TEXT," + // 2: description
                "'CREATE_DATE' INTEGER," + // 3: createDate
                "'IS_ARCHIVED' INTEGER NOT NULL ," + // 4: isArchived
                "'PROGRAM_ID' INTEGER NOT NULL );"); // 5: programId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TABLE_TEMPLATE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TableTemplate entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(4, createDate.getTime());
        }
        stmt.bindLong(5, entity.getIsArchived() ? 1l: 0l);
        stmt.bindLong(6, entity.getProgramId());
    }

    @Override
    protected void attachEntity(TableTemplate entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TableTemplate readEntity(Cursor cursor, int offset) {
        TableTemplate entity = new TableTemplate( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // createDate
            cursor.getShort(offset + 4) != 0, // isArchived
            cursor.getLong(offset + 5) // programId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TableTemplate entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setIsArchived(cursor.getShort(offset + 4) != 0);
        entity.setProgramId(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TableTemplate entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TableTemplate entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "tableTemplateList" to-many relationship of Program. */
    public List<TableTemplate> _queryProgram_TableTemplateList(long programId) {
        synchronized (this) {
            if (program_TableTemplateListQuery == null) {
                QueryBuilder<TableTemplate> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ProgramId.eq(null));
                program_TableTemplateListQuery = queryBuilder.build();
            }
        }
        Query<TableTemplate> query = program_TableTemplateListQuery.forCurrentThread();
        query.setParameter(0, programId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getProgramDao().getAllColumns());
            builder.append(" FROM TABLE_TEMPLATE T");
            builder.append(" LEFT JOIN PROGRAM T0 ON T.'PROGRAM_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected TableTemplate loadCurrentDeep(Cursor cursor, boolean lock) {
        TableTemplate entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Program program = loadCurrentOther(daoSession.getProgramDao(), cursor, offset);
         if(program != null) {
            entity.setProgram(program);
        }

        return entity;    
    }

    public TableTemplate loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<TableTemplate> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TableTemplate> list = new ArrayList<TableTemplate>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<TableTemplate> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TableTemplate> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
