import java.util.Scanner;

public class Main {
    static final double SPEED_OF_LIGHT = 299792458.0; // m/s

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("=== Advanced Relativistic Doppler Effect Simulator ===");
                System.out.println(">> INPUT GUIDE:");
                System.out.println("- Emitted Frequency: Frequency of the source in Hz (e.g., 5e14)");
                System.out.println("- Source Velocity: Velocity of the source in m/s (must be < speed of light)");
                System.out.println("- Is the source moving? (yes/no)");
                System.out.println("- If yes, is the source approaching the observer? (yes/no)");
                System.out.println("------------------------------------------------------\n");

                System.out.print("Enter emitted frequency (Hz): ");
                double f0 = Double.parseDouble(input.nextLine());

                System.out.print("Enter source velocity (m/s): ");
                double v = Math.abs(Double.parseDouble(input.nextLine()));

                System.out.print("Is the source moving? (yes/no): ");
                String isMoving = input.nextLine().trim().toLowerCase();

                boolean isApproaching = false;
                if (isMoving.equals("yes")) {
                    System.out.print("Is the source approaching the observer? (yes/no): ");
                    isApproaching = input.nextLine().trim().toLowerCase().startsWith("y");
                }

                if (v >= SPEED_OF_LIGHT) {
                    System.out.println("ERROR: Velocity must be less than the speed of light!");
                    continue;
                }

                double beta = v / SPEED_OF_LIGHT;
                double fObserved = f0;
                double lambdaEmitted = SPEED_OF_LIGHT / f0;
                double lambdaObserved = lambdaEmitted;

                if (isMoving.equals("yes")) {
                    if (isApproaching) {
                        fObserved = f0 * Math.sqrt((1 + beta) / (1 - beta));
                    } else {
                        fObserved = f0 * Math.sqrt((1 - beta) / (1 + beta));
                    }
                    lambdaObserved = SPEED_OF_LIGHT / fObserved;
                }

                // Output
                System.out.println("\n=== Simulation Result ===");
                System.out.printf("Original Frequency     : %.4e Hz\n", f0);
                System.out.printf("Observed Frequency     : %.4e Hz\n", fObserved);
                System.out.printf("Original Wavelength    : %.4e m\n", lambdaEmitted);
                System.out.printf("Observed Wavelength    : %.4e m\n", lambdaObserved);

                if (isMoving.equals("no")) {
                    System.out.println("Effect: No Doppler shift (Static source)");
                } else {
                    System.out.println("Effect: " + (isApproaching ? "Blue-shift (approaching observer)" : "Red-shift (receding from observer)"));
                }

                // Visualization
                System.out.println("\n--- Wave Visualization ---");
                System.out.println("Original Wave:");
                drawWave(lambdaEmitted);

                System.out.println("Observed Wave:");
                drawWave(lambdaObserved);

                // Repeat?
                System.out.print("Do you want to simulate again? (yes/no): ");
                String repeat = input.nextLine().trim().toLowerCase();
                if (!repeat.equals("yes")) {
                    System.out.println("Simulation terminated. Goodbye!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("ERROR: Invalid input! " + e.getMessage());
            }
        }

        input.close();
    }

    static void drawWave(double wavelength) {
        int scale = 40;
        int waveCount = (int)(scale / wavelength * 1e-6);
        waveCount = Math.max(waveCount, 5); // at least 5 waves to visualize properly
        for (int i = 0; i < waveCount; i++) {
            System.out.print("~");
        }
        System.out.println("\nLegend: ~ = wave crest\n");
    }
}
