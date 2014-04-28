package generators;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import es.us.isa.FAMA.models.FAMAAttributedfeatureModel.fileformats.AttributedReader;
import fr.familiar.attributedfm.AttributedFeatureModel;
import fr.familiar.attributedfm.GenericAttribute;
import fr.familiar.readers.VMWriter;

public class RandomNDGenerator {
	public static void main(String[] args) throws Exception {

		int[] features = { 10,20,30,50,100,200};
		int[] ctc = { 5, 10, 15 };
		int[] destinationSubDir={0,25,50};
		String srcDirectory="./experiments/random/";
		String dstDirectory="./experiments/";

		Random r = new Random();

		for (int feat : features) {
			for (int cons : ctc) {
					for (int i = 0; i < 10; i++) {
						String name=feat+"-"+cons+"-"+i;
						
						for(int destDir:destinationSubDir){
							AttributedReader reader = new AttributedReader();
							AttributedFeatureModel model = reader.parseFile(srcDirectory+name+".afm");
							
							//Introduce the percentage of random ND
							
							List<GenericAttribute> allAttributes = model.getAllAttributes();
							Float numberofAttributesToDo= new Float(allAttributes.size()*(destDir/100d));
							int numberOfAtts=0;
							while(numberOfAtts<numberofAttributesToDo.intValue()){
								GenericAttribute attribute = allAttributes.get(r.nextInt(allAttributes.size()));
								attribute.nonDesicion=true;
								numberOfAtts++;
							}

							VMWriter writer = new VMWriter();
							writer.writeFile(dstDirectory+destDir+"ND/"+name+".vm", model);
						}
						
						
					
				}
			}
		}
		
	}
}
