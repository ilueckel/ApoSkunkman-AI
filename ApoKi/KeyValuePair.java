/*
 * This code is licensed under the Ms-PL (http://www.microsoft.com/en-us/openness/licenses.aspx#MPL) by Igor Lueckel
 */

public class KeyValuePair implements Comparable<KeyValuePair> {
	
	@Override
	public int compareTo(KeyValuePair arg0) {
		if (arg0 instanceof KeyValuePair && arg0.Value instanceof Integer && this.Value instanceof Integer){
			return (Integer)Value-(Integer)arg0.Value;
		}
		return 0;
	}
	
	@Override 
	 public boolean equals(Object arg0 ) 
	 {
		 if (arg0==null||Key==null||Value==null||Other==null)
			 return false;
		 if ( !(arg0 instanceof KeyValuePair) ) return false;
		 KeyValuePair temp=(KeyValuePair)arg0;
		 if (Key.equals(temp.Key) && Value.equals(temp.Value) && Other.equals(temp.Other))
			 return true;
		 return false;
	 }
	
	@Override
	public String toString(){
		return "["+Key.toString()+", "+Value.toString()+", "+Other.toString()+"]";
	}
	
	public KeyValuePair(){
		Key=null;
		Value=null;
		Other=null;
	}
	
	public KeyValuePair(Object _Key, Object _Value){
		Key=_Key;
		Value=_Value;
		Other=null;
	}
	
	public KeyValuePair(Object _Key, Object _Value, Object _Other){
		Key=_Key;
		Value=_Value;
		Other=_Other;
	}
	
	public Object Key;
	public Object Value;
	public Object Other;
}
