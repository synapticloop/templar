package synapticloop.templar.bean;

public class InnerBean {
	private String name = "innerBean";
	public String getName() {
		return(name);
	}

	public boolean isTrue() { return(true); }
	public boolean isFalse() { return(false); }
	public Object getNull() { return(null); }
	public Object getNotNull() {return("not null"); }
}
