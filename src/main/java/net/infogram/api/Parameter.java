package net.infogram.api;

/**
 * Represents a request parameter - a key/value pair, comparable by key.
 */
class Parameter implements Comparable<Parameter> {

	public final String key;
	public final String value;

	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int compareTo(Parameter other) {
		// implement ordering by keys
		return key.compareTo(other.key);
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Parameter) return key.equals( ((Parameter)other).key );
		return false;
	}
}
