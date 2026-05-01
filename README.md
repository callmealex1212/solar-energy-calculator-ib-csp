# Solar Energy Calculator

**IB DP Computer Science | CSP Practical Project**
Topic: Energy for Future Scientific Challenges

---

## Overview

This is a command-line Java application that estimates the solar energy production and financial savings of a rooftop solar panel installation. The user provides their roof area, geographic location, and panel efficiency, and the program calculates daily and annual energy output, installation cost, payback period, lifetime savings, and carbon emissions avoided.

The project was built as part of the IB Diploma Programme Computer Science course, addressing the theme of energy solutions for future scientific and environmental challenges.

---

## How to Run

You will need Java installed on your machine (JDK 8 or later).

**Step 1: Compile the program**

```
javac Main.java
```

**Step 2: Run the program**

```
java Main
```

Follow the on-screen prompts to enter your inputs.

---

## Inputs

The program asks for three things:

**Roof area** — the usable surface area of your roof in square metres.

**Location** — chosen from a menu of four preset regions (UK/Northern Europe, Central Europe, Southern Europe, Middle East/Africa), each mapped to a realistic average of peak sun hours per day. A custom value can also be entered.

**Panel efficiency** — chosen from four standard efficiency tiers (15%, 20%, 22%, 25%) representing budget through premium monocrystalline panels. A custom percentage can also be entered.

---

## Outputs

After the inputs are entered, the program displays:

**System summary** showing the number of panels that fit on the roof, the total system capacity in kilowatts, daily energy output in kWh, and annual energy output in kWh.

**Financial breakdown** showing the estimated installation cost, annual electricity savings, payback period in years, and projected net savings over the 25-year panel lifetime.

**Environmental impact** showing kilograms of CO2 avoided per year and the equivalent number of trees that would need to be planted to offset the same amount.

**Visual bar chart** giving a quick graphical representation of annual energy output in the terminal.

**Efficiency comparison table** automatically comparing all four efficiency tiers side by side so the user can see how panel quality affects the results.

---

## Key Assumptions and Constants

These values are based on real-world industry data and UK averages used at the time of development:

| Constant | Value | Source / Notes |
|---|---|---|
| Watts per panel | 400 W | Standard modern residential panel |
| Panel area | 2.0 m² | Typical physical footprint |
| System loss factor | 80% | Accounts for inverter, wiring, and temperature losses |
| Electricity price | £0.28 / kWh | UK average (2024) |
| Installation cost | £1,200 / kW | UK average residential quote |
| CO2 per kWh | 0.233 kg | UK National Grid carbon intensity |
| Panel lifetime | 25 years | Industry standard warranty period |

---

## Program Structure

The code is organised into clearly separated methods to demonstrate good software design practice:

`main` handles the overall program flow.

`calculate` contains all the core mathematical logic and returns a `Result` object.

`getPeakSunHours` and `getEfficiency` handle location and efficiency input with menus and validation.

`printResults` and `printComparisonTable` handle all output formatting.

`getPositiveDouble`, `getRangedDouble`, and `getRangedInt` are reusable input validation helpers that reject invalid or out-of-range entries and prompt the user again.

`Result` is a static inner class that stores all computed values cleanly.

---

## Technologies Used

Java (JDK 8+), standard library only. No external dependencies.

---

## Author

IB DP Computer Science Student Alex Shabani
