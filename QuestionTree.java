/*
Kevin Baron
4/24/13
CSE 143 Assignment #7
Question Tree
*/

import java.util.Scanner;//for console
import java.io.PrintStream;//for file-writing

public class QuestionTree {
	
	private QuestionNode overallRoot;
	private Scanner console;//for user input
	
	//post: 
	public QuestionTree() {
		//default tree is a single-leaf tree with a "computer" answer node
		overallRoot = new QuestionNode("computer");
		console = new Scanner(System.in);
	}//eo QuestionTree constructor
	
	//pre : the given Scanner uses a legal, standard-format File with at least one answer
	//post: overallRoot has been set as a copy of the input source
	public void read(Scanner input) {
		overallRoot = read1(input);
	}//eo read
	
	//pre : the given Scanner uses a legal, standard-format File with at least one answer
	//post: a root with all appropriate subtrees (possibly none) has been returned
	private QuestionNode read1(Scanner input) {
		String qA = input.nextLine();//either Q: or A:
		//the root's qOrA field must be initialized with something with a question or an answer
		QuestionNode root = new QuestionNode(input.nextLine());
		//recursive case: if this root is a question, there must be more to read from input,
		//so continue recursing into this root's left and right subtrees
		if (qA.equals("Q:")) {
			root.left = read1(input);
			root.right = read1(input);
		}//eo if
		return root;
	}//eo read1
	
	//pre : the given PrintStream refers to an existing File
	//post: this entire QuestionTree has been printed to the PrintStream in legal, standard format
	public void write(PrintStream output) {
		write(output, overallRoot);
	}//eo write(PrintStream)
	
	//pre : the given PrintStream refers to an existing File, the given QuestionNode is not null
	//post: root and all subtrees of root have been printed to the PrintStream in legal, standard format
	private void write(PrintStream output, QuestionNode root) {
		//base case: an answer leaf has been found.
		//testing root.left rather than root.right is arbitrary. because of the way that this
		//QuestionTree is written, if one side is null, then the other side is also guaranteed to be null
		if (root.left == null)
			output.print("A:\n" + root.qOrA + "\n");
		//recursive case: a question has been found. print the question
		//and continue by calling write on its left and right subtrees
		else {
			output.print("Q:\n" + root.qOrA + "\n");
			write(output, root.left);
			write(output, root.right);
		}//eo else
	}//eo write(PrintStream, QuestionNode)
	
	//pre : this QuestionTree's overallRoot is not null
	//post: overallRoot has possibly been modified to include a new question and a new answer
	public void askQuestions() {
		overallRoot = askQuestions(overallRoot);
	}//eo askQuestions()
	
	//pre : the given QuestionNode is not null
	//post: given root has been returned unchanged or a new QuestionNode has been returned which includes
	//      a user-provided question and answer and a reference to the current root
	private QuestionNode askQuestions(QuestionNode root) {
		//base case: an answer leaf has been found
		if (root.left == null) {
			//the answer is correct. print this fact and return the answer unchanged
			if (yesTo("Would your object happen to be " + root.qOrA + "?")) {
				System.out.println("Great, I got it right!");
				return root;
			}//eo if
			//the answer is incorrect. replace this leaf with a branch (a new question) which has
			//two leaves (a new answer and the previously incorrect answer)
			System.out.print("What is the name of your object? ");
			QuestionNode object = new QuestionNode(console.nextLine());//a new answer leaf QuestionNode
			System.out.println("Please give me a yes/no question that");
			System.out.println("distinguishes between your object");
			System.out.print("and mine--> ");
			String question = console.nextLine();
			//return one of two possible orientations of the old answer and new answer
			//attached to a new question branch
			if (yesTo("And what is the answer for your object?"))
				return new QuestionNode(question, object, root);
			return new QuestionNode(question, root, object);
		}//eo if
		//recursive case: ask another question and continue to the left or right subtree depending on the response.
		//afterwards, return this root, which may or may not have been modified by subsequent calls on askQuestions
		if (yesTo(root.qOrA))
			root.left = askQuestions(root.left);
		else
			root.right = askQuestions(root.right);
		return root;
	}//eo askQuestions(QuestionNode)
	
	// post: asks the user a question, forcing an answer of "y" or "n";
	//       returns true if the answer was yes, returns false otherwise
	public boolean yesTo(String prompt) {
		for (;;) {
			System.out.print(prompt + " (y/n)? ");
			String response = console.nextLine().trim().toLowerCase();
			if (response.equals("y"))
				return true;
			else if (response.equals("n"))
				return false;
			else
				System.out.println("Please answer y or n.");
		}
	}
	
}//eo QuestionTree class