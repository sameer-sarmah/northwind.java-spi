package api;

import javax.sql.DataSource;

public interface IDataSourceProvider {
	public DataSource dataSource();
}
