package com.hrms.repositories;

import com.hrms.models.Attendance;
import com.hrms.utils.FileHandler;
import com.hrms.utils.HRMSException;
import java.util.*;

public class AttendanceRepository {

    public void save(Attendance a) throws HRMSException {
        if (findByEmployeeAndDate(a.getEmployeeId(), a.getDate()) != null)
            throw new HRMSException("Attendance already marked for " + a.getEmployeeId() + " on " + a.getDate());
        FileHandler.appendLine(FileHandler.ATTENDANCE_FILE, a.toFileString());
    }

    public void saveOrUpdate(Attendance newRecord) {
        List<Attendance> all = findAll();
        boolean found = false;
        List<String> lines = new ArrayList<>();
        for (Attendance a : all) {
            if (a.getEmployeeId().equalsIgnoreCase(newRecord.getEmployeeId())
                    && a.getDate().equals(newRecord.getDate())) {
                lines.add(newRecord.toFileString()); found = true;
            } else lines.add(a.toFileString());
        }
        if (!found) lines.add(newRecord.toFileString());
        FileHandler.writeLines(FileHandler.ATTENDANCE_FILE, lines);
    }

    public List<Attendance> findAll() {
        List<Attendance> list = new ArrayList<>();
        for (String line : FileHandler.readLines(FileHandler.ATTENDANCE_FILE)) {
            Attendance a = Attendance.fromFileString(line);
            if (a != null) list.add(a);
        }
        return list;
    }

    public List<Attendance> findByDate(String date) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : findAll()) if (a.getDate().equals(date)) result.add(a);
        return result;
    }

    public List<Attendance> findByEmployee(String employeeId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : findAll())
            if (a.getEmployeeId().equalsIgnoreCase(employeeId)) result.add(a);
        return result;
    }

    public Attendance findByEmployeeAndDate(String employeeId, String date) {
        for (Attendance a : findAll())
            if (a.getEmployeeId().equalsIgnoreCase(employeeId) && a.getDate().equals(date)) return a;
        return null;
    }
}
