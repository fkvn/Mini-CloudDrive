package webtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import webtest.models.File;
import webtest.models.User;

public class DbUtils {

	private static String host = "";
	private static String database = "";

	private static String username = "";
	private static String password = "";
	
	private static String url = "";
	
	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		DbUtils.host = host;
	}

	public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		DbUtils.database = database;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		DbUtils.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DbUtils.password = password;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DbUtils.url = url;
	}
	
	
	Connection connection;

	public static void setUpDatabase(String host, String username, String password, String database) {
		DbUtils.setHost(host);
		DbUtils.setDatabase(database);
		DbUtils.setPassword(password);
		DbUtils.setUsername(username);
		DbUtils.setUrl("jdbc:mysql://" + host + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true");
	}
	
	public DbUtils()
	{
		try
		{
			connection = DriverManager.getConnection( url, username, password );
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			if( connection != null ) connection.close();
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}
	}
	
	public boolean createTables() {
		try {
			String[] sql = 	{"drop table if exists users;", 
								"SET SQL_SAFE_UPDATES = 0;", 
								"create table users (" + 
									"	id int auto_increment primary key, " + 
									"    name varchar(255), " + 
									"    password varchar(255) " + 
									");",
								"drop table if exists files;",
								"create table files (" + 
									"    id          integer auto_increment primary key, " + 
									"    name        varchar(255), " + 
									"    type varchar(255), " + 
									"    is_folder   boolean default false, " + 
									"    parent_id   integer default 0 not null , " + 
									"    foreign key fk_parentId(parent_id) references files(id) on delete cascade, " + 
									"    owner_id    integer references users(id), " + 
									"	data   longblob default null, " + 
									"    md5 varchar(255) " + 
									");",
								"CREATE UNIQUE INDEX idx_parentId_md5 " + 
										"ON files (owner_id, parent_id, md5);",
								"insert into files values (0, 'Top Folder', 'Folder', true, 1, 1, null, null);",
							};
			PreparedStatement pstmt = null;
			
			for (int i = 0; i < sql.length; i++) {
				pstmt = connection.prepareStatement(sql[i]);
				pstmt.executeUpdate();
			}
			return true;
					
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}
		return false;
	}

	public User authorizeUser(String user_name, String pass_word) {
		User user = null;

		try {
			String getUser = "select md5(id) as id, name from users where name = ? and password = MD5(?)";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, user_name);
			pstmt.setString(2, pass_word);

			ResultSet rs = pstmt.executeQuery( );

			if (rs.next())
				user = new User(rs.getString("id"), rs.getString("name"));				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isDuplicateUser(String user_name) {
		try {
			String getUser = "select * from users where name = ?";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, user_name);

			ResultSet rs = pstmt.executeQuery( );

			if (rs.next())
				return true;				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User addUser(String user_name, String password) {
		User user = null;

		try {
			String addUser = "insert into users (name, password) values (?, MD5(?));";

			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, user_name);
			pstmt.setString(2, password);

			pstmt.executeUpdate();
			int id = 0;

			ResultSet rs = pstmt.getGeneratedKeys();
			if( rs.next() )  id = rs.getInt( 1 );

			String getUser = "select md5(id) as id, name from users where id = ?";

			pstmt = connection.prepareStatement(getUser);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery( );

			if(rs.next())
				user = new User(rs.getString("id"), rs.getString("name"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public User getUserByName(String user_name) {
		User user = null;
		
		try {
			String getUser = "select md5(id) as id, name from users where name = ?";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, user_name);

			ResultSet rs = pstmt.executeQuery( );
			
			if (rs.next())
				user = new User(rs.getString("id"),rs.getString("name") );
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUserById(String id) {
		User user = null;
		
		try {
			String getUser = "select md5(id) as id, name from users where md5(id) = ?";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery( );
			
			if (rs.next())
				user = new User(rs.getString("id"),rs.getString("name") );
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User recoverPassword(String id, String password) {
		User user = null;
		
		try {
			String updatePassword = "update users set password = md5(?) where md5(id) = ?";

			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement(updatePassword);

			pstmt.setString(1, password);
			pstmt.setString(2, id);

			pstmt.executeUpdate();
			
			user = this.getUserById(id);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public List<File> getTopLevelFiles(String owner_id) {
		List<File> files  = new ArrayList<>();

		try {
			String getUser = "select md5(id) as id, name, type, is_folder, md5(parent_id) as parent_id "
					+ "from files "
					+ "where parent_id = 1 and md5(owner_id)  = ? and id > 1 "
					+ "order by is_folder desc, name;";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, owner_id);

			ResultSet rs = pstmt.executeQuery( );

			while (rs.next())
				files.add(new File(rs.getString("id"), rs.getString("name"),rs.getString("type"), rs.getBoolean("is_folder"), rs.getString("parent_id"), owner_id));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return files;
	}
	
	public List<File> getSubFiles(String id, String owner_id) {
		List<File> files  = new ArrayList<>();

		try {
			String getUser = "select md5(id) as id, name, type, is_folder, md5(parent_id) as parent_id "
					+ "from files "
					+ "where md5(parent_id) = ? and md5(owner_id) = ? "
					+ "order by is_folder desc, name;";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, id);
			pstmt.setString(2, owner_id);

			ResultSet rs = pstmt.executeQuery( );

			while (rs.next())
				files.add(new File(rs.getString("id"), rs.getString("name"),rs.getString("type"), rs.getBoolean("is_folder"), rs.getString("parent_id"), owner_id));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return files;
	}
	
	public List<File> getSearchFile(String name, String owner_id) {
		List<File> files  = new ArrayList<>();

		try {
			String getUser = "select md5(id) as id, name, type, is_folder, "
					+ "md5(parent_id) as parent_id, md5(owner_id) as owner_id "
					+ "from files"
					+ " where name like '%" + name + "%' and md5(owner_id) = ? and id > 1 "
					+ "order by is_folder desc, name;";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, owner_id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next())
				files.add(new File(rs.getString("id"), rs.getString("name"),rs.getString("type"), rs.getBoolean("is_folder"), 
						rs.getString("parent_id"), rs.getString("owner_id")));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return files;
	}
	
	public String addNewFolder(File file) {
		try {
			String getUserId = "select id from users where md5(id) = ?";
			int userId = 0;
			PreparedStatement pstm = connection.prepareStatement(getUserId);

			pstm.setString(1, file.getOwner_id());
			ResultSet rs = pstm.executeQuery();
			
			if(rs.next()) userId = rs.getInt("id");
			
			int parent_id = 1;
			System.out.println(!file.getParent_id().equals("1"));
			if (!file.getParent_id().equals("1"))
			{
				System.out.println(file.getParent_id());
				String getFileId = "select id from files where md5(id) = ?";
				pstm = connection.prepareStatement(getFileId);

				pstm.setString(1, file.getParent_id());
				rs = pstm.executeQuery();
				
				if(rs.next()) {
					System.out.println(file.getParent_id());
					parent_id = rs.getInt("id");
				}
			}
			
			System.out.println(parent_id);
			
			String sql = "insert into files (id, name, type, is_folder, parent_id, owner_id)  values (?,?,?,?,?,?)";
			pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );

			pstm.setInt(1, 0);
			pstm.setString(2, file.getName());
			pstm.setString(3, file.getType());

			pstm.setBoolean(4, file.isFolder());
			pstm.setInt(5, parent_id);	
			pstm.setInt(6, userId);
			pstm.executeUpdate();

			return "Successful Created Folder";

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return "Error Create Folder";
	}
	

	public File getFile(String id) {
		File file = null;
		try {
			String getUser = "select md5(id) as id, name, type, md5(parent_id) as parent_id, md5(owner_id) as owner_id, "
					+ "is_folder, data from files where md5(id) = ?;";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery( );

			while (rs.next())
			{
				file = new File();
				file.setId(rs.getString("id"));
				file.setName(rs.getString("name"));
				file.setType(rs.getString("type"));
				file.setParent_id(rs.getString("parent_id"));
				file.setOwner_id(rs.getString("owner_id"));
				file.setFolder(rs.getBoolean("is_folder"));
				file.setFileData(rs.getBytes("data"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return file;
	}
	
	public boolean isTopFile(String id) {
		try {
			String getUser = "select * from files where md5(id) = ? and parent_id = 1;";

			PreparedStatement pstmt = connection.prepareStatement(getUser);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery( );

			if (rs.next()) return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String upDateFileName(String id, String name) {
		try {
			String sql = "update files set name = ? where md5(id) = ?";
			PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );

			pstm.setString(1, name);
			pstm.setString(2, id);

			pstm.executeUpdate();

			return "Successful Updated File Name";

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return "Update name failed";
	}


	public String deleteFile(String id) {
		try {
			String sql = "delete from files  where md5(id) = ?";
			PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );

			pstm.setString(1, id);

			pstm.executeUpdate();

			return "Successful Deleted File";

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return "Delete name failed";
	}


	public String uploadFile(File file, InputStream data) throws SQLException, NoSuchAlgorithmException, IOException {
		String message = "";
		String sql;
		PreparedStatement pstm;
		ResultSet rs;
		int id = 0;
		try {
			
			String getParent_id = "select id from files where md5(id) = ?";
			int parent_id = 1;
			pstm = connection.prepareStatement(getParent_id);
			pstm.setString(1, file.getParent_id());
			rs = pstm.executeQuery();
			System.out.println("file db: " + file.getParent_id());
			if(rs.next()) {
				System.out.println("rs db: " + rs.getInt("id"));
				parent_id = rs.getInt("id");
			}
			
			System.out.println("Upload db: " + parent_id);
			
			String getOwnerId = "select id from users where md5(id) = ?";
			int owner_id = 1;
			pstm = connection.prepareStatement(getOwnerId);
			pstm.setString(1, file.getOwner_id());
			rs = pstm.executeQuery();
			
			if(rs.next()) owner_id = rs.getInt("id");
			
			sql = "insert into files values (?, ?,?,?,?,?,?, ?)";
			pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );

			pstm.setInt(1, 0);
			pstm.setString(2, file.getName());
			pstm.setString(3, file.getType());


			pstm.setBoolean(4, file.isFolder());
			pstm.setInt(5, parent_id);	
			pstm.setInt(6, owner_id);
			pstm.setBlob(7, data);
			pstm.setString(8, "");
			pstm.executeUpdate();
			
			System.out.println("parent_id: " + parent_id);
			System.out.println("onwer_id: " + owner_id);

			rs = pstm.getGeneratedKeys();
			if( rs.next() )  id = rs.getInt( 1 );
			
			sql = "update files f1, files f2 set f1.md5  =  Md5(f2.data) where f1.id = f2.id and f1.id = ?;";
			pstm = connection.prepareStatement(sql);
			pstm.setInt(1, id);
			
			pstm.executeUpdate();

			message =  "Successful Uploaded File";
			data.close();

		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1062)
			{
				sql = "delete from files where id = ?;";
				pstm = connection.prepareStatement(sql);
				pstm.setInt(1, id);
				pstm.executeUpdate();
				message = "Duplicate File";
			}
			else 
				message = e.getMessage();
			e.printStackTrace();
		}
		return message;
	} 

	public InputStream downloadFile(String id, String owner_id) {
		InputStream inputStream = null;
		System.out.println(owner_id);
		try {
			String sql = "";
			PreparedStatement statement = null;

			sql = "SELECT * FROM files WHERE md5(id) = ? and md5(owner_id) = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.setString(2, owner_id);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				java.sql.Blob blob = result.getBlob("data");
				inputStream = blob.getBinaryStream();
				return inputStream;     
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		
		return inputStream;
	}



}

