import java.util.*;

import java.nio.charset.*;

class java

{

    public static void main(String[] args) throws Exception{
        System.setProperty("file.encoding","UTF-8");
        Properties pps = System.getProperties();
        pps.list(System.out);      
    }

}