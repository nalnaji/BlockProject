public class Debugger{
    static boolean on = false;
    public static void toggleState(){
      on = !on;   
    }
    public static boolean isEnabled(){
        return on;
    }

    public static void log(Object o){
      if(on) {
        System.out.println(o.toString());
      }
    }
}

