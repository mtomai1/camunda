package it.reply.poc.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskState {

	private String id;
	private String name;
	private String assignee;
	private String created;
	private String executionId;
	private String owner;
	private String processDefinitionId;
	private String processInstanceId;
	private String taskDefinitionKey;

	@Override
	public String toString() {
		return "TaskState{" +
										"id='" + id + '\'' +
										", name='" + name + '\'' +
										", assignee='" + assignee + '\'' +
										", created='" + created + '\'' +
										", executionId='" + executionId + '\'' +
										", owner='" + owner + '\'' +
										", processDefinitionId='" + processDefinitionId + '\'' +
										", processInstanceId='" + processInstanceId + '\'' +
										", taskDefinitionKey='" + taskDefinitionKey + '\'' +
										'}';
	}
}
