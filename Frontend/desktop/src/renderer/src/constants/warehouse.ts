import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';

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
