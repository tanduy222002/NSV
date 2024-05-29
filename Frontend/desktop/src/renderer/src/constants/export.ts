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

export const createExportTicketConfirmPopupData: InfoPopup = {
    title: 'Tạo phiếu xuất',
    body: 'Xác nhận thông tin phiếu xuất muốn tạo?'
};

type CreateExportTicketResult = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const CreateExportTicketResult: CreateExportTicketResult = {
    Success: {
        title: 'Thành công',
        body: 'Tạo phiếu xuất thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Đã có lỗi xảy ra khi tạo phiếu. Vui lòng thử lại.',
        popupType: ResultPopupType.Error
    }
};
