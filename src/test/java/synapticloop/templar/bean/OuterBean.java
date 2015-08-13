package synapticloop.templar.bean;

public class OuterBean {
	private String name = "outerBean";
	private InnerBean innerBean = new InnerBean();

	public OuterBean() {
	}
	
	public InnerBean getInnerBean() { return(innerBean); }
	public String getName() { return(name); }
	public boolean isTrue() { return(true); }
	public boolean isFalse() { return(false); }
	public Object getNull() { return(null); }
	public Object getNotNull() {return("not null"); }
}

