package bigdata;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.supervised.attribute.AttributeSelection;;

public class StudentsClassifier {

	public static void main(String[] args) throws Exception {
		DataSource src = new DataSource("students_por.arff");
		DataSource srcTest = new DataSource("students_por_test.arff");
		Instances train = src.getDataSet();
		Instances test = srcTest.getDataSet();
		
		// setting class attribute
		train.setClassIndex(17);
		test.setClassIndex(17);
		AttributeSelection attrSel = new AttributeSelection(); // new instance of filter
		attrSel.setEvaluator(new InfoGainAttributeEval());
		attrSel.setSearch(new Ranker());                           
		attrSel.setInputFormat(train);                          // inform filter about dataset **AFTER** setting options
		
		//weka.classifiers.lazy.IBk -K 1 -W 0 -A "weka.core.neighboursearch.LinearNNSearch -A \"weka.core.EuclideanDistance -R first-last\""
		//weka.core.neighboursearch.LinearNNSearch -A "weka.core.EuclideanDistance -R first-last"
		IBk ibk = new IBk();
		ibk.setNearestNeighbourSearchAlgorithm(new LinearNNSearch());
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(attrSel);
		fc.setClassifier(ibk);
		
		// train and make predictions
		fc.buildClassifier(train);
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = fc.classifyInstance(test.instance(i));
			System.out.print("ID: " + test.instance(i).value(0));
			System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			System.out.println(", predicted: " + test.classAttribute().value((int) pred));
		}
	}
}
