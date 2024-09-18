package Response;

import java.util.List;
import VO.MealDetails;
import VO.UserInfo;

public class ResponseHandle {

	private int id;
	private List<MealDetails> md;
	private String sucessMessage;
	private String FailureMessage;
	private List<UserInfo> user;
	
	//Getter and Setter
	
	public List<UserInfo> getUser() {
		return user;
	}

	public void setUser(List<UserInfo> user) {
		this.user = user;
	}
	public List<MealDetails> getMd() {
		return md;
	}

	public void setmealDetails(List<MealDetails> md) {
		this.md = md;
	}

	public ResponseHandle(){
		
	}

	public String getSucessMessage() {
		return sucessMessage;
	}

	public void setSucessMessage(String sucessMessage) {
		this.sucessMessage = sucessMessage;
	}

	public String getFailureMessage() {
		return FailureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		FailureMessage = failureMessage;
	}

	
}
