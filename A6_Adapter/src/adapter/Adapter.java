package adapter;

import java.util.Comparator;
import java.util.List;

public class Adapter implements FileOperateInterfaceV2 {
    private FileOperateInterfaceV1 adaptee;

    public Adapter(FileOperateInterfaceV1 adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public List<StaffModel> readAllStaff() {
        return adaptee.readStaffFile();
    }

    @Override
    public void listAllStaff(List<StaffModel> list) {
        adaptee.printStaffFile(list);
    }

    public void writeByName(List<StaffModel> list) {
        List<StaffModel> sortedList = list.stream()
                .sorted(Comparator.comparing(StaffModel::getName))
                .toList();
        adaptee.writeStaffFile(sortedList);
    }

    @Override
    public void writeByRoom(List<StaffModel> list) {
        List<StaffModel> sortedList = list.stream()
                .sorted(Comparator.comparing(StaffModel::getRoom))
                .toList();
        adaptee.writeStaffFile(sortedList);
    }
}
