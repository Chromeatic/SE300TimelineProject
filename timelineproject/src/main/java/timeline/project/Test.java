public class Test {
    public static void main(String[] args) {
        int time = 1979_03_26;
        int yearDigits = 6;
        int monthDigits = 4;
        int dayDigits = 2;

        System.out.println(1979_03_26 / 1_00_00);
        System.out.println((1979_03_26 % 1_00_00) / 1_00);
        System.out.println((1979_03_26 % 1_00));

        System.out.println((1979_03_26 % 1_0000_00_00) / 1_00_00);
        System.out.println((1979_03_26 %      1_00_00) /    1_00);
        System.out.println((1979_03_26 %         1_00) /       1);

        System.out.println((1979_03_26 % 1_0000_00_00) / 1_00_00);
        System.out.println((1979_03_26 %      1_00_00) /    1_00);
        System.out.println((1979_03_26 %         1_00) /       1);
    }
}
