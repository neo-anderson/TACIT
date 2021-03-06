package edu.usc.cssl.tacit.common.queryprocess;

public enum QueryOperatorType {
	STRING_EQUALS, STRING_CONTAINS, STRING_STARS_WITH, STRING_ENDS_WITH, INTEGER_GREATER_THAN, INTEGER_LESS_THAN, INTEGER_EQUALS, DOUBLE_EQUALS, DOUBLE_GREATER_THAN, DOUBLE_LESS_THAN;

	@Override
	public String toString() {
		switch (this) {
		case STRING_EQUALS:
			return "equals";
		case STRING_CONTAINS:
			return "contains";
		case STRING_STARS_WITH:
			return "starts with";
		case STRING_ENDS_WITH:
			return "ends with";
		case INTEGER_GREATER_THAN:
			return ">";
		case INTEGER_LESS_THAN:
			return "<";
		case INTEGER_EQUALS:
			return "=";
		case DOUBLE_EQUALS:
			return "=";
		case DOUBLE_GREATER_THAN:
			return ">";
		case DOUBLE_LESS_THAN:
			return "<";
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public static QueryOperatorType getOperatorType(String op) {
		if(op.equals("equals"))
			return STRING_EQUALS;
		else if(op.equals("contains"))
			return STRING_CONTAINS;
		else if(op.equals("starts with"))
			return STRING_STARS_WITH;
		else if(op.equals("ends with"))
			return STRING_ENDS_WITH;
		else if(op.equals(">"))
			return INTEGER_GREATER_THAN;
		else if(op.equals("<"))
			return INTEGER_LESS_THAN;
		else if(op.equals("="))
			return INTEGER_EQUALS;
		else
			return null;
	}

}
