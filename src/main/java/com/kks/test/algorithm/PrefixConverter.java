package com.kks.test.algorithm;

import java.util.Stack;

public class PrefixConverter {

	private Stack<Character> out;
	private Stack<Character> stack;
	
	private void init() {
		this.out = new Stack<>();
		this.stack = new Stack<>();
		
	}
	
	public void flushUntilSlower(char ch, char escape) {
		while(!this.stack.isEmpty()) {
			char ch2 = this.stack.peek();
			if(ch2 == escape) {
				break;
			}
			
			if(getOperatorRank(ch2) > getOperatorRank(ch)) {
				this.out.push(this.stack.pop());
			} else {
				break;
			}
		}
	}
	
	public void flush() {
		while(!this.stack.isEmpty()) {
			char ch = this.stack.pop();
			this.out.push(ch);
		}
	}
	
	public void flushUntil(char escape) {
		boolean hasMetEscape = false;
		while(!this.stack.isEmpty()) {
			char ch = this.stack.pop();
			if(ch == escape) {
				hasMetEscape = true;
				break;
			}
			this.out.push(ch);
		}
		
		if(!hasMetEscape) {
			throw new RuntimeException("'" + escape + "' not found in the stack");
		}
	}
	
	public boolean isOperand(char ch) {
		switch (ch) {
		case '+':
			return false;
		case '-':
			return false;
		case '*':
			return false;
		case '/':
			return false;
		case '(':
			return false;
		case ')':
			return false;
		default:
			return true;
		}
	}
	
	public int getOperatorRank(char operator) {
		switch (operator) {
		case '+':
		case '-':
			return 0;
		case '*':
		case '/':
			return 1;
		case '(':
		case ')':
			return 2;
		default:
			throw new RuntimeException("Not support such operator '" + operator + "'");
		}
	}
	
	/**
	 * hasNext 이런거 있는 인터페이스가 있을것임.. 찾아서 임플..
	 * @return
	 */
	private class ReverseReader {
		private String buffer;
		private int curIndex;
		
		public ReverseReader(String buffer) {
			this.buffer = buffer;
			this.curIndex = buffer.length();
		}
		
		public boolean hasNext() {
			if(curIndex > 0) {
				return true;
			}
			return false;
		}
		
		public char getNext() {
			this.curIndex--;
			return this.buffer.charAt(this.curIndex);
		}
	}
	
	public String convert(String infix) {
		this.init();
		
		ReverseReader rr = new ReverseReader(infix);
		while(rr.hasNext()) {
			char ch = rr.getNext();
			if(isOperand(ch)) {
				out.push(ch);
			} else {  //연산자
				if(ch == '(') {  //특수연산자 '(' 처리
					this.flushUntil(')');
				} else {  //일반연산자 처리
					if(stack.isEmpty()) {
						stack.push(ch);
					} else {
						flushUntilSlower(ch, ')');
						stack.push(ch);
					}
					
				}
			}
		}
		
		//입력 버퍼 처리가 끝난 후 처리
		flush();
		char[] prefix = new char[this.out.size()];
		int i=0;
		while(!this.out.isEmpty()) {
			prefix[i] = this.out.pop();
			i++;
		}
		
		return new String(prefix);
	}
	
	public static void main(String[] args) {
		String infix1 = "(1+3*4+5)*3";
		String prefix1 = "*++1*3453";
		
		String infix2 = "1+2*3";
		String prefix2 = "+1*23";
		
		String infix3 = "(1+2+3*4)*3";
		String prefix3 = "*++12*343";
		
		PrefixConverter converter = new PrefixConverter();
		String convResult1 = converter.convert(infix1);
		String convResult2 = converter.convert(infix2);
		String convResult3 = converter.convert(infix3);
		
		System.out.println(String.format("infix: %s, prefix: %s, conv-result: %s", infix1, prefix1, convResult1));
		System.out.println(String.format("infix: %s, prefix: %s, conv-result: %s", infix2, prefix2, convResult2));
		System.out.println(String.format("infix: %s, prefix: %s, conv-result: %s", infix3, prefix3, convResult3));
	}

}
