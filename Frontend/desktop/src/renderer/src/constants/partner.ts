import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';
import { Partner } from '@renderer/types/partner';

export const defaultPartnerValue: Partner = {
    name: '',
    email: '',
    address: {
        address: '',
        province: null,
        district: null,
        ward: null
    },
    phone: '',
    phoneNumber: '',
    taxNumber: '',
    faxNumber: '',
    bankAccount: ''
};

export const createPartnerPopupData: InfoPopup = {
    title: 'Tạo đối tác',
    body: 'Xác nhận thông tin đối tác muốn tạo?'
};

export const createPartnerSuccessPopupData: ResultPopup = {
    title: 'Thành công',
    body: 'Đối tác mới đã được tạo',
    popupType: ResultPopupType.Success
};
