package generators;

import java.util.List;
import java.util.Random;

import fr.familiar.attributedfm.AttributedFeatureModel;
import fr.familiar.attributedfm.GenericAttribute;
import fr.familiar.readers.VMReader;
import fr.familiar.readers.VMWriter;

public class MotivNDGenerator {
	public static void main(String[] args) throws Exception {

		int[] destinationSubDir = {0,10,20,30,40,50,60,70,80,90,100 };

		Random r = new Random();
		VMReader reader = new VMReader();
		for (int destDir : destinationSubDir) {
			for(int i=0;i<10;i++){
			AttributedFeatureModel model = reader.parseFile("experiments/real/MOTIVWithTimeCost.vm");


			List<GenericAttribute> allAttributes = model.getAllAttributes();
			Float numberofAttributesToDo = new Float(allAttributes.size()* (destDir / 100d));
			int numberOfAtts = 0;
			while (numberOfAtts < numberofAttributesToDo.intValue()) {
				GenericAttribute attribute = allAttributes.get(r.nextInt(allAttributes.size()));
				if(!attribute.nonDesicion){
					attribute.nonDesicion = true;
					numberOfAtts++;
				}
			}

			VMWriter writer = new VMWriter();
			writer.writeFile("experiments/real/costNDVariants/MOTIVWCostND"+destDir+"-"+i + ".vm",model);
		}
		}
	}

}
