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

export const CreatePartnerResult = {
    Success: {
        title: 'Thành công',
        body: 'Đối tác mới đã được tạo',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Tạo đối tác thất bại',
        popupType: ResultPopupType.Error
    }
};

export const updatePartnerProfileConfirmPopupData: InfoPopup = {
    title: 'Cập nhật đối tác',
    body: 'Xác nhận thông tin đối tác?'
};

type UpdatePartnerProfileResult = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const UpdatePartnerProfileResult: UpdatePartnerProfileResult = {
    Success: {
        title: 'Thành công',
        body: 'Cập nhật thông tin đối tác thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Cập nhật thông tin đối tác thất bại',
        popupType: ResultPopupType.Error
    }
};
