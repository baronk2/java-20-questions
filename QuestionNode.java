/*
Kevin Baron
4/24/13
CSE 143 Assignment #7
Question Node
*/

public class QuestionNode {
	
	String qOrA;//text which could either be a question or an answer
	QuestionNode left;
	QuestionNode right;
	
	//post: a QuestionNode leaf has been created
	public QuestionNode(String qOrA) {
		this(qOrA, null, null);
	}//eo QuestionNode constructor
	
	//post: a QuestionNode has been created with appropriate left and right subtree references
	public QuestionNode(String qOrA, QuestionNode left, QuestionNode right) {
		this.qOrA = qOrA;
		this.left = left;
		this.right = right;
	}//eo QuestionNode constructor
	
}//eo QuestionNode class