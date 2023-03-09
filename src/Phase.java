/**
 * This class stores information of 8 phases with respective durations
 */
public class Phase {

	private int phaseNumber;
	private int phaseDuration;

	public Phase(int phaseNumber, int phaseDuration) {
		this.phaseNumber = phaseNumber;
		this.phaseDuration = phaseDuration;
	}

	public int getPhaseNumber() {
		return phaseNumber;
	}

	public int getPhaseDuration() {
		return phaseDuration;
	}

	public String toString() {
		return phaseNumber + " " + phaseDuration;
	}
}
