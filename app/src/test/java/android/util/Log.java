package android.util;

public class Log {
    //Stubbing Log static functions to allow test to run on code using them
    public static int d(String tag, String msg){ return -1;}
    public static int d(String tag, String msg, Throwable tr){return -1;}

    public static int e(String tag, String msg){ return -1;}
    public static int e(String tag, String msg, Throwable tr){ return -1;}

    public static int i(String tag, String msg){ return -1;}
    public static int i(String tag, String msg, Throwable tr){ return -1;}

    public static int v(String tag, String msg){ return -1;}
    public static int v(String tag, String msg, Throwable tr){ return -1;}

    public static int w(String tag, String msg){ return -1;}
    public static int w(String tag, String msg, Throwable tr){ return -1;}

    public static int wtf(String tag, String msg){ return -1;}
    public static int wtf(String tag, String msg, Throwable tr){ return -1;}
}
