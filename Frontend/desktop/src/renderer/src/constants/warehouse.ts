import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';

// Create Warehouse Map
export const createWarehouseMapConfirmPopupData: InfoPopup = {
    title: 'Tạo sơ đồ',
    body: 'Xác nhận thông tin sơ đồ muốn tạo?'
};

const createWarehouseMapSuccessPopupData: ResultPopup = {
    title: 'Thành công',
    body: 'Sơ đồ kho đã được tạo thành công',
    popupType: ResultPopupType.Success
};

const createWarehouseMapErrorPopupData: ResultPopup = {
    title: 'Thất bại',
    body: 'Đã có lỗi xảy ra. Vui lòng thử lại',
    popupType: ResultPopupType.Error
};

export const CreateWarehouseMapResult = {
    Success: createWarehouseMapSuccessPopupData,
    Error: createWarehouseMapErrorPopupData
};

// Create Warehouse
export const createWarehouseConfirmPopupData: InfoPopup = {
    title: 'Tạo sơ mới',
    body: 'Xác nhận thông tin kho?'
};

const createWarehouseSuccessPopupData: ResultPopup = {
    title: 'Thành công',
    body: 'Kho mới đã được tạo thành công',
    popupType: ResultPopupType.Success
};

const createWarehouseErrorPopupData: ResultPopup = {
    title: 'Thất bại',
    body: 'Đã có lỗi xảy ra. Vui lòng thử lại',
    popupType: ResultPopupType.Error
};

export const CreateWarehouseResult = {
    Success: createWarehouseSuccessPopupData,
    Error: createWarehouseErrorPopupData
};
