import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vehicle implements Comparable<Vehicle> {

	private Phase phase;
	private String segment;
	private String plateId;
	private String type;
	private int crossingTime;
	private VehicleDirection direction;
	private int length;
	private int emission;
	private Status crossingStatus;

	public enum VehicleDirection {
		STRAIGHT, RIGHT, LEFT;

		// string to Enum ignore case
		// https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
		public static VehicleDirection directionLookup(String direction) {
			for (VehicleDirection d : VehicleDirection.values()) {
				if (d.name().equalsIgnoreCase(direction)) {
					return d;
				}
			}
			return null;
		}
	}

	public enum Status {
		CROSSED, WAITING;

		// string to Enum with ignore case
		// https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
		public static Status StatusLookup(String status) {
			for (Status s : Status.values()) {
				if (s.name().equalsIgnoreCase(status)) {
					return s;
				}
			}
			return null;
		}
	}

	/**
	 * @param segment        - Vehicle segment, Can only be s1, s2, s3 or s4
	 * @param plateId        - Vehicle plate number
	 * @param type           - car, bus or truck
	 * @param crossingTime
	 * @param direction      - can be left, right or straight
	 * @param length         - lenght of the vehicle
	 * @param emission       - emission of the vehicle
	 * @param crossingStatus - can only be waiting, crossed
	 * @throws CarPlateNumberInvalid - if plateId is invalid
	 * @throws NumberFormatException - if crossingTime, length or emission can't be parsed to valid integer values
	 * @throws InvalidInputException - if direction, crossingStatus or type are not valid
	 */
	public Vehicle(String segment, String plateId, String type, String crossingTime, String direction, String length,
			String emission, String crossingStatus)
					throws CarPlateNumberInvalid, NumberFormatException, InvalidInputException {

		String seg = segment.trim().toUpperCase();
		if (seg.length() == 2 && seg.charAt(0) == 'S') {
			int ascii = seg.charAt(1); // character to ascii value
			int ascii1 = (int) '1'; // ascii value of 1
			int ascii8 = (int) '4'; // ascii value of 4
			if (ascii1 <= ascii && ascii <= ascii8) // ascii value should be between[1,4] as segment can be only s1, s2,
				// s3 and s4
			{
				this.segment = seg;
			} else {
				throw new InvalidInputException("Segment can only be S1, S2, S3, S4, S5, S6, S7 or S8");
			}

		} else {
			throw new InvalidInputException("Segment can only be S1, S2, S3, S4, S5, S6, S7 or S8");
		}

		if (isCarPlateNumberValid(plateId)) {
			this.plateId = plateId;
		} else {
			throw new CarPlateNumberInvalid("Car plate number is not valid");
		}

		type = type.trim().toUpperCase();
		if (type.equals("CAR") || type.equals("TRUCK") || type.equals("BUS")) {
			this.type = type;
		} else {
			throw new InvalidInputException("Truck, Bus and Car are only types allowed");
		}

		// https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/#:~:text=Use%20Integer.parseInt()%20to%20Convert%20a%20String%20to%20an%20Integer&text=If%20the%20string%20does%20not,inside%20the%20try%2Dcatch%20block.
		if (!isNumeric(crossingTime.trim())) {
			throw new NumberFormatException("CrossingTime not a valid integer");
		}
		this.crossingTime = Integer.parseInt(crossingTime);

		if (!isNumeric(length.trim())) {
			throw new NumberFormatException("Length not a valid integer");
		}
		this.length = Integer.parseInt(length.trim());
		if (!isNumeric(emission.trim())) {
			throw new NumberFormatException("Emission not a valid integer");
		}
		this.emission = Integer.parseInt(emission.trim());

		VehicleDirection d = VehicleDirection.directionLookup(direction);
		if (d == null) {
			throw new InvalidInputException("Vehicle direction can only be left, right or straight");
		} else {
			this.direction = d;
		}

		Status s = Status.StatusLookup(crossingStatus);
		if (s == null) {
			throw new InvalidInputException("Crossing Status can only be crossed, waiting");
		}
		this.crossingStatus = s;
	}

	/**
	 * @param str to be checked if it is a valid numeric value
	 * @return true if valid, false if invalid
	 */
	private boolean isNumeric(String str) {
		if (str == null || str.isEmpty() || !str.matches("[0-9.]+")) {
			return false;
		}
		return true;
	}

	/**
	 * @param plateNumber to be checked if it is a valid number plate
	 * @return true if valid, false if not a valid number plate
	 */
	private boolean isCarPlateNumberValid(String plateNumber) {
		// https://www.javatpoint.com/java-regex
		// https://gist.github.com/danielrbradley/7567269
		String pattern = "(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)|(^[0-9]{1,4}[A-Z]{1,2}$)|(^[0-9]{1,3}[A-Z]{1,3}$)|(^[A-Z]{1,2}[0-9]{1,4}$)|(^[A-Z]{1,3}[0-9]{1,3}$)|(^[A-Z]{1,3}[0-9]{1,4}$)|(^[0-9]{3}[DX]{1}[0-9]{3}$)\r\n"
				+ "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(plateNumber.trim().toUpperCase());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return A string containing all details of the vehicle Not being used right now.
	 */
	public String toString() {
		//        return String.format("%-5s", id ) + String.format("%-20s", name) +
		//                 String.format("%5d", hoursWorked );

		return String.format("%-4s", segment) + plateId + " " + String.format("%-7s", type)
		+ String.format("%-5s", crossingTime) + String.format("%-10s", direction)
		+ String.format("%-5s", length) + String.format("%-5s", emission)
		+ String.format("%-9s", crossingStatus) + String.format("%-10s", phase);
	}

	/**
	 * Two vehicle will be equal if both are Vehicle class instances and their plateId is
	 */
	public boolean equals(Object other) {
		if (other instanceof Vehicle) {
			Vehicle otherVehicle = (Vehicle) other;
			return plateId.equals(otherVehicle.plateId);
		} else {
			return false;
		}
	}

	/**
	 * Compare this vehicle object against another, for the purpose of sorting. Fields are compared by segment.
	 * @param otherDetails The details to be compared against.
	 * @return a negative integer if this id comes before the parameter's id, zero
	 *         if they are equal and a positive integer if this comes after the
	 *         other.
	 */
	public int compareTo(Vehicle otherDetails) {
		return segment.compareTo(otherDetails.getSegment());
	}

	public void setPhase(Phase p) {
		phase = p;
	}

	public Phase getPhase() {
		return phase;
	}

	public String getSegment() {
		return segment;
	}

	public String getPlateId() {
		return plateId;
	}

	public String getType() {
		return type;
	}

	public int getCrossingTime() {
		return crossingTime;
	}

	public VehicleDirection getDirection() {
		return direction;
	}

	public int getLength() {
		return length;
	}

	public int getEmission() {
		return emission;
	}

	public Status getStatus() {
		return crossingStatus;
	}
}
