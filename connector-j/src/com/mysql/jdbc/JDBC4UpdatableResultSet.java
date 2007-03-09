/*
 Copyright (C) 2002-2007 MySQL AB

 This program is free software; you can redistribute it and/or modify
 it under the terms of version 2 of the GNU General Public License as 
 published by the Free Software Foundation.

 There are special exceptions to the terms and conditions of the GPL 
 as it is applied to this software. View the full text of the 
 exception in file EXCEPTIONS-CONNECTOR-J in the directory of this 
 software distribution.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package com.mysql.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Field;
import com.mysql.jdbc.NotUpdatable;
import com.mysql.jdbc.RowData;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.UpdatableResultSet;

import com.mysql.jdbc.exceptions.NotYetImplementedException;

public class JDBC4UpdatableResultSet extends UpdatableResultSet {
	public JDBC4UpdatableResultSet(String catalog, Field[] fields, RowData tuples, Connection conn, Statement creatorStmt) throws SQLException {
		super(catalog, fields, tuples, conn, creatorStmt);
		// TODO Auto-generated constructor stub
	}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new NotUpdatable();
		
	}
	
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new NotUpdatable();
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new NotUpdatable();
		
	}


	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		updateNCharacterStream(columnIndex, x, (int) length);
		
	}
	

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new NotUpdatable();
		
	}

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new NotUpdatable();
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new NotUpdatable();
		
	}
	
	
	
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new NotUpdatable();
	}

	/**
	 * JDBC 4.0 Update a column with a character stream value. The updateXXX()
	 * methods are used to update column values in the current row, or the
	 * insert row. The updateXXX() methods do not update the underlying
	 * database, instead the updateRow() or insertRow() methods are called to
	 * update the database.
	 * 
	 * @param columnIndex
	 *            the first column is 1, the second is 2, ...
	 * @param x
	 *            the new column value
	 * @param length
	 *            the length of the stream
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 */
	public synchronized void updateNCharacterStream(int columnIndex,
	        java.io.Reader x, int length) throws SQLException {
	    String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
	    if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
	        throw new SQLException(
	                "Can not call updateNCharacterStream() when field's character set isn't UTF-8");
	    }
	    
	    if (!this.onInsertRow) {
	        if (!this.doingUpdates) {
	            this.doingUpdates = true;
	            syncUpdate();
	        }
	
	        ((com.mysql.jdbc.JDBC4PreparedStatement)this.updater).setNCharacterStream(columnIndex, x, length);
	    } else {
	    	((com.mysql.jdbc.JDBC4PreparedStatement)this.inserter).setNCharacterStream(columnIndex, x, length);
	
	        if (x == null) {
	            this.thisRow[columnIndex - 1] = null;
	        } else {
	            this.thisRow[columnIndex - 1] = STREAM_DATA_MARKER;
	        }
	    }
	}

	/**
	 * JDBC 4.0 Update a column with a character stream value. The updateXXX()
	 * methods are used to update column values in the current row, or the
	 * insert row. The updateXXX() methods do not update the underlying
	 * database, instead the updateRow() or insertRow() methods are called to
	 * update the database.
	 * 
	 * @param columnName
	 *            the name of the column
	 * @param reader
	 *            the new column value
	 * @param length
	 *            of the stream
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 */
	public synchronized void updateNCharacterStream(String columnName,
	        java.io.Reader reader, int length) throws SQLException {
	    updateNCharacterStream(findColumn(columnName), reader, length);
	}

	/**
	 * @see ResultSet#updateNClob(int, NClob)
	 */
	public void updateNClob(int columnIndex, java.sql.NClob nClob)
	        throws SQLException {
	    String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
	    if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
	        throw new SQLException("Can not call updateNClob() when field's character set isn't UTF-8");
	    }
	    
	    if (nClob == null) {
	        updateNull(columnIndex);
	    } else {
	        updateNCharacterStream(columnIndex, nClob.getCharacterStream(),
	                (int) nClob.length());
	    }
	}

	/**
	 * @see ResultSet#updateClob(int, Clob)
	 */
	public void updateNClob(String columnName, java.sql.NClob nClob)
	        throws SQLException {
	    updateNClob(findColumn(columnName), nClob);
	}

	/**
	 * JDBC 4.0 Update a column with NATIONAL CHARACTER. The updateXXX() methods are
	 * used to update column values in the current row, or the insert row. The
	 * updateXXX() methods do not update the underlying database, instead the
	 * updateRow() or insertRow() methods are called to update the database.
	 * 
	 * @param columnIndex
	 *            the first column is 1, the second is 2, ...
	 * @param x
	 *            the new column value
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 */
	public synchronized void updateNString(int columnIndex, String x)
	        throws SQLException {
	    String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
	    if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
	        throw new SQLException("Can not call updateNString() when field's character set isn't UTF-8");
	    }
	    
	    if (!this.onInsertRow) {
	        if (!this.doingUpdates) {
	            this.doingUpdates = true;
	            syncUpdate();
	        }
	
	        ((com.mysql.jdbc.JDBC4PreparedStatement)this.updater).setNString(columnIndex, x);
	    } else {
	    	((com.mysql.jdbc.JDBC4PreparedStatement)this.inserter).setNString(columnIndex, x);
	
	        if (x == null) {
	            this.thisRow[columnIndex - 1] = null;
	        } else {
	            this.thisRow[columnIndex - 1] = StringUtils.getBytes(x,
	                        this.charConverter, fieldEncoding,
	                        this.connection.getServerCharacterEncoding(),
	                        this.connection.parserKnowsUnicode());
	        }
	    }
	}

	/**
	 * JDBC 4.0 Update a column with NATIONAL CHARACTER. The updateXXX() methods are
	 * used to update column values in the current row, or the insert row. The
	 * updateXXX() methods do not update the underlying database, instead the
	 * updateRow() or insertRow() methods are called to update the database.
	 * 
	 * @param columnName
	 *            the name of the column
	 * @param x
	 *            the new column value
	 * 
	 * @exception SQLException
	 *                if a database-access error occurs
	 */
	public synchronized void updateNString(String columnName, String x)
	        throws SQLException {
	    updateNString(findColumn(columnName), x);
	}

	public int getHoldability() throws SQLException {
		throw new NotYetImplementedException();
	}

	/**
	 * JDBC 4.0 Get a NCLOB column.
	 * 
	 * @param columnIndex
	 *            the first column is 1, the second is 2, ...
	 * 
	 * @return an object representing a NCLOB
	 * 
	 * @throws SQLException
	 *             if an error occurs
	 */
	protected java.sql.NClob getNativeNClob(int columnIndex)
			throws SQLException {
		String stringVal = getStringForNClob(columnIndex);
	
		if (stringVal == null) {
			return null;
		}
	
		return getNClobFromString(stringVal, columnIndex);
	}

	/**
	 * JDBC 4.0
	 * 
	 * <p>
	 * Get the value of a column in the current row as a java.io.Reader.
	 * </p>
	 * 
	 * @param columnIndex
	 *            the column to get the value from
	 * 
	 * @return the value in the column as a java.io.Reader.
	 * 
	 * @throws SQLException
	 *             if an error occurs
	 */
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
		if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
			throw new SQLException(
					"Can not call getNCharacterStream() when field's charset isn't UTF-8");
		}
		return getCharacterStream(columnIndex);
	}

	/**
	 * JDBC 4.0
	 * 
	 * <p>
	 * Get the value of a column in the current row as a java.io.Reader.
	 * </p>
	 * 
	 * @param columnName
	 *            the column name to retrieve the value from
	 * 
	 * @return the value as a java.io.Reader
	 * 
	 * @throws SQLException
	 *             if an error occurs
	 */
	public Reader getNCharacterStream(String columnName) throws SQLException {
		return getNCharacterStream(findColumn(columnName));
	}

	/**
	 * JDBC 4.0 Get a NCLOB column.
	 * 
	 * @param i
	 *            the first column is 1, the second is 2, ...
	 * 
	 * @return an object representing a NCLOB
	 * 
	 * @throws SQLException
	 *             if an error occurs
	 */
	public NClob getNClob(int columnIndex) throws SQLException {
		String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
		if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
			throw new SQLException(
					"Can not call getNClob() when field's charset isn't UTF-8");
		}
		if (!this.isBinaryEncoded) {
			String asString = getStringForNClob(columnIndex);
	
			if (asString == null) {
				return null;
			}
	
			return new com.mysql.jdbc.JDBC4NClob(asString);
		}
	
		return getNativeNClob(columnIndex);
	}

	/**
	 * JDBC 4.0 Get a NCLOB column.
	 * 
	 * @param colName
	 *            the column name
	 * 
	 * @return an object representing a NCLOB
	 * 
	 * @throws SQLException
	 *             if an error occurs
	 */
	public NClob getNClob(String columnName) throws SQLException {
		return getNClob(findColumn(columnName));
	}

	private final java.sql.NClob getNClobFromString(String stringVal,
			int columnIndex) throws SQLException {
		return new com.mysql.jdbc.JDBC4NClob(stringVal);
	}

	/**
	 * JDBC 4.0
	 * 
	 * Get the value of a column in the current row as a Java String
	 * 
	 * @param columnIndex
	 *            the first column is 1, the second is 2...
	 * 
	 * @return the column value, null for SQL NULL
	 * 
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public String getNString(int columnIndex) throws SQLException {
		String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
		if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
			throw new SQLException(
					"Can not call getNString() when field's charset isn't UTF-8");
		}
		return getString(columnIndex);
	}

	/**
	 * JDBC 4.0
	 * 
	 * The following routines simply convert the columnName into a columnIndex
	 * and then call the appropriate routine above.
	 * 
	 * @param columnName
	 *            is the SQL name of the column
	 * 
	 * @return the column value
	 * 
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public String getNString(String columnName) throws SQLException {
		return getNString(findColumn(columnName));
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new NotYetImplementedException();
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		return getRowId(findColumn(columnLabel));
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return new JDBC4MysqlSQLXML(this, columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return getSQLXML(findColumn(columnLabel));
	}

	private String getStringForNClob(int columnIndex) throws SQLException {
		String asString = null;
	
		String forcedEncoding = "UTF-8";
	
		try {
			byte[] asBytes = null;
	
			if (!this.isBinaryEncoded) {
				asBytes = getBytes(columnIndex);
			} else {
				asBytes = getNativeBytes(columnIndex, true);
			}
	
			if (asBytes != null) {
				asString = new String(asBytes, forcedEncoding);
			}
		} catch (UnsupportedEncodingException uee) {
			throw SQLError.createSQLException("Unsupported character encoding "
					+ forcedEncoding, SQLError.SQL_STATE_ILLEGAL_ARGUMENT);
		}
	
		return asString;
	}

	public synchronized boolean isClosed() throws SQLException {
		return this.isClosed;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		throw new NotYetImplementedException();
	}

	public Object unwrap(Class arg0) throws SQLException {
		throw new NotYetImplementedException();
	}
}
