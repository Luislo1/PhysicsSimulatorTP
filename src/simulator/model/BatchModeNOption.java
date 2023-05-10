package simulator.model;

import java.util.HashMap;
import java.util.Map;

import simulator.control.Controller;

public class BatchModeNOption implements SimulatorObserver{
	Map<Body, Integer> numberOfNorth;
	Map<Body, Boolean> needsToChange;
	
	/* Usar este input en el arguments de la run configuration para probar la nueva opción -n del batch mode.
	   -i resources/examples/input/ex1.json -o resources/examples/expected_output/out.1.json -s 1000 -dt 1000 -fl nlug -m batch -n
	*/
	
	public BatchModeNOption(Controller ctrl) {
		numberOfNorth = new HashMap<Body, Integer>();
		needsToChange = new HashMap<Body, Boolean>();
		ctrl.addObserver(this); // Toda clase que extienda simulatorObserver hay que añadirla a la lista de observers, sino no puede coger la informacion.
	}
	
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		Integer valueOfBody;
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for(Body b: value) {
				if(b.getVelocity().getY() < 0) { // If it's negative.
					needsToChange.put(b, true);
				}
				else
					needsToChange.put(b, false);
				
				if(numberOfNorth.containsKey(b)) { // If the key is already in the map.
					if(needsToChange.get(b).equals(true)) {
						valueOfBody = numberOfNorth.get(b) + 1;
						numberOfNorth.replace(b, valueOfBody);
					}
				}
				else { // Create the key if it's not yet in the map.
					if(needsToChange.get(b).equals(true))
						numberOfNorth.put(b, 1);
					else
						numberOfNorth.put(b, 0);
			    }
			
			}
		}
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	
	public void PrintNumberOfNorth() {
		
		for(Map.Entry<Body, Integer> entry: numberOfNorth.entrySet()) {
				Integer value = entry.getValue();
				System.out.println(entry.getKey().getId() + ":" + entry.getKey().getgId() + " " + value);
		}
	}

}
