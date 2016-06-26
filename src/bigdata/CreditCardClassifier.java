package bigdata;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.supervised.attribute.AttributeSelection;;

public class CreditCardClassifier {

	public static void main(String[] args) throws Exception {
		DataSource src = new DataSource("credit_card_clients.arff");
		DataSource srcTest = new DataSource("credit_card_clients_test.arff");
		Instances train = src.getDataSet();
		Instances test = srcTest.getDataSet();
		
		// setting class attribute
		train.setClassIndex(17);
		test.setClassIndex(17);
		// do not work with numeric values
		//AttributeSelection attrSel = new AttributeSelection(); // new instance of filter
		//attrSel.setEvaluator(new InfoGainAttributeEval());
		//attrSel.setSearch(new Ranker());                           
		//attrSel.setInputFormat(train);                         
		
		IBk ibk = new IBk();
		ibk.setNearestNeighbourSearchAlgorithm(new LinearNNSearch());

		// train and make predictions
		ibk.buildClassifier(train);
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = ibk.classifyInstance(test.instance(i));
			System.out.print("ID: " + test.instance(i).value(0));
			System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			System.out.println(", predicted: " + test.classAttribute().value((int) pred));
		}
	}
}
