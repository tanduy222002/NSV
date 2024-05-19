import { login } from './auth/login';
import { signup } from './auth/signup';
import { generateOtp } from './auth/generateOtp';
import { renewPassword } from './auth/renewPassword';
import { getProductList } from './product/getProductList';
import { searchWarehouseMap } from './warehouse/searchWarehouseMap';
import { createWarehouseMap } from './warehouse/createWarehouseMap';
import { getWarehouseMap } from './warehouse/getWarehouseMap';
import { getWarehouseCategory } from './warehouse/getWarehouseCategory';
import { getProvinces } from './address/getProvinces';
import { getDistricts } from './address/getDistricts';
import { getWards } from './address/getWards';
import { searchPartner } from './partner/searchPartner';
import { createWarehouse } from './warehouse/createWarehouse';
import { getWarehouseDetail } from './warehouse/getWarehouseDetail';
import { getProductCategory } from './product/getProductCategory';
import { createImportTicket } from './import/createImportTicket';
import { searchImportTicket } from './import/searchImportTicket';
import { getProductType } from './product/getProductType';
import { createProduct } from './product/createProduct';
import { searchWarehouse } from './warehouse/searchWarehouse';
import { getWarehouseStatistic } from './warehouse/getWarehouseStatistic';
import { getSlotDetail } from './warehouse/getSlotDetail';
import { getSlotStatistic } from './warehouse/getSlotStatistic';
import { searchExportTicket } from './export/searchExportTicket';
import { getWarehouseDropdown } from './warehouse/getWarehouseDropdown';
import { getAvailableBin } from './export/getAvailableBin';
import { createExportTicket } from './export/createExportTicket';
import { getProductListStatistic } from './product/getProductListStatistic';
import { getProductLocation } from './product/getProductLocation';
import { getImportTicketDetail } from './import/getImportTicketDetail';
import { approveImportTicket } from './import/approveImportTicket';
import { getExportTicketDetail } from './export/getExportTicketDetail';
import { approveExportTicket } from './export/approveExportTicket';
import { getPartnerList } from './partner/getPartnerList';
import { getPartnerDetail } from './partner/getPartnerDetail';
import { getPartnerDebtDetail } from './partner/getPartnerDebtDetail';
import { getPartnerTransactionDetail } from './partner/getPartnerTransactionDetail';
import { removeTicketDebt } from './debt/removeTicketDebt';
import { getEmployeeList } from './employee/getEmployeeList';
import { deleteEmployee } from './employee/deleteEmployee';
import { getEmployeeDetail } from './employee/getEmployeeDetail';
import { updateEmployeeAccountStatus } from './employee/updateEmployeeAccountStatus';
import { updateEmployeeRoles } from './employee/updateEmployeeRoles';

export {
    login,
    signup,
    generateOtp,
    renewPassword,
    getProductList,
    searchWarehouseMap,
    getWarehouseMap,
    getWarehouseCategory,
    createWarehouseMap,
    createWarehouse,
    getProvinces,
    getDistricts,
    getWards,
    searchPartner,
    getWarehouseDetail,
    getProductCategory,
    createImportTicket,
    searchImportTicket,
    getProductType,
    createProduct,
    searchWarehouse,
    getWarehouseStatistic,
    getSlotDetail,
    getSlotStatistic,
    searchExportTicket,
    getWarehouseDropdown,
    getAvailableBin,
    createExportTicket,
    getProductListStatistic,
    getProductLocation,
    getImportTicketDetail,
    approveImportTicket,
    getExportTicketDetail,
    approveExportTicket,
    getPartnerList,
    getPartnerDetail,
    getPartnerDebtDetail,
    getPartnerTransactionDetail,
    removeTicketDebt,
    getEmployeeList,
    deleteEmployee,
    getEmployeeDetail,
    updateEmployeeAccountStatus,
    updateEmployeeRoles
};
