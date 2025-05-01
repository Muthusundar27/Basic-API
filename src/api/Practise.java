package api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.Arrays;

public class Practise {

	public static void main(String[] args) {

		int arr[] = { 10, 20, 30, 40, 50 };
		int n = arr.length;
		for(int i=1; i<n-1; i+=2) {
			int tmp = arr[i];
			arr[i]=arr[n-1];
			for(int j =n-1; j>i; j--) {
				arr[j] = arr[j-1];
			}
			arr[i+1] = tmp;
		}
        System.out.println("Rearranged Array: " + Arrays.toString(arr));

	}

}
