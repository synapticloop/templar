package synapticloop.templar.bean;

import java.util.ArrayList;

public class EvaluationBean {
	private String name;
	private boolean isAlive = false;
	private int age = 0;
	private long timeBorn = 0;
	private EvaluationChildBean evaluationChildBean = null;
	private ArrayList<String> ArrayList = new ArrayList<String>();

	private ArrayList<EvaluationChildBean> childBeans = new ArrayList<EvaluationChildBean>();

	public EvaluationBean() {

	}

	public EvaluationBean(String name, boolean isAlive, int age, long timeBorn, EvaluationChildBean evaluationChildBean) {
		this.name = name;
		this.isAlive = isAlive;
		this.age = age;
		this.timeBorn = timeBorn;
		this.setEvaluationChildBean(evaluationChildBean);
		ArrayList.add("one");
		ArrayList.add("two");
		ArrayList.add("three");
		ArrayList.add("four");
		ArrayList.add("5");
		ArrayList.add("vi");

		childBeans.add(new EvaluationChildBean("jose"));
		childBeans.add(new EvaluationChildBean("hose b"));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setTimeBorn(long timeBorn) {
		this.timeBorn = timeBorn;
	}

	public long getTimeBorn() {
		return timeBorn;
	}

	public boolean getIsOfAge() {
		return(age >= 18);
	}
	public void setEvaluationChildBean(EvaluationChildBean evaluationChildBean) {
		this.evaluationChildBean = evaluationChildBean;
	}

	public EvaluationChildBean getEvaluationChildBean() {
		return evaluationChildBean;
	}

	public void setArrayList(ArrayList<String> ArrayList) {
		this.ArrayList = ArrayList;
	}

	public ArrayList<String> getArrayList() {
		return ArrayList;
	}

	public void setChildBeans(ArrayList<EvaluationChildBean> childBeans) {
		this.childBeans = childBeans;
	}

	public ArrayList<EvaluationChildBean> getChildBeans() {
		return childBeans;
	}
}
