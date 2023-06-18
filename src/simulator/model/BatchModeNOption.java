package simulator.model;

import java.util.HashMap;
import java.util.Map;

import simulator.control.Controller;

public class BatchModeNOption implements SimulatorObserver{
	/* Usar este input en el arguments de la run configuration para probar la nueva opción -n del batch mode.
	   -i resources/examples/input/ex1.json -o resources/examples/expected_output/out.1.json -s 1000 -dt 1000 -fl nlug -m batch -n
	*/
	
	private Map<String, Integer> numberOfNorth;
	private Map<String, Boolean> needsToChange;
	
	public BatchModeNOption(Controller ctrl) {
		ctrl.addObserver(this); // Toda clase que extienda simulatorObserver hay que añadirla a la lista de observers, sino no puede coger la informacion.
		numberOfNorth = new HashMap<>();
		needsToChange = new HashMap<>();
	}
	
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for(Body b: value) {
				String id = b.getId() + ":" + b.getgId();
				Double velY = b.getVelocity().getY();
				
				if (velY > 0 && needsToChange.get(id)) {
					needsToChange.put(id, false);
					int newAmount = numberOfNorth.get(id);
					newAmount++;
					numberOfNorth.put(id, newAmount);
				} 
				
				else if (velY < 0) 
					needsToChange.put(id, true);	
			}
		}
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) { // Si reseteo el simulator.
		numberOfNorth.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) { // Si añado ??????
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				String id = b.getId() + ":" + b.getgId();
				boolean pointsNorth = false;
				numberOfNorth.put(id, 0);
				if (b.getVelocity().getY() < 0) {
					pointsNorth = true;
				}
				needsToChange.put(id, pointsNorth);
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		String id = b.getId() + ":" + b.getgId();
		boolean pointsNorth = false;
		numberOfNorth.put(id, 0);
		if (b.getVelocity().getY() < 0) {
			pointsNorth = true;
		}
		needsToChange.put(id, pointsNorth);
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
		System.out.println("Turn to North Statistics:");
		for(Map.Entry<String, Integer> entry: numberOfNorth.entrySet()) {
				System.out.println(entry.getKey() + " => " + String.valueOf(entry.getValue()));
		}
	}

}
