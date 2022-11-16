package utils;

public class InfoFile {
	public int idFileString;
	public String nameFileString;
	public byte[] data;
	
	public InfoFile(int idFileString, String nameFileString, byte[] data) {
		super();
		this.idFileString = idFileString;
		this.nameFileString = nameFileString;
		this.data = data;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
