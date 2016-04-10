/*
 * MROZA - supporting system of behavioral therapy of people with autism
 *     Copyright (C) 2015-2016 autyzm-pg
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mroza.utils;

import com.mroza.ReflectionWrapper;
import com.mroza.dao.*;
import com.mroza.models.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DatabaseUtils {

    private SqlSession utilsSqlSession;
    private static KidsDao kidsDao;
    private static ProgramsDao programsDao;
    private static TablesDao tablesDao;
    private static PeriodsDao periodsDao;
    private static KidTablesDao kidTablesDao;
    private static TableRowsDao tableRowsDao;
    private static TableFieldsDao tableFieldsDao;
    private static ResolvedFieldsDao resolvedFieldsDao;
    private static DeleteHistoryDao deleteHistoryDao;
    public DatabaseUtils()
    {
        utilsSqlSession = getSqlSession();
    }

    public SqlSession getSqlSession() {
        if(utilsSqlSession != null )
            return utilsSqlSession;

        SqlSessionFactoryProvider sqlSessionFactoryProvider =  new SqlSessionFactoryProvider();
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryProvider.produceFactory();
            utilsSqlSession = sqlSessionFactory.openSession();
        } catch (IOException exception) {
            Assert.fail("Exception was raised: " + exception.getMessage());
            return null;
        }
        return utilsSqlSession;
    }

    public void cleanUpDatabase()
    {
        tablesDao = new TablesDao();
        kidTablesDao = new KidTablesDao();
        programsDao = new ProgramsDao();
        periodsDao = new PeriodsDao();
        kidsDao = new KidsDao();
        tableRowsDao = new TableRowsDao();
        tableFieldsDao = new TableFieldsDao();
        resolvedFieldsDao = new ResolvedFieldsDao();
        deleteHistoryDao = new DeleteHistoryDao();
        ReflectionWrapper.setPrivateField(tablesDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(kidTablesDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(programsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(periodsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(kidsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(tableRowsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(tableFieldsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(resolvedFieldsDao, "sqlSession", utilsSqlSession);
        ReflectionWrapper.setPrivateField(deleteHistoryDao, "sqlSession", utilsSqlSession);
        List<Kid> kidList = kidsDao.selectAllKids();
        kidsDao.deleteKids(kidList);
        List<Program> programList = programsDao.selectAllPrograms();
        programsDao.deletePrograms(programList);
        utilsSqlSession.commit();
    }

    public Kid setUpKid(String symbol)
    {
        Kid kid = new Kid(-1, symbol, false, null, null);
        kidsDao.insertKid(kid);
        utilsSqlSession.commit();
        return kid;

    }

    public Program setUpProgram(String symbol, String name, String description) {
        Program program = new Program();
        program.setSymbol(symbol);
        program.setName(name);
        program.setDescription(description);
        program.setFinished(false);
        programsDao.insertProgram(program);
        utilsSqlSession.commit();
        return program;
    }

    public Program setUpProgram(String symbol, String name, String description, Kid kid) {
        Program program = setUpProgram(symbol, name, description);
        program.setKidId(kid.getId());
        programsDao.insertProgram(program);
        utilsSqlSession.commit();
        return program;
    }

    public void setUpTableWithRows(String tableName, List<String> rowsNames, String description, int generalization, int teaching, Program program) {
        Table table = setUpTable(tableName, description, program);
        for(int orderNum = 0; orderNum < rowsNames.size(); orderNum++){
            setUpRowWithFields(rowsNames.get(orderNum), orderNum, generalization, teaching, table);
        }

    }

    public Table setUpTable(String tableName, String description, Program program) {
        Table table = new Table();
        table.setName(tableName);
        table.setDescription(description);
        table.setArchived(false);
        table.setEdited(false);
        table.setCreateDate(LocalDate.now());
        table.setProgram(program);
        tablesDao.insertTable(table);
        utilsSqlSession.commit();
        return table;
    }

    public TableRow setUpRowWithFields(String rowName,int orderNumber, int generalizationNumber, int learningNumber, Table table){
        TableRow tableRow = new TableRow(rowName,orderNumber,learningNumber,generalizationNumber);
        tableRow.setTableId(table.getId());
        tableRowsDao.insertTableRow(tableRow);

        List<TableField> tableFields = tableRow.getRowFields();
        for(TableField field : tableFields) {
            field.setRowId(tableRow.getId());
        }
        tableFields.addAll(tableRow.getRowFields());
        tableFieldsDao.insertTableFields(tableFields);
        utilsSqlSession.commit();
        return tableRow;
    }

}
