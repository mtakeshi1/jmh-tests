package jmh;

public class RecordTest {

    public static void main(String[] args) {
        SimpleRecord record = new SimpleRecord(1);
        System.out.println(record);
        System.out.println(System.getProperty("java.library.path"));
    }

}
