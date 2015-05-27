package com.ligeng;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	static class OOMObject{
		
	}
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<HeapOOM.OOMObject>();
		while(true){
			list.add(new OOMObject());
		}

	}

}
