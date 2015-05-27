package com.ligeng;

import java.util.ArrayList;
import java.util.LinkedList;

import typeinfo.pets.Pet;
import typeinfo.pets.Pets;

public class LinkedListFeatures {
	public static void main(String[] args){
		LinkedList<Pet> pets = new LinkedList<Pet>(Pets.arrayList(5));
		System.out.println(pets);
		
		System.out.println(pets.getFirst());
		System.out.println(pets.element());
		System.out.println(pets.peek());
		
	
	}
}
