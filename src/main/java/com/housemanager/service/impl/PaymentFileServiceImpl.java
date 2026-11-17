package com.housemanager.service.impl;

import com.housemanager.data.entity.*;
import com.housemanager.service.PaymentFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class PaymentFileServiceImpl implements PaymentFileService {

    private static final String HEADER = "company,employee,building,apartment,amount,paymentDate";

    @Value("${app.payments.file}")
    private String filePath;

    @Override
    public synchronized void savePayment(Payment payment) {
        Apartment apartment = payment.getApartment();
        Building building = apartment.getBuilding();
        Company company = building.getEmployee().getCompany();
        Employee employee = building.getEmployee();

        String companyName = company.getCompanyName();
        String employeeName = employee.getFirstName() + " " + employee.getLastName();
        String buildingAddress = building.getAddress();
        String apartmentNumber = String.valueOf(apartment.getApartmentNumber());
        String amount = payment.getAmount().toString();
        String date = payment.getPaymentDate().toString();

        String line = clean(companyName) + ","
                + clean(employeeName) + ","
                + clean(buildingAddress) + ","
                + apartmentNumber + ","
                + amount + ","
                + date;

        writeLineToFile(line);
    }

    private void writeLineToFile(String line) {
        File file = new File(filePath);

        File folder = file.getParentFile();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        boolean needsHeader = !file.exists() || file.length() == 0;

        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            if (needsHeader) {
                writer.write(HEADER);
                writer.newLine();
            }
            writer.write(line);
            writer.newLine();

        } catch (IOException e) {
            throw new UncheckedIOException("Could not write the payment to the file", e);
        }
    }


    private String clean(String text) {
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }
}