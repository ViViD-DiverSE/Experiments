package examples;

import java.io.PrintWriter;
import java.util.Collection;

import pairwise.PairwiseGenerator;
import es.us.isa.ChocoReasoner.pairwise.ChocoReasoner;
import es.us.isa.ChocoReasoner.pairwise.Pair;
import es.us.isa.ChocoReasoner.pairwise.questions.PairWiseNonAttribtuedQuestion;
import fr.familiar.attributedfm.AttributedFeatureModel;
import fr.familiar.readers.VMReader;

public class Experiment1 {
	private static PrintWriter out;

	public static void main(String[] args) throws Exception {

		int[] destinationSubDir = {100,90,80,70,60,50,40,30,20,10,0};
	

		out = new PrintWriter("./resultsMOTIV.csv");
		out.println("num;%ND;solverTime;time");
		out.flush();

		Collection<Pair> pairs = null;

		for (int i = 0; i < 10; i++) {
			
			for (int destDir : destinationSubDir) {
				VMReader reader = new VMReader();
				AttributedFeatureModel model = reader.parseFile("experiments/real/costNDVariants/MOTIVWCostND"+destDir+"-"+i + ".vm");
				
				System.out.println("Reading MOTIVWCostND"+destDir+"-"+i + ".vm");
				
				if (pairs == null) {//we only do this one time
					PairwiseGenerator pairgen = new PairwiseGenerator(model);
					pairs = pairgen.getPairs();
					System.out.println("The number of pairs to cover is: "
							+ pairs.size());
				}

				ChocoReasoner reasoner = new ChocoReasoner(pairs);
				model.transformto(reasoner);
				PairWiseNonAttribtuedQuestion pairwiseq = new PairWiseNonAttribtuedQuestion();
				long start= System.currentTimeMillis();
				pairwiseq.answer(reasoner);
				out.println(i+";"+destDir+";"+pairwiseq.time+";"+(System.currentTimeMillis()-start));
				out.flush();
			}

		}
		out.close();
	}

}
