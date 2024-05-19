import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';
import { ImportTicket } from '@renderer/types/import';

export const defaultImportTicketValue: ImportTicket = {
    name: '',
    weight: 0,
    transporter: '',
    description: '',
    value: 0,
    debtDto: null,
    binDto: [],
    provider_id: null,
    import_date: '',
    transport_date: ''
};

export const approveTicketPopupInfo: InfoPopup = {
    title: 'Duyệt phiếu nhập',
    body: 'Duyệt phiếu sẽ cập nhật sức chứa kho tương ứng'
};

export const approveTicketSuccessPopup: ResultPopup = {
    title: 'Thành công',
    body: 'Duyệt phiếu nhập thành công',
    popupType: ResultPopupType.Success
};
