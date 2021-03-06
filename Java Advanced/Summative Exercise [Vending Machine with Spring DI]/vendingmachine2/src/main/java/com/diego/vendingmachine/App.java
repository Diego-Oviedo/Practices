package com.diego.vendingmachine;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.diego.vendingmachine.controller.Controller;

public class App 
{
    public static void main( String[] args )
    {
    	AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.diego.vendingmachine");
        appContext.refresh();

        Controller controller = appContext.getBean("controller", Controller.class);
        controller.run();
    }
}
/*
ITMM&000JULY21::M&M's::2.34::15
ITMNA000JULY21::Naya Water::2.10::14
ITMLA000JULY21::Lays::3.75::15
ITMSN000JULY21::Snickers::3.28::14
ITMKI000JULY21::Kit Kat::1.67::15
ITMTR000JULY21::Trident Gum::1.15::13
*/