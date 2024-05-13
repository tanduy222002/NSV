import { ResultPopup, ResultPopupType } from '@renderer/types/common';

type LoginErrorType = {
    Format: ResultPopup;
    Value: ResultPopup;
    Internal: ResultPopup;
};

export const LoginError: LoginErrorType = {
    Format: {
        title: 'Lỗi xác thực',
        body: 'Tài khoản hoặc mật khẩu không đúng định dạng',
        popupType: ResultPopupType.Error
    },
    Value: {
        title: 'Lỗi xác thực',
        body: 'Tài khoản hoặc mật khẩu không chính xác',
        popupType: ResultPopupType.Error
    },
    Internal: {
        title: 'Lỗi xử lý',
        body: 'Máy chủ không thể xử lý yêu cầu, vui lòng thử lại sau',
        popupType: ResultPopupType.Error
    }
};
