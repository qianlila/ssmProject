package library.book;

public class Book {
	private String bId;
	private String bName;
	private int bNum;
	private int limitDay;
	private boolean existReturn;
	@Override
	public String toString() {
		return bId + "," + bName + "," + bNum + "," + limitDay + ","
				+ existReturn ;
	}
	public Book() {super();}
	public Book(String bId,String bName,int bNum,int limitDay,boolean existReturn) {
		this.bId = bId;
		this.bName = bName;
		this.bNum = bNum;
		this.limitDay = limitDay;
		this.existReturn = existReturn;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	public int getlimitDay() {
		return limitDay;
	}
	public void setlimitDay(int limitDay) {
		this.limitDay = limitDay;
	}
	public boolean existReturn() {
		return existReturn;
	}
	public void setReturn(boolean existReturn) {
		this.existReturn = existReturn;
	}
	
}
