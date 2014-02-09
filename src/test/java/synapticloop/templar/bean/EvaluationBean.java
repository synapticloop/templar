package synapticloop.templar.bean;

import java.util.Vector;

public class EvaluationBean {
	private String name;
	private boolean isAlive = false;
	private int age = 0;
	private long timeBorn = 0;
	private EvaluationChildBean evaluationChildBean = null;
	private Vector<String> vector = new Vector<String>();

	private Vector<EvaluationChildBean> childBeans = new Vector<EvaluationChildBean>();

	public EvaluationBean() {

	}

	public EvaluationBean(String name, boolean isAlive, int age, long timeBorn, EvaluationChildBean evaluationChildBean) {
		this.name = name;
		this.isAlive = isAlive;
		this.age = age;
		this.timeBorn = timeBorn;
		this.setEvaluationChildBean(evaluationChildBean);
		vector.add("one");
		vector.add("two");
		vector.add("three");
		vector.add("four");
		vector.add("5");
		vector.add("vi");

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

	public void setVector(Vector<String> vector) {
		this.vector = vector;
	}

	public Vector<String> getVector() {
		return vector;
	}

	public void setChildBeans(Vector<EvaluationChildBean> childBeans) {
		this.childBeans = childBeans;
	}

	public Vector<EvaluationChildBean> getChildBeans() {
		return childBeans;
	}
}
