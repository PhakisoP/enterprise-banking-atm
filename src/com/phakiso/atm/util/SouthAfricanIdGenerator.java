package com.phakiso.atm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SouthAfricanIdGenerator {

    private final Random random = new Random();
    public LocalDate generateBirthDate() {
        int year = 1970 + random.nextInt(31);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(
                LocalDate.of(year, month, 1).lengthOfMonth()
        );
        return LocalDate.of(year, month, day);
    }

    public String generateBirthDatePart() {
        LocalDate birthDate = generateBirthDate();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyMMdd");
        return birthDate.format(formatter);
    }


    public String generateSequenceDigits() {
        int sequence = random.nextInt(10000);
        return String.format("%04d", sequence);
    }


    public String generateCitizenshipDigit() {
        return "0";
    }


    public String generateIdWithoutChecksum() {
        String birthDatePart = generateBirthDatePart();
        String sequenceDigits = generateSequenceDigits();
        String citizenshipDigit = generateCitizenshipDigit();
        String raceDigit = generateRaceDigit();
        return birthDatePart
                + sequenceDigits
                + citizenshipDigit
                + raceDigit;
    }
    public String generateRaceDigit() {
        return "8";
    }



    public static void main(String[] args) {
        SouthAfricanIdGenerator generator =
                new SouthAfricanIdGenerator();
        for (int i = 0; i < 5; i++) {
            System.out.println(
                    generator.generateBirthDatePart()
            );
        }

    }
    public String generateChecksumDigit(String idNumber) {

        int oddSum = 0;

        // Step 1: Add digits in positions 1, 3, 5, 7, 9 and 11
        for (int i = 0; i < 12; i += 2) {
            oddSum += Character.getNumericValue(idNumber.charAt(i));
        }

        // Step 2: Concatenate digits in positions 2, 4, 6, 8, 10 and 12
        StringBuilder evenDigits = new StringBuilder();

        for (int i = 1; i < 12; i += 2) {
            evenDigits.append(idNumber.charAt(i));
        }

        // Step 3: Multiply the even-position number by 2
        int evenNumber =
                Integer.parseInt(evenDigits.toString()) * 2;

        // Step 4: Add the digits of the result
        int evenSum = 0;

        for (char c : String.valueOf(evenNumber).toCharArray()) {
            evenSum += Character.getNumericValue(c);
        }

        // Step 5: Calculate total
        int total = oddSum + evenSum;

        // Step 6: Calculate checksum
        int checksum =
                (10 - (total % 10)) % 10;

        return String.valueOf(checksum);
    }

    public String generateId() {
        String idWithoutChecksum =
                generateIdWithoutChecksum();
        String checksum =
                generateChecksumDigit(idWithoutChecksum);
        return idWithoutChecksum + checksum;
    }
    }

