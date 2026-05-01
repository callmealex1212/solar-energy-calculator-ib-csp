import java.util.Scanner;
public class Main {
    static final double PANEL_WATT_PEAK        = 400;
    static final double PANEL_AREA_M2          = 2.0;
    static final double CO2_KG_PER_KWH         = 0.233;
    static final double ELECTRICITY_PRICE_KWH  = 0.28;
    static final double SYSTEM_LOSS_FACTOR     = 0.80;
    static final double INSTALL_COST_PER_KW    = 1200.0;
    static final int    PANEL_LIFETIME_YEARS   = 25;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printBanner();
        double roofAreaM2      = getPositiveDouble(scanner, "Enter your usable roof area (m²): ");
        double peakSunHours    = getPeakSunHours(scanner);
        double efficiencyPct   = getEfficiency(scanner);
        Result result = calculate(roofAreaM2, peakSunHours, efficiencyPct / 100.0);

        printResults(result);

        printComparisonTable(roofAreaM2, peakSunHours);

        scanner.close();
    }

    static Result calculate(double roofAreaM2, double peakSunHours, double efficiency) {
        int    panels          = (int) Math.floor(roofAreaM2 / PANEL_AREA_M2);
        double systemKw        = (panels * PANEL_WATT_PEAK * efficiency) / 1000.0;
        double dailyKwh        = systemKw * peakSunHours * SYSTEM_LOSS_FACTOR;
        double annualKwh       = dailyKwh * 365;
        double annualSavings   = annualKwh * ELECTRICITY_PRICE_KWH;
        double installCost     = systemKw * INSTALL_COST_PER_KW;
        double paybackYears    = (installCost > 0 && annualSavings > 0)
                ? installCost / annualSavings : 0;
        double lifetimeSavings = annualSavings * PANEL_LIFETIME_YEARS - installCost;
        double co2SavedKg      = annualKwh * CO2_KG_PER_KWH;

        return new Result(panels, systemKw, dailyKwh, annualKwh,
                annualSavings, installCost, paybackYears,
                lifetimeSavings, co2SavedKg);
    }

    static void printBanner() {
        System.out.println("=============================================================");
        System.out.println("        SOLAR ENERGY CALCULATOR — IB CSP PROJECT            ");
        System.out.println("        Energy for Future Scientific Challenges              ");
        System.out.println("=============================================================");
        System.out.println();
    }

    static void printResults(Result r) {
        System.out.println();
        System.out.println("-------------------------------------------------------------");
        System.out.println("                        RESULTS                              ");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("  Panels that fit on roof   : %d panels%n",           r.panels);
        System.out.printf("  System capacity           : %.2f kW%n",              r.systemKw);
        System.out.printf("  Daily energy output       : %.2f kWh%n",             r.dailyKwh);
        System.out.printf("  Annual energy output      : %.1f kWh%n",             r.annualKwh);
        System.out.println();
        System.out.printf("  Estimated install cost    : £%.2f%n",                r.installCost);
        System.out.printf("  Annual savings            : £%.2f%n",                r.annualSavings);
        System.out.printf("  Payback period            : %.1f years%n",           r.paybackYears);
        System.out.printf("  Lifetime net savings (25y): £%.2f%n",                r.lifetimeSavings);
        System.out.println();
        System.out.printf("  CO₂ avoided per year      : %.1f kg%n",              r.co2SavedKg);
        System.out.printf("  Equivalent trees planted  : %.0f trees%n",           r.co2SavedKg / 21.77);
        System.out.println("-------------------------------------------------------------");
        printEnergyBar(r.annualKwh);
    }

    static void printEnergyBar(double annualKwh) {
        System.out.println("\n  Annual output visualised (each ▓ = 500 kWh):");
        System.out.print("  ");
        int blocks = (int) Math.min(annualKwh / 500, 40);
        for (int i = 0; i < blocks; i++) System.out.print("▓");
        System.out.printf("  %.0f kWh%n", annualKwh);
        System.out.println();
    }

    static void printComparisonTable(double roofAreaM2, double peakSunHours) {
        System.out.println("=============================================================");
        System.out.println("        EFFICIENCY COMPARISON TABLE                         ");
        System.out.println("=============================================================");
        System.out.printf("  %-12s %-15s %-15s %-12s%n",
                "Efficiency", "Annual kWh", "Annual £ saved", "Payback (yrs)");
        System.out.println("  " + "-".repeat(56));

        int[] efficiencies = {15, 20, 22, 25};
        for (int eff : efficiencies) {
            Result r = calculate(roofAreaM2, peakSunHours, eff / 100.0);
            System.out.printf("  %-12s %-15.1f %-15.2f %-12.1f%n",
                    eff + "%", r.annualKwh, r.annualSavings, r.paybackYears);
        }
        System.out.println("=============================================================");
    }

    static double getPeakSunHours(Scanner sc) {
        System.out.println();
        System.out.println("Select your location (peak sun hours/day):");
        System.out.println("  1. UK / Northern Europe  (2.5 hrs)");
        System.out.println("  2. Central Europe        (4.0 hrs)");
        System.out.println("  3. Southern Europe       (5.5 hrs)");
        System.out.println("  4. Middle East / Africa  (6.5 hrs)");
        System.out.println("  5. Enter custom value");

        int choice = getRangedInt(sc, "Enter choice (1-5): ", 1, 5);
        switch (choice) {
            case 1: return 2.5;
            case 2: return 4.0;
            case 3: return 5.5;
            case 4: return 6.5;
            default: return getPositiveDouble(sc, "Enter peak sun hours per day: ");
        }
    }

    static double getEfficiency(Scanner sc) {
        System.out.println();
        System.out.println("Select panel efficiency:");
        System.out.println("  1. Standard (15%) — budget panels");
        System.out.println("  2. Mid-range (20%) — common residential");
        System.out.println("  3. High-end  (22%) — premium monocrystalline");
        System.out.println("  4. Top-tier  (25%) — cutting-edge panels");
        System.out.println("  5. Enter custom value (%)");

        int choice = getRangedInt(sc, "Enter choice (1-5): ", 1, 5);
        switch (choice) {
            case 1: return 15;
            case 2: return 20;
            case 3: return 22;
            case 4: return 25;
            default: return getRangedDouble(sc, "Enter efficiency % (1-100): ", 1, 100);
        }
    }

    static double getPositiveDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val > 0) return val;
                System.out.println("  Please enter a value greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    static double getRangedDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("  Please enter a value between %.0f and %.0f.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    static int getRangedInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("  Please enter a value between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    static class Result {
        final int    panels;
        final double systemKw;
        final double dailyKwh;
        final double annualKwh;
        final double annualSavings;
        final double installCost;
        final double paybackYears;
        final double lifetimeSavings;
        final double co2SavedKg;

        Result(int panels, double systemKw, double dailyKwh, double annualKwh,
               double annualSavings, double installCost, double paybackYears,
               double lifetimeSavings, double co2SavedKg) {
            this.panels          = panels;
            this.systemKw        = systemKw;
            this.dailyKwh        = dailyKwh;
            this.annualKwh       = annualKwh;
            this.annualSavings   = annualSavings;
            this.installCost     = installCost;
            this.paybackYears    = paybackYears;
            this.lifetimeSavings = lifetimeSavings;
            this.co2SavedKg      = co2SavedKg;
        }
    }
}