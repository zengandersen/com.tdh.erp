package com.tdh.pagination;

import java.sql.Types;

/**
 * @author wwq
 */
public class OracleDialect extends Dialect {

	public String getLimitString(String sql, int offset, int limit) {

		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from (");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ > "+offset+" and rownum_ <= "+(offset + limit));

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		
		return pagingSelect.toString();
	}
	
	public String getSelectClauseNullString(int sqlType) {
		switch(sqlType) {
			case Types.VARCHAR:
			case Types.CHAR:
				return "to_char(null)";
			case Types.DATE:
			case Types.TIMESTAMP:
			case Types.TIME:
				return "to_date(null)";
			default:
				return "to_number(null)";
		}
	}
	public String getCurrentTimestampSelectString() {
		return "select sysdate from dual";
	}
	public String getCurrentTimestampSQLFunctionName() {
		return "sysdate";
	}
}

