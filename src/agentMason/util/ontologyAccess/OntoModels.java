package agentMason.util.ontologyAccess;



import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.impl.OntModelImpl;
import org.apache.jena.rdf.model.ModelFactory;



public class OntoModels {

	private static OntModelImpl rawOntModel;

	private static OntModelImpl inferOntModel;


	public static void chargeModels() {
		String ontologyFileAddress = OntoAddress.getOntoAddress();

		// charge the raw ontology
		rawOntModel = new OntModelImpl(OntModelSpec.OWL_DL_MEM);
		rawOntModel.getDocumentManager().addAltEntry(
				ontologyFileAddress, ontologyFileAddress);
		rawOntModel.read(ontologyFileAddress);

		// charge the infered ontology

		inferOntModel = new OntModelImpl(
				OntModelSpec.OWL_DL_MEM_RULE_INF);
		inferOntModel.getDocumentManager().addAltEntry(
				ontologyFileAddress, ontologyFileAddress);
		inferOntModel.read(ontologyFileAddress);

	}
	
	/**
	 * @return OntModelImpl, that reference the raw ontology model
	 */
	public static OntModelImpl getOntoModel(){
		if (inferOntModel == null)chargeModels();
		return rawOntModel;
		
	}

	/**
	 * @return OntModelImpl, that reference the infered ontology model and use the owl-dl rules
	 */
	public static OntModelImpl getInferModel(){
		if (inferOntModel == null)chargeModels();
		
		return inferOntModel;
		
	}

	public static void main(String[] args) {

		// new OntoAddress();
		chargeModels();
		
		
		System.out.println("rawOntModel.toString()");
		System.out.println(rawOntModel.toString());
		System.out.println("inferDominiumOntModel.toString()");
		System.out.println(inferOntModel.toString());
		System.out.println("inferOntModel.getReasoner().toString()");
		System.out.println(inferOntModel.getReasoner().toString());
		
		

	}

}
