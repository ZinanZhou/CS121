public class Assertion {
    private Object obj;

    public Assertion(Object o){
        this.obj = o;
    }
    /* You'll need to change the return type of the assertThat methods */
    static Assertion assertThat(Object o) {
        return new Assertion(o);
    }
    static Assertion assertThat(String s) {
        return new Assertion((String) s);
    }
    static Assertion assertThat(boolean b) {
        return new Assertion(b);
    }
    static Assertion assertThat(int i) {
        return new Assertion(Integer.valueOf(i));
    }

    public Assertion isNull(){
        if(this.obj==null){
            return this;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isNotNull(){
        if(this.obj == null){
            throw new UnsupportedOperationException();
        }
        else {
            return this;
        }
    }

    public Assertion isEqualTo(Object o2){
        if(this.obj.equals(o2)==true){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }
    public Assertion isEqualTo(int i2){
        if(this.obj instanceof Integer && ((Integer) this.obj).intValue()==i2){
            return this;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isLessThan(int i2){
        if(this.obj instanceof Integer && ((Integer) this.obj).intValue()<=i2){
            return this;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isGreaterThan(int i2){
        if(this.obj instanceof Integer && ((Integer) this.obj).intValue()>=i2){
            return this;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isNotEqualTo(Object o2){
        if(this.obj.equals(o2)==false){
            return this;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isInstanceOf(Class c){
        if(this.obj.getClass().equals(c) ){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    public Assertion startsWith(String s2){
        if (this.obj instanceof String && ((String) this.obj).startsWith(s2)){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isEmpty(){
        if(this.obj instanceof String && this.obj.equals("")){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    public Assertion contains(String s2){
        if(this.obj instanceof String && ((String) this.obj).contains(s2)){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isTrue(){
        if(this.obj instanceof Boolean && Boolean.TRUE.equals(this.obj)){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

    public Assertion isFalse(){
        if(this.obj instanceof Boolean && Boolean.FALSE.equals(this.obj)){
            return this;
        }
        else{
            throw new UnsupportedOperationException();
        }
    }

}
