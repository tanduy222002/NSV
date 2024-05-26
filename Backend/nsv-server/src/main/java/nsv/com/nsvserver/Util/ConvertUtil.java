package nsv.com.nsvserver.Util;

import nsv.com.nsvserver.Entity.Address;

public class ConvertUtil {
    public static String convertAddressToString(Address address){
        if(address ==null)
            return "";
        StringBuilder addressString = new StringBuilder();
        addressString.append(address.getName());
        addressString.append(", ");
        addressString.append(address.getWard().getName());
        addressString.append(", ");
        addressString.append(address.getWard().getDistrict().getName());
        addressString.append(", ");
        addressString.append(address.getWard().getDistrict().getProvince().getName());
        return addressString.toString();
    }
}
