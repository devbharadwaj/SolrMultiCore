package edu.solr.buffalo.IR.launch;

import edu.solr.buffalo.IR.wikinews.WikiNewsRunner;
import edu.solr.buffalo.IR.wikipedia.WikipediaRunner;
import edu.solr.buffalo.IR.wikivoyage.WikiVoyageRunner;

public class IndexSolr {

	public static void main(String[] args) {
	
		WikiNewsRunner.starter();
		System.out.println("WikiVoyage Indexed.\n");
		WikiVoyageRunner.starter();
		System.out.println("WikiNews Indexed.\n");
		WikipediaRunner.starter();
		System.out.println("Wikipedia Indexed.\n");
		System.out.println("Done!");
	}
	
	
	
}
