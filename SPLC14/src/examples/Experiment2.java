package examples;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pairwise.PairwiseGenerator;
import es.us.isa.ChocoReasoner.pairwise.ChocoReasoner;
import es.us.isa.ChocoReasoner.pairwise.Pair;
import es.us.isa.ChocoReasoner.pairwise.questions.PairWiseNonAttribtuedQuestion;
import fr.familiar.attributedfm.AttributedFeatureModel;
import fr.familiar.readers.VMReader;

public class Experiment2 {
	private static PrintWriter out ;
	
	public static void main(String[] args) throws Exception {
		
		
		int[] features = {50,100,200};
		int[] ctc = { 5, 10, 15 };
		int[] destinationSubDir={0,25,50};
		out=  new PrintWriter("./results50.csv");
		out.println("NoF;CTC;num;%ND;time");
		out.flush();
		for (int feat : features) {
			for (int cons : ctc) {
					for (int i = 0; i < 10; i++) {
						Collection<Pair> pairs =null;
						for(int destDir:destinationSubDir){
							String name="./experiments/random/"+destDir+"ND/"+feat+"-"+cons+"-"+i+".vm";
							out.print(feat+";"+cons+";"+i+";"+destDir+";");
							
							VMReader reader = new VMReader();
							AttributedFeatureModel model = (AttributedFeatureModel) reader.parseFile(name);

							if(pairs==null){
								PairwiseGenerator pairgen = new PairwiseGenerator(model);
								pairs = pairgen.getPairs();
							}
							System.out.println("The number of pairs to cover is: " + pairs.size());
							
							ChocoReasoner reasoner = new ChocoReasoner(pairs);
							model.transformto(reasoner);
							PairWiseNonAttribtuedQuestion pairwiseq = new PairWiseNonAttribtuedQuestion();
							
							pairwiseq.answer(reasoner);
							out.println(pairwiseq.time);
							out.flush();	
							System.out.println(name);
						}
					}
				}
			
		}
		out.close();
	}
	

}
