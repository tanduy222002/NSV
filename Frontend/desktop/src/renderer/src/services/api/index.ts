import { makeLoginRequest } from './auth/makeLoginRequest';
import { makeSignupRequest } from './auth/makeSignupRequest';
import { makeGenerateOtpRequest } from './auth/makeGenerateOtpRequest';
import { makeRenewPasswordRequest } from './auth/makeRenewPasswordRequest';
import { makeGetProductListRequest } from './product/makeGetProductListRequest';
import { searchWarehouseMap } from './warehouse/searchWarehouseMap';
import { createWarehouseMap } from './warehouse/createWarehouseMap';
import { getWarehouseMap } from './warehouse/getWarehouseMap';
import { getWarehouseCategory } from './warehouse/getWarehouseCategory';
import { getProvinces } from './address/getProvinces';
import { getDistricts } from './address/getDistricts';
import { getWards } from './address/getWards';
import { searchPartner } from './partner/searchPartner';

export {
    makeLoginRequest,
    makeSignupRequest,
    makeGenerateOtpRequest,
    makeRenewPasswordRequest,
    makeGetProductListRequest,
    searchWarehouseMap,
    getWarehouseMap,
    getWarehouseCategory,
    createWarehouseMap,
    getProvinces,
    getDistricts,
    getWards,
    searchPartner
};
