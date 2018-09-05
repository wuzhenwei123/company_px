package com.base.production.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.production.domain.Table;
import com.base.production.tools.AssistTools;

public class JDBCHandle {
	private static final String BASE_SQL = "SELECT * FROM %s";
	/** 用来查询数据库备注信息 **/
	private static final String BASE_SCHEMA_SQL = "SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s'";

	public static Table getColumns(Table table, Connection conn) {
		List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> reList = new ArrayList<Map<Object, Object>>();
		List<Map<Object, Object>> reList1 = new ArrayList<Map<Object, Object>>();
		ResultSet rs = null;
		Statement stmt = null;
		DatabaseMetaData dbmd;
		PreparedStatement pstmt;
		try {
			String schemaSql = String.format(BASE_SCHEMA_SQL, table.getTableName(), JDBCTemplate.DBNAME);
			pstmt = (PreparedStatement) conn.prepareStatement(schemaSql);
			ResultSet rs1 = pstmt.executeQuery();
			if (rs1.next()) {
				String tableComment = rs1.getString(1);
				if (tableComment != null) {
					table.setTableComment(tableComment);//表备注
				}
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(BASE_SQL, table.getTableName()));
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<Object, Object> fields = null;
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				fields = new HashMap<Object, Object>();
				String name = rsmd.getColumnName(i);
				int jdbcType = rsmd.getColumnType(i);
				String type = changeJavaType(jdbcType);
				fields.put("columnLabel", rsmd.getColumnLabel(i));
				fields.put("name", name);
				fields.put("javaName", AssistTools.underlineToCamel(name));
				fields.put("type", type);
				fields.put("jdbcType", changeJDBCType(jdbcType));

				fields.put("isPK", false);
				fields.put("isFK", false);
				list.add(fields);
			}
			dbmd = conn.getMetaData();
			ResultSet rPrimaryKey;
			String databaseName = dbmd.getDatabaseProductName().toUpperCase();
			if (databaseName.indexOf("ORACLE") != -1) {
				rPrimaryKey = dbmd.getPrimaryKeys(null,
						JDBCTemplate.USERNAME.toUpperCase(),
						table.getTableName());
			} else {
				rPrimaryKey = dbmd.getPrimaryKeys(null, null,
						table.getTableName());
			}
			while (rPrimaryKey.next()) {

				String pk = rPrimaryKey.getString(4);
				for (int i = 0; i < list.size(); i++) {
					Map<Object, Object> map = (Map<Object, Object>) list.get(i);

					if (pk.equals(map.get("name"))) {
						Map<Object, Object> mapPK = new HashMap<Object, Object>();
						String name = map.get("name").toString();
						mapPK.put("name", name);
						mapPK.put("type", map.get("type"));
						mapPK.put("javaName", AssistTools.underlineToCamel(name));
						mapPK.put("jdbcType", map.get("jdbcType"));
						table.setPkType((String) mapPK.get("type"));
						table.setPkName((String) name);
						mapPK.put("isPK", true);
						mapPK.put("isFK", false);
						mapPK.put("columnLabel", "");
						reList.add(mapPK);
						list.remove(map);
						i = list.size() + 1;
					}
				}
			}
			reList.addAll(list);
			ResultSet columnSet = dbmd.getColumns(null, "%",
					table.getTableName(), "%");
			if (null != columnSet) {
				while (columnSet.next()) {
					for (Map<Object, Object> map : reList) {
						String name = map.get("name").toString();
						String columnName = columnSet.getString("COLUMN_NAME");
						if (name.equalsIgnoreCase(columnName)) {
							String remarks = columnSet.getString("REMARKS");
							// String typeName =
							// columnSet.getString("TYPE_NAME");
							map.put("remarks", remarks);
							reList1.add(map);
						}
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs, stmt, conn);
		}
		table.setList(reList1);
		return table;
	}

	private static String changeJavaType(int type) {
		switch (type) {
		case Types.ARRAY:
			return "java.sql.Array";
		case Types.BIGINT:
			return "Long";
		case Types.BINARY:
			return "byte[]";
		case Types.BIT:
			return "boolean";
		case Types.BLOB:
			return "java.sql.Blob";
		case Types.BOOLEAN:
			return "boolean";
		case Types.CHAR:
			return "String";
		case Types.CLOB:
			return "java.sql.Clob";
		case Types.DATALINK:
			return "Float";
		case Types.DATE:
			return "java.sql.Date";
		case Types.DECIMAL:
			return "java.math.BigDecimal";
			// case Types.DISTINCT:
			// return "";
		case Types.DOUBLE:
			return "Float";
		case Types.FLOAT:
			return "Float";
		case Types.INTEGER:
			return "Integer";
			// case Types.JAVA_OBJECT:
			// return "";
		case Types.LONGNVARCHAR:
			return "String";
		case Types.LONGVARBINARY:
			return "byte[]";
		case Types.LONGVARCHAR:
			return "String";
		case Types.NCHAR:
			return "String";
		case Types.NCLOB:
			return "java.sql.NClob";
		case Types.NULL:
			return "";
		case Types.NUMERIC:
			return "java.math.BigDecimal";
		case Types.NVARCHAR:
			return "String";
			// case Types.OTHER:
			// return "";
			// case Types.REAL:
			// return "";
			// case Types.REF:
			// return "";
			// case Types.ROWID:
			// return "";
		case Types.SMALLINT:
			return "short";
			// case Types.SQLXML:
			// return "";
			// case Types.STRUCT:
			// return "";
		case Types.TIME:
			return "java.sql.Time";
		case Types.TIMESTAMP:
			return "Date";
		case Types.TINYINT:
			return "Integer";
		case Types.VARBINARY:
			return "byte[]";
		case Types.VARCHAR:
			return "String";
		default:
			return "String";
		}
	}
	private static String changeJDBCType(int type) {
		switch (type) {
		case Types.ARRAY:
			return "ARRAY";
		case Types.BIGINT:
			return "BIGINT";
		case Types.BINARY:
			return "BINARY";
		case Types.BIT:
			return "BOOLEAN";
		case Types.BLOB:
			return "BLOB";
		case Types.BOOLEAN:
			return "BOOLEAN";
		case Types.CHAR:
			return "CHAR";
		case Types.CLOB:
			return "CLOB";
		case Types.DATALINK:
			return "DATALINK";
		case Types.DATE:
			return "TIMESTAMP";
		case Types.DECIMAL:
			return "DECIMAL";
		case Types.DOUBLE:
			return "DOUBLE";
		case Types.FLOAT:
			return "FLOAT";
		case Types.INTEGER:
			return "INTEGER";
		case Types.LONGNVARCHAR:
			return "VARCHAR";
		case Types.LONGVARBINARY:
			return "LONGVARBINARY";
		case Types.LONGVARCHAR:
			return "VARCHAR";
		case Types.NCHAR:
			return "VARCHAR";
		case Types.NCLOB:
			return "NCLOB";
		case Types.NULL:
			return "";
		case Types.NUMERIC:
			return "java.math.BigDecimal";
		case Types.NVARCHAR:
			return "VARCHAR";
			// case Types.OTHER:
			// return "";
			// case Types.REAL:
			// return "";
			// case Types.REF:
			// return "";
			// case Types.ROWID:
			// return "";
		case Types.SMALLINT:
			return "SMALLINT";
			// case Types.SQLXML:
			// return "";
			// case Types.STRUCT:
			// return "";
		case Types.TIME:
			return "TIME";
		case Types.TIMESTAMP:
			return "TIMESTAMP";
		case Types.TINYINT:
			return "INTEGER";
		case Types.VARBINARY:
			return "VARCHAR";
		case Types.VARCHAR:
			return "VARCHAR";
		default:
			return "";
		}
	}
}
