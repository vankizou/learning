/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 18:31
 **/
public class T1 {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {2, 3, 4, 5};

        int arr1Len = arr1.length;
        int arr2Len = arr2.length;
        int arr1Cursor = 0;
        int arr2Cursor = 0;

        while (true) {
            if (arr1Cursor >= arr1Len || arr2Cursor >= arr2Len) {
                break;
            }
            if (arr1[arr1Cursor] == arr2[arr2Cursor]) {
                System.out.print(arr1[arr1Cursor] + " ");
                arr1Cursor++;
                arr2Cursor++;
            } else if (arr1[arr1Cursor] < arr2[arr2Cursor]) {
                arr1Cursor++;
            } else if (arr1[arr1Cursor] > arr2[arr2Cursor]) {
                arr2Cursor++;
            } else {
                break;
            }
        }
    }
}
