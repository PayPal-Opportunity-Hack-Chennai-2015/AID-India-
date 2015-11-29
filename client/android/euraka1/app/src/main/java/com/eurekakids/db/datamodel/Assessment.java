package com.eurekakids.db.datamodel;

public class Assessment {
	private int student_id;
	private int skill_id;
	private int is_completed;

	public Assessment(int s_id,int skill_id, int is_completed){
		this.student_id = s_id;
		this.skill_id = skill_id;
		this.is_completed = is_completed;
	}

	public Assessment(){}

	public int getStudentId() {
		return student_id;
	}
	public void setStudentId(int studentId) {
		this.student_id = studentId;
	}
	public int getSkillId() {
		return skill_id;
	}
	public void setSkillId(int skillId) {
		this.skill_id = skillId;
	}
	public int getIsCompleted() {
		return is_completed;
	}
	public void setIsCompleted(int isCompleted) {
		this.is_completed = isCompleted;
	}
	
}
