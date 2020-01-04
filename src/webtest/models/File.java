package webtest.models;

public class File {
	private String id;
	private String name;
	private boolean isFolder;
	private String type;
	private String parent_id;
	private String owner_id;
	private byte[] fileData;

	public File () {};
	public File(String id, String name, String type, boolean isFolder, String parent_id, String owner_id) {
		this.id = id;
		this.name = name;
		this.isFolder = isFolder;
		this.parent_id = parent_id;
		this.owner_id = owner_id;
		this.type = type;
		this.fileData = null;
	}

	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
