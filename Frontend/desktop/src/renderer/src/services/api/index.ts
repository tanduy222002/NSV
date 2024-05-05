import { makeLoginRequest } from './auth/makeLoginRequest';
import { makeSignupRequest } from './auth/makeSignupRequest';
import { makeGenerateOtpRequest } from './auth/makeGenerateOtpRequest';
import { makeRenewPasswordRequest } from './auth/makeRenewPasswordRequest';
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

export {
    makeLoginRequest,
    makeSignupRequest,
    makeGenerateOtpRequest,
    makeRenewPasswordRequest,
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
    createProduct
};
