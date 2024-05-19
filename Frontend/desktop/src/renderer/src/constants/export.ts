import {
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';
import { ExportTicket } from '@renderer/types/export';

export const defaultExportTicketValue: ExportTicket = {
    name: '',
    transporter: '',
    description: '',
    debt: undefined,
    exportBins: [],
    customer_id: 0,
    export_date: ''
};

export const approveTicketPopupInfo: InfoPopup = {
    title: 'Duyệt phiếu xuất',
    body: 'Duyệt phiếu sẽ cập nhật sức chứa kho tương ứng'
};

export const approveTicketSuccessPopup: ResultPopup = {
    title: 'Thành công',
    body: 'Duyệt phiếu xuất thành công',
    popupType: ResultPopupType.Success
};
