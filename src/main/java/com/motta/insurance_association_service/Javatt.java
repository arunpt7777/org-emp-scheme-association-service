package com.motta.insurance_association_service;

public class Javatt {
    public static void main(String[] args) {
        int [] nums = new int []{1,2,3,4,5, 20};
        int max = 0;
        for(int i:nums) {
            if (i>max) {
                max = i;
            }
        }
        System.out.println("Max is: " + max);


    }
}
