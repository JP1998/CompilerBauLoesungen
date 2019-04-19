/* **********************************************
 * Duale Hochschule Baden-Württemberg Karlsruhe
 * Prof. Dr. Jörn Eisenbiegler
 * 
 * Vorlesung Übersetzerbau
 * Praxis X Abstiegsparser
 * - Abstiegsparser
 * 
 * **********************************************
 */

package de.dhbw.compiler.xparser;

public class XParser {

	private TokenReader reader;

	public XParser(TokenReader in) {
		this.reader = in;
	}

	public Tree parseProgram() {
		final Tree rootNode = new Tree(new Token(Token.APROGRAM));
		Token token;

		if((token = reader.nextToken()).getType() == Token.PROGRAM) {
			rootNode.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Unexpected Token. Found '" + token.toString() + "'; expected: 'program'");
		}

		if((token = reader.nextToken()).getType() == Token.ID) {
			rootNode.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Expected identifier, but found token '" + token.getText() + "'");
		}

		if((token = reader.nextToken()).getType() == Token.SEMICOLON) {
			rootNode.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Expected token ';' found '" + token.getText() + "'");
		}

		Tree block;
		if((block = parseBlock()) != null) {
			rootNode.addLastChild(block);
		} else {
			throw new RuntimeException("Expected a block of statements, but couldn't find one.");
		}

		if((token = reader.nextToken()).getType() == Token.DOT) {
			rootNode.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Expected token '.' found '" + token.getText() + "'");
		}

		if((token = reader.nextToken()).getType() == Token.EOF) {
			rootNode.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Unexpected token '" + token.getText() + "' after end of program.");
		}

		return rootNode;
	}

	private Tree parseBlock() {
		final Tree blockRoot = new Tree(new Token(Token.BLOCK));
		final int RESET_POSITION = reader.getPosition();

		Token token;

		if((token = reader.nextToken()).getType() == Token.BEGIN) {
			blockRoot.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		blockRoot.addLastChild(parseStatementList());

		if((token = reader.nextToken()).getType() == Token.END) {
			blockRoot.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		return blockRoot;
	}

	private Tree parseStatementList() {
		final Tree statementlistroot = new Tree(new Token(Token.STATLIST));

		Tree nextStatement;
		while ((nextStatement = tryParseStatementWithSemicolon()) != null) {
			statementlistroot.addLastChild(nextStatement);
		}

		return statementlistroot;
	}

	private Tree tryParseStatementWithSemicolon() {
		final Tree statementWithSemicolonRoot = new Tree(new Token(Token.STATWITHSEMI));
		final int RESET_POSITION= reader.getPosition();

		final Tree statement = tryParseStatement();
		if(statement != null) {
			statementWithSemicolonRoot.addLastChild(statement);
		} else {
			return reset(RESET_POSITION);
		}

		Token semicolonToken = reader.nextToken();
		if(semicolonToken.getType() == Token.SEMICOLON) {
			statementWithSemicolonRoot.addLastChild(new Tree(semicolonToken));
		} else {
			throw new RuntimeException("Unexpected token '" + semicolonToken.getText() + "'; expected: ';'");
		}

		return statementWithSemicolonRoot;
	}

	private Tree tryParseStatement() {
		final Tree statementRoot = new Tree(new Token(Token.STAT));
		final int RESET_POSITION = reader.getPosition();

		Tree actualStatement = parseBlock();
		if(actualStatement != null) {
			statementRoot.addLastChild(actualStatement);
			return statementRoot;
		}

		actualStatement = parseConditionalStatement();
		if(actualStatement != null) {
			statementRoot.addLastChild(actualStatement);
			return statementRoot;
		}

		actualStatement = parseNumericAssignment();
		if(actualStatement != null) {
			statementRoot.addLastChild(actualStatement);
			return statementRoot;
		}

		return reset(RESET_POSITION);
	}

	private Tree parseConditionalStatement() {
		final Tree conditionalStatementTree = new Tree(new Token(Token.CONDSTAT));
		final int RESET_POSITION = reader.getPosition();

		Token token;
		if((token = reader.nextToken()).getType() == Token.IF) {
			conditionalStatementTree.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		Tree condition = parseCondition();
		if(condition != null) {
			condition.addLastChild(condition);
		} else {
			throw new RuntimeException("Expected condition, but couldn't find one.");
		}

		if((token = reader.nextToken()).getType() == Token.THEN) {
			conditionalStatementTree.addLastChild(new Tree(token));
		} else {
			throw new RuntimeException("Expected token 'then', but found '" + token.getText() + "' instead.");
		}

		Tree ifcontent = tryParseStatement();
		if(ifcontent != null) {
			conditionalStatementTree.addLastChild(ifcontent);
		} else {
			throw new RuntimeException("Expected a statement but couldn't find one.");
		}

		if((token = reader.nextToken()).getType() == Token.ELSE) {
			conditionalStatementTree.addLastChild(new Tree(token));

			Tree elsecontent = tryParseStatement();
			if(elsecontent != null) {
				conditionalStatementTree.addLastChild(elsecontent);
			} else {
				throw new RuntimeException("Expected a statement but couldn't find one.");
			}
		}

		return conditionalStatementTree;
	}

	private Tree parseCondition() {
		final Tree conditionTree = new Tree(new Token(Token.COND));

		Tree firstNumericalExpression = parseNumericExpression();
		if(firstNumericalExpression != null) {
			conditionTree.addLastChild(firstNumericalExpression);
		} else {
			throw new RuntimeException("Expected numeric expression but couldn't find one.");
		}

		Token comparison = reader.nextToken();
		final int comptype = comparison.getType();
		if(comptype == Token.LESS || comptype == Token.MORE || comptype == Token.EQUALS) {
			conditionTree.addLastChild(new Tree(comparison));
		} else {
			throw new RuntimeException("Expected comparison operator. Found '" + comparison.getText() + "'");
		}

		Tree secondNumericalExpression = parseNumericExpression();
		if(secondNumericalExpression != null) {
			conditionTree.addLastChild(secondNumericalExpression);
		} else {
			throw new RuntimeException("Expected numeric expression but couldn't find one.");
		}

		return conditionTree;
	}

	private Tree parseNumericExpression() {
		final Tree numericExpressionRoot = new Tree(new Token(Token.EXPR));
		final int RESET_POSITION = reader.getPosition();

		Token token = reader.nextToken();
		switch (token.getType()) {
			case Token.ID:
				numericExpressionRoot.addLastChild(new Tree(token));
				break;
			case Token.LBR:
				numericExpressionRoot.addLastChild(new Tree(token));

				Tree innerExpression = parseNumericExpression();
				if(innerExpression != null) {
					numericExpressionRoot.addLastChild(innerExpression);
				} else {
					return reset(RESET_POSITION);
				}

				if((token = reader.nextToken()).getType() == Token.RBR) {
					numericExpressionRoot.addLastChild(new Tree(token));
				} else {
					throw new RuntimeException("Expected token ')' but found '" + token.getText() + "'");
				}
				break;
			case Token.INTCONST: // TODO: First look for further expression!
				// TODO: Find list of all the tokens to put into the expression and parse them accordingly.
				numericExpressionRoot.addLastChild(new Tree(token));
				break;
		}

		return numericExpressionRoot;
	}

	private Tree parseNumericAssignment() {
		final Tree numericAssignmentRootTree = new Tree(new Token(Token.ASSIGNSTAT));
		final int RESET_POSITION = reader.getPosition();

		Token token;
		if((token = reader.nextToken()).getType() == Token.ID) {
			numericAssignmentRootTree.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		if((token = reader.nextToken()).getType() == Token.COLON) {
			numericAssignmentRootTree.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		if((token = reader.nextToken()).getType() == Token.EQUALS) {
			numericAssignmentRootTree.addLastChild(new Tree(token));
		} else {
			return reset(RESET_POSITION);
		}

		Tree numericExpression = parseNumericExpression();
		if(numericExpression != null) {
			numericAssignmentRootTree.addLastChild(numericExpression);
		} else {
			return reset(RESET_POSITION);
		}

		return numericAssignmentRootTree;
	}

	private Tree reset(int pos) {
		this.reader.setPosition(pos);
		return null;
	}

}
