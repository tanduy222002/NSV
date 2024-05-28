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

export const ApproveImportTicketResult = {
    Success: {
        title: 'Thành công',
        body: 'Duyệt phiếu nhập thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Duyệt phiếu nhập thất bại',
        popupType: ResultPopupType.Error
    }
};

export const createImportTicketConfirmPopupData: InfoPopup = {
    title: 'Tạo phiếu nhập',
    body: 'Xác nhận thông tin phiếu nhập muốn tạo?'
};

type CreateImportTicketResult = {
    Success: ResultPopup;
    Error: ResultPopup;
};

export const CreateImportTicketResult: CreateImportTicketResult = {
    Success: {
        title: 'Thành công',
        body: 'Tạo phiếu nhập thành công',
        popupType: ResultPopupType.Success
    },
    Error: {
        title: 'Thất bại',
        body: 'Đã có lỗi xảy ra khi tạo phiếu. Vui lòng thử lại.',
        popupType: ResultPopupType.Error
    }
};
