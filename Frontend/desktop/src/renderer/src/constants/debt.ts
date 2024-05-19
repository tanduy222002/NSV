import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';

export const removeTicketDebtPopupData: InfoPopup = {
    title: 'Gạch nợ',
    body: 'Xác nhận thanh toán công nợ?'
};

export const removeTicketDebtSuccessPopupData: ResultPopup = {
    title: 'Thành công',
    body: 'Công nợ đã được cập nhật',
    popupType: ResultPopupType.Success
};
