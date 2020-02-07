package com.upp.nc.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PaymentMethods {
	SUBSCRIPTION, OPEN_ACCESS;
	
	public static List<String> getFields(){
		return Stream.of(PaymentMethods.values())
                .map(PaymentMethods::name)
                .collect(Collectors.toList());
	}
}
